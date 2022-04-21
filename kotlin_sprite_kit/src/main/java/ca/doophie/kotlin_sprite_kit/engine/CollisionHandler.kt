package ca.doophie.kotlin_sprite_kit.engine

import android.graphics.Rect
import android.util.Size
import ca.doophie.kotlin_sprite_kit.extensions.Rectangle
import ca.doophie.kotlin_sprite_kit.extensions.hasOverlap
import ca.doophie.kotlin_sprite_kit.sprite.Sprite

class CollisionHandler {

    // A list of all walls
    val walls: ArrayList<Sprite> = ArrayList()

    fun checkForCollisions(sprites: List<Sprite>) {
        for (i in 0 until sprites.count() - 1) {
            val spriteBox = getSpriteBox(sprites[i])

            for (j in i + 1 until sprites.count()) {
                val overlap = spriteBox.hasOverlap(getSpriteBox(sprites[j]))

                if (overlap != Rect(0, 0, 0, 0)) {
                    sprites[i].collision(sprites[j])
                    sprites[j].collision(sprites[i])
                }
            }
        }
    }

    private fun getSpriteBox(sprite: Sprite): Rectangle {
        val position = sprite.position
        val size = Size(sprite.image?.width ?: 0, sprite.image?.height ?: 0)

        return Rectangle(position.x, position.y, size.width, size.height)
    }

}