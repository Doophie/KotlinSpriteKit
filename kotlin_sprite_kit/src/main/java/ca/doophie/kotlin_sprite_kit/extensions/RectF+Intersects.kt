package ca.doophie.kotlin_sprite_kit.extensions

import android.graphics.Rect
import android.graphics.RectF
import kotlin.math.abs

data class Rectangle(
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int
)

val RectF.rectangle: Rectangle
    get() {
        return Rectangle(left.toInt(), top.toInt(), (right - left).toInt(), (bottom - top).toInt())
    }

val RectF.rect: Rect
    get() = Rect(
        left.toInt(),
        top.toInt(),
        right.toInt(),
        bottom.toInt()
    )

fun RectF.overlaps(rect: RectF): Rect {
    return rectangle.hasOverlap(rect.rectangle)
}

fun Rectangle.hasOverlap(B: Rectangle): Rect {
    val w = 0.5 * (width + B.width)
    val h = 0.5 * (height + B.height)
    val dx = (x + (width/2)) - (B.x + (B.width / 2))
    val dy = (y + (height/2)) - (B.y + (B.height / 2))

    val results = Rect(0, 0, 0, 0)

    if (abs(dx) <= w && abs(dy) <= h)
    {
        /* collision! */
        val wy = w * dy
        val hx = h * dx

        if (wy > hx) {
            if (wy > -hx)
            /* collision at the top */
                results.top = 1
            else
            /* on the left */
                results.right = 1
        } else {
            if (wy > -hx)
            /* on the right */
                results.left = 1
            else
            /* at the bottom */
                results.bottom = 1
        }
    }

    return results
}