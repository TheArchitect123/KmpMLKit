package com.architect.kmpmlkit.converters

import com.google.mlkit.vision.common.InputImage
import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

fun byteArrayToMlImage(byteArray: ByteArray): InputImage {
    return InputImage.fromByteArray(
        byteArray,
        640, // Width
        480, // Height
        0,   // Rotation
        InputImage.IMAGE_FORMAT_NV21 // Adjust if needed
    )
}

fun imageFileToByteArray(filePath: String): ByteArray {
    val file = File(filePath)
    return file.readBytes()
}

fun getByteBufferFromFile(filePath: String): ByteBuffer {
    val file = File(filePath)
    val randomAccessFile = RandomAccessFile(file, "r")
    val fileChannel = randomAccessFile.channel
    val byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size())

    fileChannel.close()
    randomAccessFile.close()

    return byteBuffer
}