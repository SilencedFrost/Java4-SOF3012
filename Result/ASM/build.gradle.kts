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

    jvmArgs = listOf(
        "-Xmx2048m",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.rmi/sun.rmi.transport=ALL-UNNAMED"
    )

    managedClassReload = false
}

dependencies {
    // Jakarta com.servlet API
    compileOnly("jakarta.servlet:jakarta.servlet-api:6.1.0")

    // Add JSTL for Jakarta EE (Tomcat 10)
    implementation("org.eclipse.jetty:glassfish-jstl:11.0.25")

    // JPA + Hibernate
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
    implementation("org.hibernate:hibernate-core:7.0.6.Final")
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen:7.0.6.Final")

    // MSSQL JDBC driver
    implementation("com.microsoft.sqlserver:mssql-jdbc:12.10.1.jre11")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")

    // JBcrypt
    implementation("org.mindrot:jbcrypt:0.4")

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