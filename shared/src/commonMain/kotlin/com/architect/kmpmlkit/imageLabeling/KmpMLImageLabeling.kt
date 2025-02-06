package com.architect.kmpmlkit.imageLabeling

import com.architect.neuralKmp.typealiases.DefaultImageLabelsAction

expect class KmpMLImageLabeling {
    companion object{
        fun getLabelsFromImage(imageBuffer: ByteArray, action: DefaultImageLabelsAction)
    }
}