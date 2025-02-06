package com.architect.kmpmlkit.faceDetection

import com.architect.kmpmlkit.faceDetection.models.EulerCoordinates
import com.architect.kmpmlkit.faceDetection.models.FacialCoordinates
import com.architect.kmpmlkit.faceDetection.models.FacialLandmarks
import com.architect.kmpmlkit.faceDetection.models.LatitudeLongitude
import com.architect.neuralKmp.typealiases.DefaultFacialFeaturesAction
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceLandmark

internal object CoordinatesTransformer {

    private fun getLandmarkType(landmark: Int): FacialLandmarks {
        return when (landmark) {
            0 -> FacialLandmarks.MOUTHBOTTOM
            11 -> FacialLandmarks.MOUTHRight
            5 -> FacialLandmarks.MOUTHLeft
            4 -> FacialLandmarks.LeftEye
            10 -> FacialLandmarks.RightEye
            3 -> FacialLandmarks.LeftEar
            9 -> FacialLandmarks.RightEar
            1 -> FacialLandmarks.LeftCheek
            7 -> FacialLandmarks.RightCheek
            else -> FacialLandmarks.NoseBridge
        }
    }

    private fun getFaceCoordinatesForPosition(landmark: Int, face: Face): FacialCoordinates {
        val section = face.getLandmark(landmark)
        val coords = section?.position

        return FacialCoordinates(
            coordinates = LatitudeLongitude(
                latitude = coords?.x ?: 0f,
                longitude = coords?.y ?: 0f
            ),
            facialFeature = getLandmarkType(landmark),
            isSmiling = (face.smilingProbability ?: 0f) >= 0.5,
            isEyesClosed = (face.leftEyeOpenProbability
                ?: 0f) >= 0.5 && (face.rightEyeOpenProbability ?: 0f) >= 0.5
        )
    }

    fun processFace(faces: List<Face>, liveResults: DefaultFacialFeaturesAction) {
        val faceCoordinateItems = mutableListOf<FacialCoordinates>()
        for (face in faces) {
            // face section
            val rotX = face.headEulerAngleX // Head is rotated to the right rotY degrees
            val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
            val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees
            faceCoordinateItems.add(
                FacialCoordinates(
                    eulerCoordinates = EulerCoordinates(
                        rotX,
                        rotY,
                        rotZ
                    ),
                    facialFeature = FacialLandmarks.Head
                )
            )

            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.LEFT_EAR, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.RIGHT_EAR, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.LEFT_EYE, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.RIGHT_EYE, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.LEFT_CHEEK, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.RIGHT_CHEEK, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.MOUTH_LEFT, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.MOUTH_RIGHT, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.MOUTH_BOTTOM, face))
            faceCoordinateItems.add(getFaceCoordinatesForPosition(FaceLandmark.NOSE_BASE, face))
        }

        liveResults(faceCoordinateItems)
    }
}