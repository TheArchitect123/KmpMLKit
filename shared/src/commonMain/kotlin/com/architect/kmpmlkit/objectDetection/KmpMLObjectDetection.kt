package com.architect.kmpmlkit.objectDetection

import com.architect.neuralKmp.typealiases.DefaultObjectLabelsAction

expect class KmpMLObjectDetection {
    companion object{
        fun getLiveTrackingObjectsFromImage(imageBuffer: ByteArray, action: DefaultObjectLabelsAction)
        fun getSingleTrackingObjectsFromImage(imageBuffer: ByteArray, action: DefaultObjectLabelsAction)
    }
}