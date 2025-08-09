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
            isWindows && isArch64 && isAarch64 -> Platforms.windowsArm64
            isWindows && isArch64 -> Platforms.windows
            isWindows -> Platforms.windowsX86

            isLinux && isArm && (isArch64 || arch.startsWith("armv8")) -> Platforms.linuxArm64
            isLinux && isArm -> Platforms.linuxArm32
            isLinux -> Platforms.linux

            isMacos && isAarch64 -> Platform("macos-arm64")
            isMacos -> Platforms.macos

            else -> null
        }
    }
}