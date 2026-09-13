package dev.wyck.test;

import dev.wyck.wrapper.Wrapper;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BuilderParityTest {

    @Test
    void everyWrapperWithABuilderHasToBuilder() throws IOException, ClassNotFoundException {
        Path sourceRoot = repoRoot().resolve("api/src/main/java");
        List<String> failures = new ArrayList<>();

        try (Stream<Path> files = Files.walk(sourceRoot)) {
            for (Path file : (Iterable<Path>) files.filter(BuilderParityTest::isJavaFile)::iterator) {
                String className = className(sourceRoot, file);
                if (className.endsWith("package-info") || className.endsWith("module-info")) {
                    continue;
                }
                inspect(Class.forName(className, false, BuilderParityTest.class.getClassLoader()), failures);
            }
        }

        assertTrue(
            failures.isEmpty(),
            "Wrappers declaring a Builder without toBuilder():\n  " + String.join("\n  ", failures)
        );
    }

    private static void inspect(Class<?> type, List<String> failures) {
        Class<?>[] nestedTypes = type.getDeclaredClasses();
        boolean declaresBuilder = Arrays.stream(nestedTypes)
            .anyMatch(nested -> nested.getSimpleName().equals("Builder"));
        boolean declaresToBuilder = Arrays.stream(type.getMethods())
            .filter(method -> method.getName().equals("toBuilder"))
            .map(Method::getParameterCount)
            .anyMatch(parameterCount -> parameterCount == 0);

        if (Wrapper.class.isAssignableFrom(type) && declaresBuilder && !declaresToBuilder) {
            failures.add(type.getName());
        }
        for (Class<?> nestedType : nestedTypes) {
            if (!nestedType.getSimpleName().equals("Builder")) {
                inspect(nestedType, failures);
            }
        }
    }

    private static boolean isJavaFile(Path path) {
        return path.toString().endsWith(".java");
    }

    private static String className(Path sourceRoot, Path file) {
        String relative = sourceRoot.relativize(file).toString();
        return relative.substring(0, relative.length() - ".java".length())
            .replace(File.separatorChar, '.');
    }

    private static Path repoRoot() {
        String property = System.getProperty("repo.root");
        if (property != null) {
            return Path.of(property);
        }

        Path directory = Path.of("").toAbsolutePath();
        while (directory != null && !Files.exists(directory.resolve("settings.gradle.kts"))) {
            directory = directory.getParent();
        }
        return Objects.requireNonNull(directory, "could not locate repository root");
    }
}
