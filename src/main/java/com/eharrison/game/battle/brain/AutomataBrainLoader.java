package com.eharrison.game.battle.brain;

import lombok.val;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * To register a new Brain in a JAR:
 * 1. Create a class that implements the `AutomataBrain` interface.
 * 2. In the JAR, include a file at `META-INF/services/com.eharrison.automata.brain.AutomataBrain`.
 * 3. The file should contain the fully qualified class name of the `AutomataBrain` implementation, one per line.
 *    Example:
 *    com.example.brains.MyCustomBrain
 *    com.example.brains.AnotherBrain
 */
public class AutomataBrainLoader {
    private final Path jarDirectory;

    public AutomataBrainLoader(final String jarDirectoryPath) {
        jarDirectory = Paths.get(jarDirectoryPath);
        if (!Files.exists(jarDirectory) || !Files.isDirectory(jarDirectory)) {
            throw new IllegalArgumentException("Invalid Brain directory: " + jarDirectoryPath);
        }
    }

    public Map<String, AutomataBrain> loadBrains() throws IOException {
        val brains = new HashMap<String, AutomataBrain>();

        try (val jarFiles = Files.newDirectoryStream(jarDirectory, "*.jar")) {
            for (val jarFile : jarFiles) {
                try (val classLoader = new URLClassLoader(
                        new URL[]{ jarFile.toUri().toURL() },
                        AutomataBrain.class.getClassLoader())
                ) {
                    val serviceLoader = ServiceLoader.load(AutomataBrain.class, classLoader);
                    for (val brain : serviceLoader) {
                        brains.put(brain.name(), brain);
                    }
                }
            }
        }

        return brains;
    }
}
