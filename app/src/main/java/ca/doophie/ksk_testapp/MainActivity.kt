package ca.doophie.ksk_testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ca.doophie.kotlin_sprite_kit.views.Joystick
import ca.doophie.ksk_testapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create an instance of our sprite
        val sampleSprite = SampleSprite(resources)

        // add it to the sprite surface view
        binding.spriteSurface.addSprite(sampleSprite)

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
}