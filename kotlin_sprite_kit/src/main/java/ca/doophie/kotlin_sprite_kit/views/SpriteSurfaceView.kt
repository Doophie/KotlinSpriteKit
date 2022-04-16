package ca.doophie.kotlin_sprite_kit.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceView
import ca.doophie.kotlin_sprite_kit.engine.Camera
import ca.doophie.kotlin_sprite_kit.engine.Ticker
import ca.doophie.kotlin_sprite_kit.extensions.drawBitmap
import ca.doophie.kotlin_sprite_kit.sprite.Sprite

class SpriteSurfaceView(context: Context, attributeSet: AttributeSet) :
    SurfaceView(context, attributeSet), Ticker.TickSubscriber {

    companion object {
        private const val TAG = "SpriteSurfaceView"
    }

    private val ticker = Ticker()

    fun pause() {
        ticker.pause()
    }

    fun resume() {
        ticker.resume()
    }

    private val sprites: ArrayList<Sprite> = ArrayList()

    private var canvas: Canvas? = null
    private var paint: Paint = Paint()

    private var camera: Camera = Camera()

    var background: Bitmap? = null

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        ticker.subscribe(this)

        ticker.resume()
    }

    fun setCamera(camera: Camera) {
        ticker.subscribe(camera)

        this.camera = camera
    }

    fun addSprite(sprite: Sprite) {
        ticker.subscribe(sprite)

        sprites.add(sprite)
    }

    override fun tick(fps: Int): Boolean {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            if (background == null) {
                canvas?.drawPaint(paint)
            } else {
                canvas?.drawBitmap(background!!, Point(0, 0), camera)
            }

            sprites.forEach { sprite ->
                sprite.image?.let { image ->
                    canvas?.drawBitmap(image, sprite.position, camera)
                }
            }

            holder.unlockCanvasAndPost(canvas)
        }

        return true
    }
}