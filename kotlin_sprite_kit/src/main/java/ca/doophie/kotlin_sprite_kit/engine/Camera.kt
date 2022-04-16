package ca.doophie.kotlin_sprite_kit.engine

import android.graphics.PointF
import android.util.SizeF

/***
 * A Camera keeps track of what area of a Scene should be displayed
 *
 * the camera can be used to tack a specific object, be stationary or move independently
 */
class Camera {

    // the center point of the camera
    val position: PointF = PointF(0f, 0f)

    // the width and height of the area the camera will display
    val bounds: SizeF = SizeF(0f, 0f)

}