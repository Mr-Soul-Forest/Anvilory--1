/**Copyright 2025 Forge-of-Ovorldule (https://github.com/Forge-of-Ovorldule) and Mr-Soul-Forest (https://github.com/Mr-Soul-Forest)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package forgeofovorldule.anvilory

import java.io.File
import kotlinx.serialization.json.*

private val json = Json { prettyPrint = true }
private val settingsFile = File("$save_file_name.json")

private var savedStrings: MutableMap<String, JsonElement> = readSettings().toMutableMap()

actual fun openSaveFiles() {
    savedStrings = readSettings().toMutableMap()
}

actual fun closeSaveFiles() {
    settingsFile.writeText(json.encodeToString(JsonObject(savedStrings)))
}

private fun readSettings(): MutableMap<String, JsonElement> {
    if (!settingsFile.exists()) return mutableMapOf()
    val text = settingsFile.readText()
    if (text.isBlank()) return mutableMapOf()
    return json.parseToJsonElement(text).jsonObject.toMutableMap()
}

actual fun saveValue(value: Any, name: String) {
    val element = JsonPrimitive(value.savedElementToString())
    savedStrings[name] = element
}

actual fun <T> loadValue(value: T, name: String): T {
    val settings = readSettings()
    val jsonElement = settings[name] ?: return value
    var element = jsonElement.jsonPrimitive.toString()
    element = element.substring(1, element.length - 1)

    return element.loadedElementToVal(value)
}

actual fun deleteValue(name: String) {
    val settings = readSettings().toMutableMap()
    settings.remove(name)
    settingsFile.writeText(json.encodeToString(JsonObject(settings)))
}