plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.glassfish.tyrus.bundles:tyrus-standalone-client:1.9")
    //implementation("compile group: 'org.eclipse.scout.rt', name: 'org.eclipse.scout.json', version: '23.2.15'")
    implementation("org.json:json:20230227")
}

tasks.test {
    useJUnitPlatform()
}