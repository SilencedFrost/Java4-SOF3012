plugins {
    id("java")
    id("org.jetbrains.kotlin.plugin.jpa") version "1.9.10"
    id("war")
    id("org.gretty") version "4.1.6"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

gretty {
    contextPath = "/"
    servletContainer = "tomcat10"
    httpPort = 8080
    httpsPort = 8443

    // Optional configurations
    jvmArgs = listOf("-Xmx1024m")
    debugPort = 5005
    debugSuspend = false
}

dependencies {
    // Jakarta servlet API
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
    annotationProcessor("jakarta.servlet:jakarta.servlet-api:6.0.0")

    // Add JSTL for Jakarta EE (Tomcat 10)
    implementation("org.glassfish.web:jakarta.servlet.jsp.jstl:3.0.1")
    implementation("jakarta.servlet.jsp.jstl:jakarta.servlet.jsp.jstl-api:3.0.0")


    // JPA + Hibernate
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    implementation("org.hibernate:hibernate-core:6.2.7.Final")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.2.7.Final")

    // MSSQL JDBC driver
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

// Enable annotation processing
tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}