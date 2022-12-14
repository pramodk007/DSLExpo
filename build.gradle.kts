buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    val kotlin_version = "1.6.20"
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
        classpath("dev.rikka.tools.materialthemebuilder:gradle-plugin:1.3.2")
        classpath("com.android.tools.build:gradle:7.1.3")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
plugins {
    id("com.android.application") version "7.1.2" apply false
    id("com.android.library") version "7.1.2" apply false
    kotlin("android") version "1.6.20" apply false
    // Step 1, Add the plugins with the respective version
    id("io.gitlab.arturbosch.detekt") version "1.20.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }

    apply(plugin = "io.gitlab.arturbosch.detekt") // Version should be inherited from parent

    //(Optional) Step 2, Detekt closure configuration
    detekt {
        // Version of Detekt that will be used. When unspecified the latest detekt
        // version found will be used. Override to stay on the same version.
        toolVersion = "1.20.0"

        // The directories where detekt looks for source files.
        // Defaults to `files("src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin")`.
        source = files("src/main/java", "src/main/kotlin")

        // Builds the AST in parallel. Rules are always executed in parallel.
        // Can lead to speedups in larger projects. `false` by default.
        parallel = false

        // Define the detekt configuration(s) you want to use.
        // Defaults to the default detekt configuration.
        config = files("${project.rootDir}/detekt/config/config.yml")

        // Applies the config files on top of detekt's default config file. `false` by default.
        buildUponDefaultConfig = true

        // Turns on all the rules. `false` by default.
        allRules = false

        // Specifying a baseline file. All findings stored in this file in subsequent runs of detekt.
        baseline = file("${project.rootDir}/detekt/config/baseline.xml")

        // Disables all default detekt rulesets and will only run detekt with custom rules
        // defined in plugins passed in with `detektPlugins` configuration. `false` by default.
        disableDefaultRuleSets = false

        // Adds debug output during task execution. `false` by default.
        debug = false

        // If set to `true` the build does not fail when the
        // maxIssues count was reached. Defaults to `false`.
        ignoreFailures = true

        // Android: Don't create tasks for the specified build types (e.g. "release")
        ignoredBuildTypes = listOf("release")

        // Android: Don't create tasks for the specified build flavor (e.g. "production")
        ignoredFlavors = listOf("production")

        // Android: Don't create tasks for the specified build variants (e.g. "productionRelease")
        ignoredVariants = listOf("productionRelease")

        basePath = "${project.rootDir}/detekt/reports"
    }

    //(Optional) Step 3, set up report directory and file type
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
            html.outputLocation.set(file("${project.rootDir}/detekt/reports/detekt.html"))
            xml.required.set(false) // checkstyle like format mainly for integrations like Jenkins
            txt.required.set(false) // similar to the console output, contains issue signature to manually edit baseline files
            sarif.required.set(false) // standardized SARIF format (https://sarifweb.azurewebsites.net/) to support integrations with Github Code Scanning
        }
    }
}

tasks.register<Delete>("Clean") {
    delete(rootProject.buildDir)
}

//(Optional) Step 4, using Detekt type resolution
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    this.jvmTarget = "1.8"
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    this.jvmTarget = "1.8"
}