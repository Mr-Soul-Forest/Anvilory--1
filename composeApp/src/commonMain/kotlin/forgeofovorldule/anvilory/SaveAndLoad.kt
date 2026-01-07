/**Copyright 2025 Forge-of-Ovorldule (https://github.com/Forge-of-Ovorldule) and Mr-Soul-Forest (https://github.com/Mr-Soul-Forest)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package forgeofovorldule.anvilory

import androidx.compose.ui.graphics.Color
import kotlinx.datetime.LocalDate

private fun saveSystem(
    saves: () -> Unit
) {
    openSaveFiles()
    saves()
    closeSaveFiles()
}

expect fun openSaveFiles()
expect fun closeSaveFiles()

expect fun saveValue(value: Any, name: String)

fun Any.savedElementToString(): String {
    return when (val value = this) {
        is Color -> value.value.toString(16)
        else -> value.toString()
    }
}

fun saveAllValues() {
    saveSystem {
        saveAllValuesProcess()
    }
}

fun saveAllValuesProcess() {
    saveValue(app_version, "app_version")
    saveValue(language, "language")
    saveValue(plots.size, "plots-size")
    for (x in 0..<plots.size) {
        saveValue(plots[x].title, "plots-$x--title")
        saveValue(plots[x].description, "plots-$x--description")
        saveValue(plots[x].typeOfPlot, "plots-$x--typeOfPlot")
        saveValue(plots[x].lastEdit, "plots-$x--lastEdit")
        saveValue(plots[x].chapters.size, "plots-$x--chapters-size")
        for (y in 0..<plots[x].chapters.size) {
            saveValue(plots[x].chapters[y].title, "plots-$x--chapters-$y--title")
            saveValue(plots[x].chapters[y].lastEdit, "plots-$x--chapters-$y--lastEdit")
            saveValue(plots[x].chapters[y].parts.size, "plots-$x--chapters-$y--parts-size")
            for (z in 0..<plots[x].chapters[y].parts.size) {
                saveValue(plots[x].chapters[y].parts[z].text, "plots-$x--chapters-$y--parts-$z--text")
                saveValue(plots[x].chapters[y].parts[z].lastEdit, "plots-$x--chapters-$y--parts-$z--lastEdit")
            }
        }
    }
}

expect fun <T> loadValue(value: T, name: String): T

fun <T> String.loadedElementToVal(value: T): T {
    val element = this
    return when (value) {
        is Long -> element.toLongOrNull() ?: value
        is Int -> element.toIntOrNull() ?: value
        is String -> element
        is Color -> Color(element.toULongOrNull(16) ?: "ffffffff00000000".toULong(16))
        is Boolean -> element.toBoolean()
        is LocalDate -> element.let { LocalDate.parse(it) }
        is Languages -> enumValueOf<Languages>(element)
        is AppStatus -> enumValueOf<AppStatus>(element)
        else -> value
    } as T
}

fun loadAllValues() {
    val oldAppVersion = loadValue(app_version * (-1), "app_version")
    language = loadValue(language, "language")
    val plotsSize = loadValue(plots.size, "plots-size")
    plots = mutableListOf(Plot())
    for (x in 0..<plotsSize) {
        plots[x].title = loadValue(plots[x].title, "plots-$x--title")
        plots[x].description = loadValue(plots[x].description, "plots-$x--description")
        plots[x].typeOfPlot = loadValue(plots[x].typeOfPlot, "plots-$x--typeOfPlot")
        plots[x].lastEdit = loadValue(plots[x].lastEdit, "plots-$x--lastEdit")
        val chaptersSize = loadValue(plots[x].chapters.size, "plots-$x--chapters-size")
        plots[x].chapters = mutableListOf(Chapter())
        for (y in 0..<chaptersSize) {
            plots[x].chapters[y].title = loadValue(plots[x].chapters[y].title, "plots-$x--chapters-$y--title")
            plots[x].chapters[y].lastEdit = loadValue(plots[x].chapters[y].lastEdit, "plots-$x--chapters-$y--lastEdit")
            val partsSize = loadValue(plots[x].chapters[y].parts.size, "plots-$x--chapters-$y--parts-size")
            plots[x].chapters[y].parts = mutableListOf(Part())
            for (z in 0..<partsSize) {
                plots[x].chapters[y].parts[z].text =
                    loadValue(plots[x].chapters[y].parts[z].text, "plots-$x--chapters-$y--parts-$z--text")
                plots[x].chapters[y].parts[z].lastEdit =
                    loadValue(plots[x].chapters[y].parts[z].lastEdit, "plots-$x--chapters-$y--parts-$z--lastEdit")
                if (z != partsSize - 1) plots[x].chapters[y].parts.add(Part())
            }
            if (y != chaptersSize - 1) plots[x].chapters.add(Chapter())
        }
        if (x != plotsSize - 1) plots.add(Plot())
    }
}

expect fun deleteValue(name: String)
