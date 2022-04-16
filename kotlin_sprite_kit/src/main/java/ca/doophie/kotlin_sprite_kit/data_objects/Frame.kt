package ca.doophie.kotlin_sprite_kit.data_objects

import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.RectF

data class Frame (
    val bitmap: Bitmap,
    val frameToDraw: Rect
)