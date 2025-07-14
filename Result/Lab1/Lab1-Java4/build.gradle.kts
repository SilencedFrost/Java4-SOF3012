plugins {
    id("java")
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // JPA + Hibernate
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate:hibernate-core:6.2.7.Final")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.2.7.Final")

    // MSSQL JDBC driver
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}

// Enable annotation processing
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}