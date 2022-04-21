package ca.doophie.ksk_testapp

import android.graphics.Point
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
import ca.doophie.kotlin_sprite_kit.other.ScreenMetricsCompat


class MainActivity : AppCompatActivity() {

    private val gameCamera = Camera()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val background = resources.bitmap(R.drawable.demo_background)!!.scale(3.0)
        val sampleSprite = SampleSprite(resources)
        val sampleSprite2 = SampleSprite(resources)

        sampleSprite2.position = Point(600, 600)

        // add sample sprite to the sprite surface view
        binding.spriteSurface.addSprite(sampleSprite)
        binding.spriteSurface.addSprite(sampleSprite2)

        // set the background
        binding.spriteSurface.background = background

        // set the camera
        binding.spriteSurface.setCamera(gameCamera)

        // set up the camera and make it track the sample sprite
        gameCamera.viewableArea = Size(ScreenMetricsCompat.getScreenSize(this).width, ScreenMetricsCompat.getScreenSize(this).height)
        gameCamera.trackSprite(sampleSprite, toBounds = Rect(0, 0, background.width, background.height))

        // track movements on the joystick
        binding.joystick.movementCallbacks = object: Joystick.JoystickMovementCallbacks {
            override fun onMove(strength: Double, angle: Double) {
                // move the sprite with the joystick
                sampleSprite.moveSprite(strength, angle)

                // set the sprites animation based on if its moving or not
                if (strength > 0) {
                    sampleSprite.setAnimation(SampleSprite.Animation.WALK)
                    sampleSprite.flipImage = !(angle < 90 || angle > 270)
                } else {
                    sampleSprite.setAnimation(SampleSprite.Animation.IDLE)
                }
            }
        }

        binding.aButton.setOnClickListener {
            sampleSprite.playAnimation(SampleSprite.Animation.STAB)
        }
    }
}