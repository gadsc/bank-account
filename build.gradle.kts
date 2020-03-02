plugins {
    application
    id("org.jetbrains.kotlin.jvm").version("1.3.11")
    id("com.github.johnrengelman.shadow").version("5.2.0")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.2")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("io.mockk:mockk:1.9.1")
}

application {
    mainClassName = "br.com.bank.BankApplicationKt"
}

val run by tasks.getting(JavaExec::class) {
    standardInput = System.`in`
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set(project.name)
    archiveClassifier.set("")
    archiveVersion.set("")


    manifest {
        attributes["Main-Class"] = application.mainClassName
    }
}
