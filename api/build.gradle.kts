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