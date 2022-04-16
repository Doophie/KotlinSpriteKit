package ca.doophie.kotlin_sprite_kit.extensions

import android.graphics.*
import ca.doophie.kotlin_sprite_kit.engine.Camera

/**
 * Draws a bitmap at the specified location
 */
fun Canvas.drawBitmap(bitmap: Bitmap, location: Point = Point(0, 0), camera: Camera) {
    this.drawBitmap(bitmap,
        null,
        Rect(location.x - camera.position.x,
            location.y - camera.position.y,
            location.x + bitmap.width - camera.position.x,
            location.y + bitmap.height - camera.position.y),
        null)
}