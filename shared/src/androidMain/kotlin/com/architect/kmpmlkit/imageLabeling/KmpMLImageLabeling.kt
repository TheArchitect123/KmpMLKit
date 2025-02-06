package com.architect.kmpmlkit.imageLabeling

import com.architect.kmpmlkit.converters.byteArrayToMlImage
import com.architect.kmpmlkit.imageLabeling.models.ImageLabels
import com.architect.neuralKmp.typealiases.DefaultImageLabelsAction
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

actual class KmpMLImageLabeling {
    actual companion object {
        actual fun getLabelsFromImage(imageBuffer: ByteArray, action: DefaultImageLabelsAction) {
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
            labeler.process(byteArrayToMlImage(imageBuffer))
                .addOnSuccessListener { labels ->
                    action(labels.map {
                        ImageLabels(it.text, it.confidence)
                    })
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }
        }
    }
}