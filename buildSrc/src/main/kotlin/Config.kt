object Config {
    serviceAccountEmail = getChatBotServiceAccountKey()
    fabricApiKey = getFabricApiKey()
    rollbarAccessToken = getRollbarAccessToken()

    /**
     * Set this to true to create two separate APKs instead of one:
     *   - An APK that only works on ARM devices
     *   - An APK that only works on x86 devices
     * The advantage is the size of the APK is reduced by about 4MB.
     * Upload all the APKs to the Play Store and people will download
     * the correct one based on the CPU architecture of their device.
     */
    val enableSeparateBuildPerCPUArchitecture = true

    // If true, also generate a universal APK
    const val enableUniversalApk = true

    // ABI codes
    val abiCodes = hashMapOf("armeabi-v7a": 1, "x86": 2, "mips": 3, "x86_64": 4, "armeabi": 5, "arm64-v8a": 6)

    // For per-density APKs, create a similar map like this:
    densityCodes = ["ldpi": 1, 'mdpi': 2, 'hdpi': 3, 'xhdpi': 4, "xxhdpi": 5, "xxxhdpi": 6]

    // -------------------- DEPENDENCIES --------------------

    // support lib
    supportLibVersion = "27.0.2"

    // multidex
    multiDexEnabledVersion = "1.0.2"

    // Dependency Injection
    javaxAnnotationVersion = "1.0"
    daggerVersion = "2.11"
    javaxAnnotationVersion = "1.0"
    javaxInjectVersion = "1"

    // -------------------- TEST DEPENDENCIES -----------------------
    jUnitVersion = "4.12"
    espressoVersion = "3.0.1"
    truthVersion = "0.35"
    mockitoVersion = "2.7.1"
    robolectricVersion = "3.3.2"
    testRunnerVersion = "1.0.1"
    espressoContribVersion = "2.0"
    assertJVersion = "3.6.2"
    mockitoKotlinVersion = "1.4.0"
    hamcrestVersion = "1.3"
}