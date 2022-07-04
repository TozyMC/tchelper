package com.github.tozymc.tchelper.path;

import java.nio.file.Path;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holds the config path, include config file and default config.
 */
public interface ConfigPath extends ConfigFilePath {
  @Contract(value = " -> new", pure = true)
  static @NotNull Builder builder() {
    return new ConfigPathImpl.BuilderImpl();
  }

  static @NotNull ConfigPath create(String first, String... more) {
    return builder().path(first, more).create();
  }

  static @NotNull ConfigPath create(Path root, String first, String... more) {
    return builder().root(root).path(first, more).create();
  }

  static @NotNull ConfigPath create(Path path) {
    return builder().path(path).create();
  }

  void saveDefault();

  @Nullable ResourcePath defaultResource();

  interface Builder {
    @NotNull Builder root(@NotNull Path root);

    @NotNull Builder path(@NotNull String path);

    @NotNull Builder path(@NotNull ResourcePath path);

    @NotNull Builder path(@NotNull String first, String... more);

    @NotNull Builder path(@NotNull Path path);

    @NotNull Builder defaultResource(@Nullable ResourcePath defResource);

    @NotNull ConfigPath create();
  }
}
