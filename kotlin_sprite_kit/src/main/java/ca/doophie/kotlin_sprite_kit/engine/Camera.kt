package ca.doophie.kotlin_sprite_kit.engine

import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.os.Build
import android.util.Size
import android.util.SizeF
import ca.doophie.kotlin_sprite_kit.sprite.Sprite
import java.lang.Integer.max
import java.lang.Integer.min

/***
 * A Camera keeps track of what area of a Scene should be displayed
 *
 * the camera can be used to track a specific object, be stationary or move independently
 */
class Camera : Ticker.TickSubscriber {

    // the center point of the camera
    var position: Point = Point(0, 0)

    // the width and height of the area the camera will display
    var viewableArea: Size = Size(0, 0)

    private val distanceToCenterX: Int
        get() = viewableArea.width / 2

    private val distanceToCenterY: Int
        get() = viewableArea.height / 2

    private var spriteToFollow: Sprite? = null
    private var outerBounds: Rect? = null

    fun trackSprite(sprite: Sprite, toBounds: Rect) {
        spriteToFollow = sprite
        outerBounds = toBounds
    }

    // move the camera to where ever it needs to be
    override fun tick(fps: Int): Boolean {
        spriteToFollow?.position?.let { followedSpritePosition ->
            val newPosition = Point(
                followedSpritePosition.x - distanceToCenterX,
                followedSpritePosition.y - distanceToCenterY
            )

            position = if (outerBounds != null) {
                Point(
                    minOf(outerBounds!!.right - distanceToCenterX*2, maxOf(newPosition.x, outerBounds!!.left)),
                    minOf(outerBounds!!.bottom - distanceToCenterY*2, maxOf(newPosition.y, outerBounds!!.top))
                )
            } else {
                newPosition
            }
        }

        return true
    }

}