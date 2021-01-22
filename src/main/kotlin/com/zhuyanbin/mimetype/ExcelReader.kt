package com.zhuyanbin.mimetype

import com.alibaba.excel.EasyExcel

object ExcelReader {
    fun getMimeTypeList(): List<MimeType> {
        val excelInputSystem = javaClass.getResourceAsStream(Config.EXCEL_FILE_PATH)
        val reader = EasyExcel.read(excelInputSystem)
        val mimeTypeList = reader.sheet(Config.EXCEL_MIME_SHEET_NAME).doReadSync<MimeType>()
        excelInputSystem.close()
        val result = ArrayList<MimeType>()
        mimeTypeList.filter { it.extension.trim().isNotEmpty() }.forEach {
            result.add(MimeType(it.extension.trim(), it.extension.trim()))
        }

        return result
    }

    private fun fillFileHeader(nameList: List<String>, header: FileHeader): List<FileHeader> {
        val result = ArrayList<FileHeader>()
        nameList.forEach { name ->
            run {
                val str = name.trim()
                if (str.isNotEmpty()) {
                    result.add(FileHeader(name = str, hex = header.hex.trim(), description = header.description.trim()))
                }
            }
        }
        return result
    }

    fun getFileHeaderList(): List<FileHeader> {
        val excelInputSystem = javaClass.getResourceAsStream(Config.EXCEL_FILE_PATH)
        val reader = EasyExcel.read(excelInputSystem)
        val fileHeaderList = reader.sheet(Config.EXCEL_HEADER_SHEET_NAME).doReadSync<FileHeader>()
        excelInputSystem.close()
        val result = ArrayList<FileHeader>()

//        fileHeaderList.filter { it.name.trim().isNotEmpty() }.forEach { println(it) }

        return result
    }
}