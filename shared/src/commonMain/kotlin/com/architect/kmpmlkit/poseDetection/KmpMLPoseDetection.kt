package com.architect.neuralKmp.poseDetection

expect class KmpMLPoseDetection {
    companion object {
        fun startStreamingPoseDetector(bufferImage: ByteArray)
        fun stopStreamingPoseDetector()
    }
}