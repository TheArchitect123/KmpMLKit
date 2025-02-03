package com.architect.neuralKmp.barcode

data class BarcodeScannerOptions(
    val defaultBarcodeType : Int = BarcodeTypes.FORMAT_QR_CODE,
    val barcodeType: List<Int> = listOf(BarcodeTypes.FORMAT_QR_CODE),

    )