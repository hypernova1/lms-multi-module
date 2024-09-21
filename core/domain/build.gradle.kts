tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    compileOnly("org.springframework:spring-context")
    compileOnly("org.springframework:spring-tx")

    implementation(project(":common"))
    implementation(project(":infrastructure"))
}
