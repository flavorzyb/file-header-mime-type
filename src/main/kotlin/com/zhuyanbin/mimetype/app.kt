package com.zhuyanbin.mimetype

fun main() {
    val mimeTypeList = ExcelReader.getMimeTypeList()
    val fileHeaderList = ExcelReader.getFileHeaderList()
    val formatFileHeader = Formatter.formatFileHeaderList(fileHeaderList)
    Formatter.printFileHeaderAndMimeType(formatFileHeader, mimeTypeList)
}