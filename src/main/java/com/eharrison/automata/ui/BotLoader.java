package com.eharrison.automata.ui;

import com.eharrison.automata.game.Bot;
import com.eharrison.automata.util.Files;
import lombok.val;

import java.io.File;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

public class BotLoader {
    private final List<Bot<?, ?, ?, ?, ?, ?>> bots = new ArrayList<>();

    public <B extends Bot<?, ?, ?, ?, ?, ?>> Set<B> getBots(final Class<B> botClass) {
        return bots.stream()
                .filter(botClass::isInstance)
                .map(botClass::cast)
                .collect(Collectors.toUnmodifiableSet());
    }

    public void registerBot(final Bot<?, ?, ?, ?, ?, ?> bot) {
        bots.add(bot);
    }

    public void registerBotsFromExternalJars(final File dir) {
        Files.getFilesInDirectory(dir, ".jar")
                .map(Files::filesToUrls)
                .ifPresent(urls -> {
                    try (val classLoader = new URLClassLoader(urls, Bot.class.getClassLoader())) {
                        ServiceLoader.load(Bot.class, classLoader).stream()
                                .map(ServiceLoader.Provider::get)
                                .forEach(this::registerBot);
                    } catch (final Exception ignored) {}
                });
    }
}
