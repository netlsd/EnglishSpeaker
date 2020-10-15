package com.me.englishspeaker

import android.content.Context
import java.io.File
import java.io.FileInputStream

fun Context.getWordsFile(): File {
    return File("${filesDir}/words.txt")
}

fun Context.readWordsFromFile(): String {
    val wordsFile = getWordsFile()
    if (!wordsFile.exists()) {
        return ""
    }
    val stream = FileInputStream(wordsFile)
    stream.buffered().reader().use { reader ->
        return reader.readText()
    }
}

fun Context.saveWordsToFile(words: String) {
    val wordsFile = getWordsFile()
    wordsFile.writeText(words)
}

