/**Copyright 2025 Forge-of-Ovorldule (https://github.com/Forge-of-Ovorldule) and Mr-Soul-Forest (https://github.com/Mr-Soul-Forest)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package forgeofovorldule.anvilory

import kotlinx.browser.localStorage

actual fun closeSaveFiles() {}
actual fun openSaveFiles() {}

actual fun saveValue(value: Any, name: String) {
    val serialized = value.savedElementToString()
    localStorage.setItem(name, serialized)
}

actual fun <T> loadValue(value: T, name: String): T {
    val serialized = localStorage.getItem(name) ?: return value
    return serialized.loadedElementToVal(value)
}

actual fun deleteValue(name: String) {
    localStorage.removeItem(name)
}