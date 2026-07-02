![wyck.png](docs/src/assets/wyck.png)

<div align="center">
    <h1>Wyck (BiomesAPI)</h1>
    <p>Custom biomes, dimensions, and world generation for Paper servers without datapacks.</p>
    <img src="https://img.shields.io/github/last-commit/LumaLibre/Wyck">
    <img src="https://img.shields.io/github/contributors/LumaLibre/Wyck">
    <img src="https://img.shields.io/github/forks/LumaLibre/Wyck">
</div>

---


## Docs 📚

Documentation for Wyck can be found at https://wyck.dev. 
Documentation is still under construction!

## About 📃

Wyck is a custom biome, dimensions, and world generation API for PaperMC Servers. This API is a fork of Outspending's BiomesAPI and
has been updated to support the **modern** Minecraft versions. Currently, we support Minecraft **1.21.11 ↔ 26.2**
but version support will expand with time.


Please be aware that Wyck is in active development, and some features may not be fully implemented yet.
Wyck was made for servers who are looking for more of an aesthetic feel to their builds.


## Getting Started ⭐

Wyck is built using Gradle and is hosted on my repository (repo.wyck.dev). To get started with Wyck,
follow the instructions below to add Wyck to your project.

1. Find the latest version of Wyck [HERE](https://repo.wyck.dev/#/releases/dev/wyck/Wyck). Versions with a git commit hash at the end are snapshot builds and may be unstable.
2. Add the repository to your `build.gradle.kts`, `build.gradle`, or `pom.xml` file.

And example for Gradle Kotlin DSL is provided below:

```kotlin
plugins {
    // Make sure to shade it in if you're not using it as an external dependency!
    id("com.gradleup.shadow") version "$SHADOW_VERSION"
}


repositories {
    maven("https://repo.wyck.dev/releases")
}

// 3. Replace VERSION with the latest version found in step 1
dependencies {
    implementation("dev.wyck:Wyck:$VERSION")
}

// 4. Shade the Wyck package to avoid conflicts
shadowJar {
    relocate("dev.wyck", "your.package.name.wyck")
}
```

Wyck comes as a shaded dependency or a standalone plugin, take your pick!

## Why Wyck 🤔

I believe that Wyck is one of the best ways to create custom biomes on a Paper-based Minecraft server.
At the time of writing, there are no other APIs that allow you to create custom biomes as easily as Wyck does.

Wyck is designed to be easy to use, flexible, and friendly for developers of all skill levels.

## Features 👌


- Easy to use
- Updated frequently
- Free forever
- Wiki and javadocs available
- Supports modern Minecraft versions
- Supports packet-based biome changing with ProtocolLib or PacketEvents

## Credits 🙏
Wyck was originally created by Outspending. I (Jsinco) have forked the project to add additional features and
support modern Minecraft versions. This project would not be possible without Outspending's original work.

## Contributing 📰

Contributions are welcome! If you find a bug or have a feature request, please open an issue on GitHub.

## License 🪪
Wyck is licensed under the [GPL-3.0 License](LICENSE)
