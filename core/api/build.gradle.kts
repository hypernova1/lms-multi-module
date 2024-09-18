tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation(project(":infrastructure"))
    implementation(project(":common"))
    implementation(project(":persistence"))
    implementation(project(":core:domain"))

    testImplementation("org.springframework.security:spring-security-test")
}