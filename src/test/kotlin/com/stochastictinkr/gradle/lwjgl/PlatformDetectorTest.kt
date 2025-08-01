package com.stochastictinkr.gradle.lwjgl

import kotlin.test.*

internal class PlatformDetectorTest {
    val platformDetector = PlatformDetector()

    private fun assertPlatformFrom(expected: Platform?, osName: String, osArch: String) {
        assertEquals(expected, platformDetector.platformFrom(osArch, osName),
            "Expected platformFrom to return $expected for osName=$osName and osArch=$osArch")
    }

    @Test
    fun `platformFrom on linux-arm64`() {
        assertPlatformFrom(Platforms.linuxArm64, "linux", "aarch64")
    }

    @Test
    fun `platformFrom on linux-arm32`() {
        assertPlatformFrom(Platforms.linuxArm32, "linux", "armv7l")
    }

    @Test
    fun `platformFrom on linux`() {
        assertPlatformFrom(Platforms.linux, "linux", "x86_64")
    }

    @Test
    fun `platformFrom on macos-arm64`() {
        assertPlatformFrom(Platforms.macosArm64, "mac os", "aarch64")
    }

    @Test
    fun `platformFrom on macos`() {
        assertPlatformFrom(Platforms.macos, "macos", "x86_64")
    }

    @Test
    fun `platformFrom on windows-arm64`() {
        assertPlatformFrom(Platforms.windowsArm64, "windows", "Arm64")
    }

    @Test
    fun `platformFrom on windows`() {
        assertPlatformFrom(Platforms.windows, "windows", "aarch64")
    }

    @Test
    fun `platformFrom on windows-x86`() {
        assertPlatformFrom(Platforms.windowsX86, "windows", "i386")
    }

    @Test
    fun `platformFrom on unknown platform`() {
        assertPlatformFrom(null, "unknown", "unknown")
    }

}