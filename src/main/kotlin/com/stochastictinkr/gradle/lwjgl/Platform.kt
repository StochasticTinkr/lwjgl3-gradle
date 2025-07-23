package com.stochastictinkr.gradle.lwjgl

data class Platform(val name: String) {
    companion object {
        val linuxArm64 = Platform("linux-arm64")
        val linuxArm32 = Platform("linux-arm32")
        val linux = Platform("linux")
        val macosArm64 = Platform("macos-arm64")
        val macos = Platform("macos")
        val windowsArm64 = Platform("windows-arm64")
        val windows = Platform("windows")
        val windowsX86 = Platform("windows-x86")
    }
}