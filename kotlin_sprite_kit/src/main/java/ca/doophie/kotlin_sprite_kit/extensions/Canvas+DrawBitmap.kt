package ca.doophie.kotlin_sprite_kit.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect

/**
 * Draws a bitmap at the specified location
 */
fun Canvas.drawBitmap(bitmap: Bitmap, location: Point = Point(0, 0)) {
    this.drawBitmap(bitmap,
        null,
        Rect(location.x, location.y, location.x + bitmap.width, location.y + bitmap.height),
        null)
}