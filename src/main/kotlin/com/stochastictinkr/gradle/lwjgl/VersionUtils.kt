package com.stochastictinkr.gradle.lwjgl

internal object VersionUtils {
    private val versionRegex = Regex("""(\d+)\.(\d+)(?:\.(\d+))?(-SNAPSHOT)?""")

    fun meetsVersionRequirement(
        minVersion: String,
        selectedVersion: String,
    ): Boolean {
        val (majorMin, minorMin, patchMin, snapshotMin) = versionRegex.matchEntire(minVersion)?.destructured
            ?: return false
        val (majorActual, minorActual, patchActual, snapshotActual) = versionRegex.matchEntire(selectedVersion)?.destructured
            ?: return false

        val minMajor = majorMin.toInt()
        val minMinor = minorMin.toInt()
        val minPatch = patchMin.toInt()
        val actualMajor = majorActual.toInt()
        val actualMinor = minorActual.toInt()
        val actualPatch = patchActual.toInt()
        // Snapshots are considered lower than the release version
        val minSnapshot = if (snapshotMin.isNotEmpty()) 0 else 1
        val actualSnapshot = if (snapshotActual.isNotEmpty()) 0 else 1
        return when {
            actualMajor > minMajor -> true
            actualMajor < minMajor -> false
            actualMinor > minMinor -> true
            actualMinor < minMinor -> false
            actualPatch > minPatch -> true
            actualPatch < minPatch -> false
            else -> actualSnapshot >= minSnapshot
        }
    }

}