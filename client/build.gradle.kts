tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    api("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation(project(":common"))
}