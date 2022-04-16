package ca.doophie.ksk_testapp

import android.content.res.Resources
import android.util.Size
import ca.doophie.kotlin_sprite_kit.extensions.bitmap
import ca.doophie.kotlin_sprite_kit.sprite.Sprite
import ca.doophie.kotlin_sprite_kit.sprite.SpriteSheet


class SampleSprite(resources: Resources): Sprite() {

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

    override val name: String = "SampleSprite"

    override val spriteSheet: SpriteSheet = SpriteSheet(
        resources.bitmap(R.drawable.shamelessly_stolen_spritesheet)!!,
        13,
        8
    )

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

    fun setAnimation(animation: Animations) {
        spriteSheet.setActiveRow(animation.row)
    }
}