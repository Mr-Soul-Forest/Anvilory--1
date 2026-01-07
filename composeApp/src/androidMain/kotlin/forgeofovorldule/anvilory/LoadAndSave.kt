/**Copyright 2025 Forge-of-Ovorldule (https://github.com/Forge-of-Ovorldule) and Mr-Soul-Forest (https://github.com/Mr-Soul-Forest)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import kotlinx.datetime.LocalDate
import androidx.compose.ui.graphics.Color
import androidx.core.content.edit

@SuppressLint("StaticFieldLeak")
var context: Context? = null

fun initStorage(appContext: Context) {
    context = appContext
}

private val prefs: SharedPreferences
    get() = context!!.getSharedPreferences(save_file_name, Context.MODE_PRIVATE)
private lateinit var editor: SharedPreferences.Editor

actual fun openSaveFiles() {
    editor = prefs.edit()
}

actual fun closeSaveFiles() {
    editor.apply()
}

actual fun saveValue(value: Any, name: String) {
    val serialized = value.savedElementToString()
    editor.putString(name, serialized)
}

actual fun <T> loadValue(value: T, name: String): T {
    val serialized =
        context!!.getSharedPreferences(save_file_name, Context.MODE_PRIVATE).getString(name, null)
            ?: return value

    return serialized.loadedElementToVal(value)
}

actual fun deleteValue(name: String) {
    prefs.edit { remove(name) }
}