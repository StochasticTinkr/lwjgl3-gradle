package com.stochastictinkr.gradle.lwjgl

internal class PlatformDetector {
    fun platformFrom(arch: String, osName: String): Platform? {
        val isAarch64 = arch.startsWith("aarch64")
        val isWindows = osName.contains("windows")
        val isLinux = osName.contains("linux")
        val isMacos = listOf("mac os", "macos", "darwin").any(osName::contains)
        val isArch64 = "64" in arch
        val isArm = arch.startsWith("arm") || isAarch64
        return when {
            isWindows && isArch64 && !isAarch64 -> Platform.windowsArm64
            isWindows && isArch64 -> Platform.windows
            isWindows -> Platform.windowsX86

            isLinux && isArm && (isArch64 || arch.startsWith("armv8")) -> Platform.linuxArm64
            isLinux && isArm -> Platform.linuxArm32
            isLinux -> Platform.linux

            isMacos && isAarch64 -> Platform("macos-arm64")
            isMacos -> Platform.macos

            else -> null
        }
    }
}