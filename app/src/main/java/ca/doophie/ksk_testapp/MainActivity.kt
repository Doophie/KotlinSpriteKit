package ca.doophie.ksk_testapp

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Size
import ca.doophie.kotlin_sprite_kit.engine.Camera
import ca.doophie.kotlin_sprite_kit.extensions.bitmap
import ca.doophie.kotlin_sprite_kit.extensions.scale
import ca.doophie.kotlin_sprite_kit.views.Joystick
import ca.doophie.ksk_testapp.databinding.ActivityMainBinding
import android.util.DisplayMetrics




class MainActivity : AppCompatActivity() {

    val gameCamera = Camera()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create an instance of our sprite
        val sampleSprite = SampleSprite(resources)

        // add it to the sprite surface view
        binding.spriteSurface.addSprite(sampleSprite)

        val background = resources.bitmap(R.drawable.demo_background)!!.scale(3.0)
        binding.spriteSurface.background = background
        binding.spriteSurface.setCamera(gameCamera)

        gameCamera.viewableArea = Size(getScreenDimens().width, getScreenDimens().height)
        gameCamera.trackSprite(sampleSprite, toBounds = Rect(0, 0, background.width, background.height))

        // track movements on the joystick
        binding.joystick.movementCallbacks = object: Joystick.JoystickMovementCallbacks {
            override fun onMove(strength: Double, angle: Double) {
                // move the sprite with the joystick
                sampleSprite.moveSprite(strength, angle)

                // set the sprites animation based on if its moving or not
                if (strength > 0)
                    sampleSprite.setAnimation(SampleSprite.Animations.WALK)
                else
                    sampleSprite.setAnimation(SampleSprite.Animations.IDLE)
            }
        }
    }

    fun getScreenDimens() : Size {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        return Size(width, height)
    }
}