package com.architect.kmpmlkit.faceDetection

import com.architect.neuralKmp.typealiases.DefaultActionWithBoolParam
import com.architect.neuralKmp.typealiases.DefaultFacialFeaturesAction

actual class KmpMLFacialDetection {

    actual companion object {
        actual fun isFaceSmiling(imageData: ByteArray, action: DefaultActionWithBoolParam){

        }
        actual fun areEyesOpen(imageData: ByteArray, action: DefaultActionWithBoolParam){

        }

        actual fun startLiveStreamingFacialDetectionCoordinates(
            imageData: ByteArray,
            liveResults: DefaultFacialFeaturesAction
        ){

        }

        actual fun getFacialDetectionCoordinatesForImageBuffer(
            imageData: ByteArray,
            liveResults: DefaultFacialFeaturesAction
        ){

        }

        actual fun getFacialDetectionCoordinatesForImagePath(
            imagePath: String,
            liveResults: DefaultFacialFeaturesAction
        ){

        }
    }
}