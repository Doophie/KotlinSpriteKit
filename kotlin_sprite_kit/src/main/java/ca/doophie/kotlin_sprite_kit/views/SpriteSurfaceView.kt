package ca.doophie.kotlin_sprite_kit.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceView
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        ticker.subscribe(this)

        ticker.resume()
    }

    fun addSprite(sprite: Sprite) {
        ticker.subscribe(sprite)

        sprites.add(sprite)
    }

    override fun tick(fps: Int): Boolean {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas?.drawPaint(paint)

            sprites.forEach { sprite ->
                sprite.image?.let { image ->
                    canvas?.drawBitmap(image, sprite.position)
                }
            }

            holder.unlockCanvasAndPost(canvas)
        }

        return true
    }
}