package com.zhuyanbin.mimetype

import com.alibaba.excel.EasyExcel

object ExcelReader {
    fun getMimeTypeList(): List<MimeType> {
        val excelInputSystem = javaClass.getResourceAsStream(Config.EXCEL_FILE_PATH)
        val mimeTypeList =  EasyExcel.read(excelInputSystem).head(MimeType::class.java).sheet(Config.EXCEL_MIME_SHEET_NAME).doReadSync<MimeType>()
        excelInputSystem.close()
        val result = ArrayList<MimeType>()
        mimeTypeList.filter { it.extension.trim().isNotEmpty() }
            .stream()
            .forEach {
            result.add(MimeType(it.extension.trim(), it.mimeType.trim()))
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
        val fileHeaderList = EasyExcel.read(excelInputSystem).head(FileHeader::class.java).sheet(Config.EXCEL_HEADER_SHEET_NAME).doReadSync<FileHeader>()
        excelInputSystem.close()
        val result = ArrayList<FileHeader>()

        fileHeaderList.filter { it.name.trim().isNotEmpty() }
            .forEach {
                val nameList = when {
                    it.name.indexOf(";") != -1 -> it.name.split(";")
                    it.name.indexOf(",") != -1 -> it.name.split(",")
                    else -> arrayListOf(it.name)
                }

                result.addAll(fillFileHeader(nameList, it))
            }

        return result
    }
}