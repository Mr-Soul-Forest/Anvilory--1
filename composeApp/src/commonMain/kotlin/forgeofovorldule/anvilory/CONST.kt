/**Copyright 2025 Forge-of-Ovorldule (https://github.com/Forge-of-Ovorldule) and Mr-Soul-Forest (https://github.com/Mr-Soul-Forest)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package forgeofovorldule.anvilory

const val app_version = 0 //0.0.0.0

const val save_file_name = "anvilory-save-by-forge-of-ovorldule"

enum class AppStatus {
    LOADING,
    PLOTS,
    PLOTS_UPDATER,
    CHAPTERS,
    CHAPTERS_UPDATER,
    PARTS,
    PARTS_UPDATER
}

var language: Languages = Languages.EN

var plots = mutableListOf(Plot())