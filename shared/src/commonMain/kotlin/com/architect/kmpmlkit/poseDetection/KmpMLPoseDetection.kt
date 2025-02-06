package com.architect.neuralKmp.poseDetection

import com.architect.neuralKmp.typealiases.DefaultPoseFeaturesAction

expect class KmpMLPoseDetection {
    companion object {
        fun getFromLiveStreamingPoseDetector(bufferImage: ByteArray, action: DefaultPoseFeaturesAction)
        fun getSinglePoseFromStaticImage(bufferImage: ByteArray, action: DefaultPoseFeaturesAction)
    }
}