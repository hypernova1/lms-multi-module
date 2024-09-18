tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    compileOnly("org.springframework:spring-context")
    compileOnly("org.springframework:spring-tx")
    compileOnly("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

    implementation(project(":common"))
    implementation(project(":infrastructure"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}