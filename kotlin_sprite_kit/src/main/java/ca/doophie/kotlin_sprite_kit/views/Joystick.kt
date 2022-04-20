package ca.doophie.kotlin_sprite_kit.views

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import androidx.core.content.ContextCompat
import ca.doophie.kotlin_sprite_kit.R
import java.lang.Exception
import java.lang.Math.atan2
import kotlin.math.pow

class Joystick(context: Context, attributes: AttributeSet):
    SurfaceView(context, attributes), View.OnTouchListener {

    companion object {
        private const val TAG = "Joystick"
    }

    interface JoystickMovementCallbacks {
        fun onMove(strength: Double, angle: Double)
    }

    interface JoystickSelectionCallbacks {
        fun itemSelected(index: Int)
    }

    var movementCallbacks: JoystickMovementCallbacks? = null
    var selectionCallbacks: JoystickSelectionCallbacks? = null

    private var padColor: Int // Color.argb(50, 100, 0, 0)
    private var stickColor: Int // Color.argb(0,0, 200, 0)

    var selectionNumItems: Int = 360
    private val selectionDiv: Int get() { return 360 / selectionNumItems }

    init {
        val ta = getContext().obtainStyledAttributes(attributes, R.styleable.Joystick)
        padColor = ContextCompat.getColor(context, ta.getResourceId(R.styleable.Joystick_stickBackground, android.R.color.darker_gray))
        stickColor = ContextCompat.getColor(context, ta.getResourceId(R.styleable.Joystick_stickForeground, android.R.color.white))
        ta.recycle()
    }

    override fun onTouch(v: View?, e: MotionEvent?): Boolean {
        if(v?.equals(this) == true){
            if(e == null) return true
            if(e.action != MotionEvent.ACTION_UP && e.action != 3){
                val displacement = Math.sqrt(Math.pow((e.x - centerX).toDouble(), 2.0)
                        + Math.pow((e.y - centerY).toDouble(), 2.0)).toFloat()
                if(displacement < baseRadius) {
                    drawJoystick(e.x, e.y)
                } else {
                    val ratio = baseRadius / displacement
                    val constrainedX = centerX + (e.x - centerX) * ratio
                    val constrainedY = centerY + (e.y - centerY) * ratio
                    drawJoystick(constrainedX, constrainedY)
                }
            } else {
                movementCallbacks?.onMove(0.0, 0.0)

                if (selectionCallbacks != null) {
                    val displacement = Math.sqrt(Math.pow((e.x - centerX).toDouble(), 2.0)
                            + Math.pow((e.y - centerY).toDouble(), 2.0)).toFloat()
                    val ratio = baseRadius / displacement
                    val constrainedX = centerX + (e.x - centerX) * ratio
                    val constrainedY = centerY + (e.y - centerY) * ratio
                    selectionCallbacks?.itemSelected((calculateAngle(constrainedX, constrainedY) / selectionDiv).toInt())
                }

                drawJoystick(centerX, centerY)
            }
        }

        return true
    }

    private val centerX
        get() = width / 2f
    private val centerY
        get() = height / 2f

    private val baseRadius
        get() = width.coerceAtMost(height) / 3f

    private val hatRadius
        get() = baseRadius / 3

    init {
        setOnTouchListener(this)
        this.setBackgroundResource(0)
        this.setBackgroundColor(Color.TRANSPARENT)
        this.setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSPARENT)
        Handler(Looper.getMainLooper()).postDelayed({
            reset()
        }, 100)
    }

    private fun reset(){
        drawJoystick(centerX, centerY)
    }

    private fun drawJoystick(newX: Float, newY: Float){
        try {
            val myCanvas = holder.lockCanvas()
            val color = Paint()

            myCanvas.drawColor(0, PorterDuff.Mode.SRC_IN)

            // draw pad
            color.color = padColor
            myCanvas.drawCircle(centerX, centerY, baseRadius, color)

            // draw stick
            color.color = stickColor
            myCanvas.drawCircle(newX, newY, hatRadius, color)

            holder.unlockCanvasAndPost(myCanvas)

            movementCallbacks?.onMove(calculateStrength(newX, newY), calculateAngle(newX, newY))
        } catch (e: Exception) {
            Log.e(TAG, "DrawJoystick failed: $e")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val usableDimen = maxOf(measuredHeight, measuredWidth)
        setMeasuredDimension(usableDimen, usableDimen)
    }

    private fun calculateAngle(x2: Float, y2: Float): Double {
        val deltaX = x2 - centerX
        val deltaY = centerY - y2
        val result = Math.toDegrees(atan2(deltaY.toDouble(), deltaX.toDouble()))
        return if (result < 0) 360.0 + result else result
    }

    private fun calculateStrength(x2: Float, y2: Float): Double {
        val hyp = (Math.sqrt((centerX-x2).toDouble().pow(2) + (centerY-y2).toDouble().pow(2)))
        return hyp/baseRadius
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawJoystick(centerX, centerY)
    }
}