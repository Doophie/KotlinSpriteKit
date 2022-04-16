package ca.doophie.kotlin_sprite_kit.sprite

import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Size
import ca.doophie.kotlin_sprite_kit.extensions.cropped

class SpriteSheet(
    private val image: Bitmap,
    private val numColumns: Int,
    private val numRows: Int
) {
    /***
     * tracks the number of columns each row has
     * by default it assumes is row has the same number of columns, override this
     * variable if this is not the case for a particular sprite sheet
     */
    private var individualColumnLengths: Map<Int, Int> = List(numRows) { rowNum ->
        rowNum to numColumns
    }.toMap()

    var defaultRow: Int = 0

    // keep track of which image to display
    private var currentRow: Int = 0
    private var currentColumn: Int = 0

    // dimensions of the frame
    private val frameWidth: Int
        get() = image.width / numColumns

    private val frameHeight: Int
        get() = image.height / numRows

    private var animRepeatRemaining: Int = 0

    // goes to the next available sprite, loops back if at the end of a row, and returns a cropped
    // bitmap of that sprite
    fun nextFrame(): Bitmap {
        incrementColumn()

        return buildFrame()
    }

    fun getActiveRow(): Int {
        return currentRow
    }

    fun setActiveRow(row: Int) {
        if (row < 0 || row > numRows) {
            throw Exception("Invalid row")
        }

        currentColumn = 0
        currentRow = row
    }

    fun setRowLength(row: Int, numColumns: Int) {
        val newMap = HashMap(individualColumnLengths)
        newMap[row] = numColumns
        individualColumnLengths = newMap
    }

    private fun incrementColumn() {
        val lastColumn = currentColumn
        currentColumn = (currentColumn + 1) % individualColumnLengths[currentRow]!!

        if (currentColumn < lastColumn && animRepeatRemaining > 0) {
            animRepeatRemaining -= 1
            if (animRepeatRemaining <= 0) {
                currentRow = defaultRow
            }
        }
    }

    private fun buildFrame(): Bitmap {
        val frameToDraw = Rect(0, 0, 0, 0)

        frameToDraw.left = currentColumn * frameWidth
        frameToDraw.right = frameToDraw.left + frameWidth

        frameToDraw.top = frameHeight * currentRow
        frameToDraw.bottom = frameToDraw.top + frameHeight

        return image.cropped(frameToDraw)
    }

    fun playRowAnimation(row: Int, repeat: Int = 1) {
        setActiveRow(row)
        animRepeatRemaining = repeat
    }
}