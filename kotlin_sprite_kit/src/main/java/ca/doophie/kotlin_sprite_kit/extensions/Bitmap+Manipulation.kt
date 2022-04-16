package ca.doophie.kotlin_sprite_kit.extensions

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect

val Bitmap.flipped: Bitmap?
    get() {
        val matrix = Matrix()
        matrix.postScale(-1f, 1f, width/2f, height/2f)
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
    }

fun Bitmap.cropped(rect: Rect): Bitmap {
    return Bitmap.createBitmap(this, rect.left, rect.top, rect.right-rect.left, rect.bottom-rect.top)
}

fun Bitmap.scale(amount: Double): Bitmap {
    return Bitmap.createScaledBitmap(this, (this.width * amount).toInt(), (this.height * amount).toInt(), false)
}