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

    private val gameCamera = Camera()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val background = resources.bitmap(R.drawable.demo_background)!!.scale(3.0)
        val sampleSprite = SampleSprite(resources)

        // add sample sprite to the sprite surface view
        binding.spriteSurface.addSprite(sampleSprite)

        // set the background
        binding.spriteSurface.background = background

        // set the camera
        binding.spriteSurface.setCamera(gameCamera)

        // set up the camera and make it track the sample sprite
        gameCamera.viewableArea = Size(getScreenDimens().width, getScreenDimens().height)
        gameCamera.trackSprite(sampleSprite, toBounds = Rect(0, 0, background.width, background.height))

        // track movements on the joystick
        binding.joystick.movementCallbacks = object: Joystick.JoystickMovementCallbacks {
            override fun onMove(strength: Double, angle: Double) {
                // move the sprite with the joystick
                sampleSprite.moveSprite(strength, angle)

                // set the sprites animation based on if its moving or not
                if (strength > 0) {
                    sampleSprite.setAnimation(SampleSprite.Animations.WALK)
                    sampleSprite.flipImage = !(angle < 90 || angle > 270)
                } else {
                    sampleSprite.setAnimation(SampleSprite.Animations.IDLE)
                }
            }
        }

        binding.aButton.setOnClickListener {
            sampleSprite.playAnimation(SampleSprite.Animations.STAB)
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