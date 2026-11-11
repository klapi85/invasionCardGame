plugins {
    kotlin("jvm") version "1.9.24"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
}

kotlin {
    jvmToolchain(21)
}

// Sources don't follow the Maven layout: game code is in src/game, tests in src/test
sourceSets {
    main {
        kotlin.setSrcDirs(listOf("src"))
        kotlin.exclude("test/**")
    }
    test {
        kotlin.setSrcDirs(listOf("src/test"))
    }
}

application {
    mainClass.set("game.LauncherKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// Self-contained runnable jar: java -jar build/libs/invasion.jar
tasks.jar {
    archiveFileName.set("invasion.jar")
    manifest {
        attributes["Main-Class"] = "game.LauncherKt"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
