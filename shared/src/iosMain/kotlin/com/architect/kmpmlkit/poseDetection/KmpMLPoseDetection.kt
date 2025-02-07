package com.architect.neuralKmp.poseDetection

import com.architect.neuralKmp.typealiases.DefaultPoseFeaturesAction

actual class KmpMLPoseDetection {
    actual companion object {
        actual fun getFromLiveStreamingPoseDetector(
            bufferImage: ByteArray,
            action: DefaultPoseFeaturesAction
        ) {

        }

        actual fun getSinglePoseFromStaticImage(
            bufferImage: ByteArray,
            action: DefaultPoseFeaturesAction
        ) {

        }
    }
}