package ca.doophie.ksk_testapp

import android.content.res.Resources
import android.util.Size
import ca.doophie.kotlin_sprite_kit.extensions.bitmap
import ca.doophie.kotlin_sprite_kit.extensions.scale
import ca.doophie.kotlin_sprite_kit.sprite.Sprite
import ca.doophie.kotlin_sprite_kit.sprite.SpriteSheet


class SampleSprite(resources: Resources): Sprite() {

    // each row in the sprite sheet represents an animation
    // this enum maps the row to the animation
    enum class Animations(val row: Int) {
        IDLE(0),
        WALK(1),
        SWING_1(2),
        SWING_2(3),
        STAB(4),
        JUMP(5),
        PANT(6),
        DIE(7);
    }

    // the name isn't really used right now except for logging
    override val name: String = "SampleSprite"

    // each sprite has a corresponding sprite sheet, define it here
    override val spriteSheet: SpriteSheet = SpriteSheet(
        resources.bitmap(R.drawable.shamelessly_stolen_spritesheet)!!.scale(3.0),
        13,
        8
    )

    // set the length of each row in the sprite sheet
    init {
        spriteSheet.setRowLength(0, 13)
        spriteSheet.setRowLength(1, 8)
        spriteSheet.setRowLength(2, 10)
        spriteSheet.setRowLength(3, 10)
        spriteSheet.setRowLength(4, 10)
        spriteSheet.setRowLength(5, 6)
        spriteSheet.setRowLength(6, 4)
        spriteSheet.setRowLength(7, 7)
    }

    // a custom method for setting the active animation of this sprite
    fun setAnimation(animation: Animations) {
        // setting the active row resets the column to 0, so only set it if its changed
        if (spriteSheet.getActiveRow() != animation.row)
            spriteSheet.setActiveRow(animation.row)
    }
}