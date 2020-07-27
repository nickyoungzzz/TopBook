package com.nick.topbook.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val format1 = "yyyy-MM-dd HH:mm:ss"
const val format2 = "yyyy/MM/dd"

fun parseDateTime2NewFormat(dateTime: String, oldFormat: String, newFormat: String): String {
	val localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(oldFormat))
	return localDateTime.format(DateTimeFormatter.ofPattern(newFormat))
}