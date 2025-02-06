package com.architect.neuralKmp.poseDetection

import com.architect.kmpmlkit.converters.byteArrayToMlImage
import com.architect.kmpmlkit.extensions.KmpMlLogger
import com.architect.kmpmlkit.poseDetection.models.PoseCoordinates
import com.architect.kmpmlkit.poseDetection.models.PoseLandmarks
import com.architect.kmpmlkit.poseDetection.models.PosePosition
import com.architect.neuralKmp.typealiases.DefaultPoseFeaturesAction
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions

actual class KmpMLPoseDetection {
    actual companion object {
        actual fun getFromLiveStreamingPoseDetector(
            bufferImage: ByteArray,
            action: DefaultPoseFeaturesAction
        ) {
            runPoseMode(bufferImage, PoseDetectorOptions.STREAM_MODE, action)
        }

        actual fun getSinglePoseFromStaticImage(
            bufferImage: ByteArray,
            action: DefaultPoseFeaturesAction
        ) {
            runPoseMode(bufferImage, PoseDetectorOptions.SINGLE_IMAGE_MODE, action)
        }

        private fun runPoseMode(
            bufferImage: ByteArray,
            poseMode: Int,
            action: DefaultPoseFeaturesAction
        ) {
            val options = PoseDetectorOptions.Builder()
                .setDetectorMode(poseMode)
                .setPreferredHardwareConfigs(PoseDetectorOptions.CPU_GPU)
                .build()

            PoseDetection.getClient(options).process(byteArrayToMlImage(bufferImage))
                .addOnSuccessListener { pose ->
                    action(
                        pose.allPoseLandmarks.map {
                            getPoseForLandmark(
                                it.landmarkType,
                                PoseLandmarks.fromValue(it.landmarkType)!!,
                                pose
                            )
                        })
                }
                .addOnFailureListener {
                    KmpMlLogger.logErrorWithStackTrace(it.stackTraceToString())
                }
        }

        private fun getPoseForLandmark(
            landmark: Int,
            coreLandmark: PoseLandmarks,
            pose: Pose
        ): PosePosition {
            val cln = pose.getPoseLandmark(landmark)

            return PosePosition(
                coordinates = PoseCoordinates(
                    cln?.position3D?.x ?: 0f,
                    cln?.position3D?.y ?: 0f,
                    cln?.position3D?.z ?: 0f,
                    cln?.inFrameLikelihood ?: 0f
                ), landmark = coreLandmark
            )
        }

    }
}