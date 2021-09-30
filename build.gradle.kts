plugins {
    java
    id("io.izzel.taboolib") version "1.27"
    id("org.jetbrains.kotlin.jvm") version "1.5.10"
}

taboolib {
    install("common")
    install("common-5")
    install("module-chat")
    install("module-configuration")
    install("module-lang")
    install("module-ui")
    install("expansion-command-helper")
    install("platform-bukkit")

    description {
        contributors {
            name("GalaxyVN")
        }
        dependencies {
            name("LuckPerms").optional(false)
        }
    }

    classifier = null
    version = "6.0.3-5"
}

repositories {
    mavenCentral()
}

dependencies {

    // Libraries
    compileOnly(kotlin("stdlib"))

    // Server Core
    compileOnly("ink.ptms:nms-all:1.0.0")
    compileOnly("ink.ptms.core:v11701:11701:mapped")
    compileOnly("ink.ptms.core:v11701:11701:universal")

    // Hook Plugins
    compileOnly("net.luckperms:api:5.3")

    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}