package com.architect.kmpmlkit.faceDetection

import com.architect.kmpmlkit.converters.byteArrayToMlImage
import com.architect.kmpmlkit.converters.imageFileToByteArray
import com.architect.kmpmlkit.extensions.KmpMlLogger
import com.architect.neuralKmp.typealiases.DefaultActionWithBoolParam
import com.architect.neuralKmp.typealiases.DefaultFacialFeaturesAction
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceDetectorOptions.CONTOUR_MODE_ALL

actual class KmpMLFacialDetection {
    actual companion object {
        actual fun isFaceSmiling(imageData: ByteArray, action: DefaultActionWithBoolParam) {
            processImageForAccuracy(
                imageData,
                FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE,
            ) {
                action(it.any { q -> q.isSmiling })
            }
        }

        actual fun areEyesOpen(imageData: ByteArray, action: DefaultActionWithBoolParam) {
            processImageForAccuracy(
                imageData,
                FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE,
            ) {
                action(it.any { q -> q.isEyesClosed })
            }
        }

        actual fun startLiveStreamingFacialDetectionCoordinates(
            imageData: ByteArray,
            liveResults: DefaultFacialFeaturesAction
        ) {
            processImageForAccuracy(
                imageData,
                FaceDetectorOptions.PERFORMANCE_MODE_FAST,
                liveResults
            )
        }

        actual fun getFacialDetectionCoordinatesForImageBuffer(
            imageData: ByteArray,
            liveResults: DefaultFacialFeaturesAction
        ) {
            processImageForAccuracy(
                imageData,
                FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE,
                liveResults
            )
        }

        actual fun getFacialDetectionCoordinatesForImagePath(
            imagePath: String,
            liveResults: DefaultFacialFeaturesAction
        ) {
            processImageForAccuracy(
                imageFileToByteArray(imagePath),
                FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE,
                liveResults
            )
        }

        private fun processImageForAccuracy(
            imageData: ByteArray,
            performanceMode: Int,
            liveResults: DefaultFacialFeaturesAction,
        ) {
            val fastPerformance = FaceDetectorOptions.Builder()
                .setPerformanceMode(performanceMode)
                .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                .setContourMode(CONTOUR_MODE_ALL)
                .build()

            FaceDetection.getClient(fastPerformance).process(byteArrayToMlImage(imageData))
                .addOnSuccessListener { faces ->
                    CoordinatesTransformer.processFace(faces, liveResults)
                }
                .addOnFailureListener {
                    KmpMlLogger.logErrorWithStackTrace(it.stackTraceToString())
                }
        }
    }
}

