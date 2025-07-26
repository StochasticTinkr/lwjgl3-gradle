package com.stochastictinkr.gradle.lwjgl

import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.linux
import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.linuxArm32
import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.linuxArm64
import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.macos
import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.macosArm64
import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.windows
import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.windowsArm64
import com.stochastictinkr.gradle.lwjgl.Platforms.Companion.windowsX86
import java.util.*

sealed interface Platforms {
    /**
     * Linux ARM64 platform.
     */
    val linuxArm64: Platform

    /**
     * Linux ARM32 platform.
     */
    val linuxArm32: Platform

    /**
     * Linux platform.
     */
    val linux: Platform

    /**
     * macOS ARM64 platform.
     */
    val macosArm64: Platform

    /**
     * macOS platform.
     */
    val macos: Platform

    /**
     * Windows ARM64 platform.
     */
    val windowsArm64: Platform

    /**
     * Windows platform.
     */
    val windows: Platform

    /**
     * Windows x86 platform.
     */
    val windowsX86: Platform

    /**
     * A list of all supported native platforms.
     * - [linuxArm64]: Linux ARM64 platform.
     * - [linuxArm32]: Linux ARM32 platform.
     * - [linux]: Linux platform.
     * - [macosArm64]: macOS ARM64 platform.
     * - [macos]: macOS platform.
     * - [windowsArm64]: Windows ARM64 platform.
     * - [windows]: Windows platform.
     * - [windowsX86]: Windows x86 platform.
     */
    val allPlatforms: List<Platform>

    /**
     * Detects the current platform based on the system properties.
     * Returns the platform if it is recognized, otherwise returns null.
     */
    val runningPlatform: Platform?

    companion object : Platforms {
        private val platformDetector = PlatformDetector()
        override val linuxArm64 = Platform("linux-arm64")
        override val linuxArm32 = Platform("linux-arm32")
        override val linux = Platform("linux")
        override val macosArm64 = Platform("macos-arm64")
        override val macos = Platform("macos")
        override val windowsArm64 = Platform("windows-arm64")
        override val windows = Platform("windows")
        override val windowsX86 = Platform("windows-x86")
        override val allPlatforms: List<Platform> = listOf(
            linuxArm64, linuxArm32, linux, macosArm64, macos, windowsArm64, windows, windowsX86
        )

        override val runningPlatform: Platform? by lazy {
            platformDetector.platformFrom(
                System.getProperty("os.arch"),
                System.getProperty("os.name").lowercase(Locale.ROOT)
            )
        }
    }
}