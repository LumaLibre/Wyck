dependencies {
    val libs = rootProject.libs
    compileOnly(libs.paper.api)
}

val injectBuildInfo by tasks.registering(Copy::class) {
    from("src/main/java-templates")
    into(layout.buildDirectory.dir("generated/sources/buildinfo/java/main"))
    filter { line -> line.replace("\"\${version}\"", "\"${project.version}\"") }
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

    opt.addStringOption("Xdoclint:none", "-quiet")
    opt.windowTitle = "BiomesAPI ${project.version}"
    opt.docTitle = "BiomesAPI ${project.version} API"
    opt.encoding = "UTF-8"
    opt.charSet = "UTF-8"
    opt.docEncoding = "UTF-8"
    opt.links(
        "https://docs.oracle.com/en/java/javase/25/docs/api/",
        "https://jd.papermc.io/paper/26.2/"
    )
    opt.addBooleanOption("html5", true)
    opt.use(true)
}