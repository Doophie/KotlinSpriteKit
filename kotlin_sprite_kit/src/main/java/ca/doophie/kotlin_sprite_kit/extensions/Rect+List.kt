package ca.doophie.kotlin_sprite_kit.extensions

import android.graphics.Rect

val Rect.list: List<Int>
    get() = listOf(top, left, right, bottom)