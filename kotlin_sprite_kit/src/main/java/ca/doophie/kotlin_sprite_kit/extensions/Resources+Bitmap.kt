package ca.doophie.kotlin_sprite_kit.extensions

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun Resources.bitmap(id: Int): Bitmap? {
    return BitmapFactory.decodeResource(this, id)
}

