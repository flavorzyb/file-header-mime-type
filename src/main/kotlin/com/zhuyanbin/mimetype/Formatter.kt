package com.zhuyanbin.mimetype

import java.lang.RuntimeException

object Formatter {
    private fun formatHexString(data: String): String {
        return when {
            data.indexOf(" ") != -1 -> {
                val strArray = data.split(" ")
                val result = StringBuilder()
                strArray.filter { it.trim().isNotEmpty() }.forEach {
                    try {
                        val value = it.trim().toInt(16)
                        val str = value.toString(16)
                        val hexStr = when (str.length > 1) {
                            true -> str
                            else -> "0${str}"
                        }
                        val prefix = when (value > 128) {
                            true -> "(byte)"
                            else -> ""
                        }
                        result.append("${prefix}0x${hexStr.toUpperCase()}, ")
                    } catch (e: Exception) {
                        result.append("0x--, ")
                    }
                }
                if (result.length > 2) {
                    result.substring(0, result.length - 2)
                } else {
                    result.toString()
                }
            }
            else -> "0x" + data.trim()
        }
    }
    fun formatFileHeaderList(data: List<FileHeader>): List<FileHeader> {
        data.forEach { it.hex = formatHexString(it.hex) }
        return data
    }


    private fun toMimeTypeMap(mimeTypeList: List<MimeType>): Map<String, String> {
        val result = HashMap<String, String>()
        mimeTypeList.forEach {
            val extName = it.extension.trim().toLowerCase()
            if (result.containsKey(extName)) {
                throw RuntimeException("key(${extName}) is exists.")
            }

            result[extName] = it.mimeType
        }

        return result
    }
    fun printFileHeaderAndMimeType(fileHeaderList: List<FileHeader>, mimeTypeList: List<MimeType>) {
        val mimeTypeMap = toMimeTypeMap(mimeTypeList)
        fileHeaderList.stream().sorted { o1, o2 ->  o1.name.compareTo(o2.name) }.forEach {
            val extName = it.name
            val mimeType = mimeTypeMap[extName.toLowerCase()]
            println("TYPE_${extName.toUpperCase()}(\"${extName.toLowerCase()}\", new byte[] { ${it.hex} }, \"${mimeType ?: "application/octet-stream"}\"),")
        }
    }
}