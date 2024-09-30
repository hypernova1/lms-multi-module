tasks.getByName("bootJar") {
    enabled = false
}

tasks.getByName("jar") {
    enabled = true
}

dependencies {
    api("org.springframework.retry:spring-retry")
    implementation("org.redisson:redisson-spring-boot-starter:3.36.0")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.kafka:spring-kafka")


    compileOnly("org.springframework:spring-tx")

    implementation(project(":common"))
}