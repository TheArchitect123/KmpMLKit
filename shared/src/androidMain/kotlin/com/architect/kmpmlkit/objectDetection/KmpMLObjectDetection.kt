package com.architect.kmpmlkit.objectDetection

import com.architect.kmpmlkit.converters.byteArrayToMlImage
import com.architect.kmpmlkit.extensions.KmpMlLogger
import com.architect.kmpmlkit.objectDetection.models.BoundedBoxCoordinates
import com.architect.kmpmlkit.objectDetection.models.DetectedLabels
import com.architect.neuralKmp.typealiases.DefaultObjectLabelsAction
import com.google.mlkit.vision.objects.ObjectDetection
import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

actual class KmpMLObjectDetection {
    actual companion object {
        actual fun getLiveTrackingObjectsFromImage(
            imageBuffer: ByteArray,
            action: DefaultObjectLabelsAction
        ) {
            val options = ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
                .enableClassification()  // Optional
                .build()

            ObjectDetection.getClient(options).process(byteArrayToMlImage(imageBuffer))
                .addOnSuccessListener { detectedObjects ->
                    action(detectedObjects.map {
                        DetectedLabels(
                            it.labels.map { r -> Pair(r.text, r.confidence) },
                            BoundedBoxCoordinates(
                                it.boundingBox.left,
                                it.boundingBox.top,
                                it.boundingBox.right,
                                it.boundingBox.bottom
                            )
                        )
                    })
                }
                .addOnFailureListener {
                    KmpMlLogger.logErrorWithStackTrace(it.stackTraceToString())
                }
        }

        actual fun getSingleTrackingObjectsFromImage(
            imageBuffer: ByteArray,
            action: DefaultObjectLabelsAction
        ) {
            val options = ObjectDetectorOptions.Builder()
                .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
                .enableClassification()  // Optional
                .build()

            ObjectDetection.getClient(options).process(byteArrayToMlImage(imageBuffer))
                .addOnSuccessListener { detectedObjects ->
                    action(detectedObjects.map {
                        DetectedLabels(
                            it.labels.map { r -> Pair(r.text, r.confidence) },
                            BoundedBoxCoordinates(
                                it.boundingBox.left,
                                it.boundingBox.top,
                                it.boundingBox.right,
                                it.boundingBox.bottom
                            )
                        )
                    })
                }
                .addOnFailureListener {
                    KmpMlLogger.logErrorWithStackTrace(it.stackTraceToString())
                }
        }
    }
}