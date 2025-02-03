package com.architect.kmpmlkit.documentScanner.external.config

data class ScannerOptions(
    val allowGalleryImport: Boolean = true,
    val pageLimit: Int = 1,
    val scanToPdf: Boolean = false,
    val scanToImage: Boolean = true
)