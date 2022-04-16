package ca.doophie.kotlin_sprite_kit.sprite

import android.graphics.Bitmap
import android.graphics.Point
import ca.doophie.kotlin_sprite_kit.engine.Ticker
import ca.doophie.kotlin_sprite_kit.extensions.flipped
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

abstract class Sprite: Ticker.TickSubscriber {

    abstract val name: String
    abstract val spriteSheet: SpriteSheet

    private var framesToDisplayPerSecond = 10
    private var framesSinceLastUpdate = 0

    // the current image to display for this sprite
    var image: Bitmap? = null

    var position: Point = Point(0, 0)

    private var movementStrength: Double = 0.0
    private var movementAngle: Double = 0.0

    private var movespeed: Int = 25

    var flipImage: Boolean = false

    // todo: Update sprite location
    fun moveSprite(strength: Double, angle: Double) {
        movementStrength = strength
        movementAngle = angle
    }

    // handle the game tick
    override fun tick(fps: Int): Boolean {
        updateSpriteImage(fps)
        updateSpriteLocation(fps)

        return true
    }

    private fun updateSpriteLocation(fps: Int) {
        val strength = movementStrength * movespeed * (80/max(fps.toDouble(),1.0))
        val xDelta =  (strength * cos(Math.toRadians(movementAngle))).toInt()
        val yDelta = (strength * sin(Math.toRadians(movementAngle))).toInt()

        position = Point(position.x + xDelta, position.y - yDelta)
    }

    private fun updateSpriteImage(fps: Int)  {
        // the number of frames in-between each frame where we don't update the sprite sheet
        val emptyFrames = fps / framesToDisplayPerSecond

        framesSinceLastUpdate += 1

        // if the number of frames since last update exceeds the number of desired empty frames,
        // move on to the next frame
        if (framesSinceLastUpdate >= emptyFrames) {
            val nextImage = spriteSheet.nextFrame()

            image = if (flipImage)
                nextImage.flipped
            else
                nextImage

            framesSinceLastUpdate = 0
        }
    }
}