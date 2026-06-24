dependencies {
    val libs = rootProject.libs
    compileOnly(libs.paper.api)
}

val injectBuildInfo by tasks.registering(Copy::class) {
    from("src/main/java-templates")
    into(layout.buildDirectory.dir("generated/sources/buildinfo/java/main"))
    filter { line -> line.replace($$"\"${version}\"", "\"${project.version}\"") }
    inputs.property("version", project.version.toString())
}

sourceSets["main"].java.srcDir(
    layout.buildDirectory.dir("generated/sources/buildinfo/java/main")
)

tasks.compileJava {
    dependsOn(injectBuildInfo)
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.javadoc {
    val opt = options as StandardJavadocDocletOptions

    opt.apply {
        addStringOption("Xdoclint:none", "-quiet")
        windowTitle = "BiomesAPI ${project.version}"
        docTitle = "BiomesAPI ${project.version}"
        encoding = "UTF-8"
        charSet = "UTF-8"
        docEncoding = "UTF-8"
        links(
            "https://docs.oracle.com/en/java/javase/25/docs/api/",
            "https://jd.papermc.io/paper/26.2/",
            "https://jd.papermc.io/adventure/5.1.1/",
            "https://jspecify.dev/docs/api/",
        )
        addBooleanOption("html5", true)
        use(true)
    }
}