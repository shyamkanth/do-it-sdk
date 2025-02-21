package io.github.shyamkanth.doitsdk

import android.content.Context
import android.graphics.Bitmap

class DoitSdk(private val context: Context) {

    fun generateImageWithName(
        initials: String,
        position: Int
    ): Bitmap{
        return Utils.generateInitialsImage(initials = initials, position = position)
    }

}