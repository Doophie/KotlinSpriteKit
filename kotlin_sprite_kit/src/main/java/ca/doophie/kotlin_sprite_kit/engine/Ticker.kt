package ca.doophie.kotlin_sprite_kit.engine

import android.util.Log

/**
 * A Ticker is required to run any sprites
 *
 * The ticker will simply just tick and keep track of the FPS
 */
class Ticker(val threadName: String = "Default"): Runnable {
    interface TickSubscriber {
        fun tick(fps: Int): Boolean
    }

    companion object {
        const val TAG = "SpriteSheetTicker"
    }

    /**
     * Private vars
     */

    private var tickerThread: Thread? = null
    private var listeners: List<TickSubscriber> = emptyList()

    private var lastFrameChangeTime: Long = 0
    private val frameLengthInMilliseconds = 2

    var isRunning: Boolean = false
        private set

    private var pastFps: List<Int> = emptyList()
    var fps: Int = 60
        private set(value) {
            val pastFps = pastFps.toCollection(ArrayList())

            if (value != 0) pastFps.add(value)

            field = if (pastFps.count() == 0)
                1
            else
                pastFps.sum() / pastFps.count()

            if (pastFps.count() > 10) pastFps.removeAt(0)

            this.pastFps = pastFps
        }

    /**
     * Public methods
     */

    fun pause() {
        isRunning = false
        try {
            tickerThread?.join()
            tickerThread = null
        } catch (e: InterruptedException) {
            Log.e(TAG, "Joining thread: ${e.localizedMessage}")
        }
    }

    fun resume() {
        if (isRunning) return

        isRunning = true

        if (tickerThread == null) {
            tickerThread = Thread(this, threadName)
        }

        tickerThread?.start()
    }

    fun subscribe(listener: TickSubscriber) {
        val updatedListeners = ArrayList<TickSubscriber>()

        updatedListeners.addAll(listeners)
        updatedListeners.add(listener)

        listeners = updatedListeners
    }

    override fun run() {
        var lastFrameTime = System.currentTimeMillis()
        while (isRunning) {
            if (tick()) {
                val timeThisFrame = System.currentTimeMillis() - lastFrameTime

                if (timeThisFrame >= 1) {
                    fps = (1000 / timeThisFrame).toInt()
                }

                lastFrameTime = System.currentTimeMillis()
            }
        }
    }

    private fun tick(): Boolean {
        if (!getCurrentFrame()) return false

        var success = true

        listeners.forEach { success = it.tick(fps) }

        return success
    }

    private fun getCurrentFrame(): Boolean {
        val time = System.currentTimeMillis()

        if (isRunning) {
            if (time > lastFrameChangeTime + frameLengthInMilliseconds) {
                lastFrameChangeTime = System.currentTimeMillis()

                return true
            }
        }

        return false
    }
}