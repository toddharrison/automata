plugins {
    id("java")
    id("com.vanniktech.maven.publish") version "0.34.0"
}

group = "com.eharrison"
version = "1.0.5"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.mockito:mockito-core:5.17.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.17.0")
}

tasks.jar {
    manifest {
        attributes(
            "Main-Class" to "com.eharrison.automata.ui.Application"
        )
    }
}

tasks.test {
    useJUnitPlatform()
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
}

mavenPublishing {
    coordinates(
        project.group.toString(),
        project.name,
        project.version.toString()
    )

    pom {
        name.set(project.name)
        description.set("A framework for building automata bots used to compete in simple games as coding challenges.")
        inceptionYear.set("2025")
        url.set("https://github.com/toddharrison/automata/")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                id.set("toddharrison")
                name.set("Todd Harrison")
                url.set("https://github.com/toddharrison/")
            }
        }
        scm {
            url.set("https://github.com/toddharrison/automata/")
            connection.set("scm:git:git://github.com/toddharrison/automata.git")
            developerConnection.set("scm:git:ssh://git@github.com/toddharrison/automata.git")
        }
    }
}
