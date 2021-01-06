import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.1"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    war
    kotlin("jvm") version "1.4.21"
    kotlin("kapt") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"
}

group = "me.zhiyao"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    runtimeOnly("mysql:mysql-connector-java")
    // MyBatisPlus
    implementation("com.baomidou:mybatis-plus-boot-starter:3.4.1")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp")

    // Thumbnailator
    implementation("net.coobird:thumbnailator:0.4.13")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.11.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.11.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
