package com.github.tozymc.tchelper.path;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class ConfigPathImpl extends ConfigFilePathImpl implements
    ConfigPath {
  private final ResourcePath defaultResource;

  private ConfigPathImpl(Path root, String[] fullPath, @Nullable ResourcePath defaultResource) {
    super(root, fullPath);
    this.defaultResource = defaultResource;
  }

  static String @NotNull [] join(String first, String @NotNull ... more) {
    var strings = new String[more.length + 1];
    strings[0] = first;
    System.arraycopy(more, 0, strings, 1, more.length);
    return strings;
  }

  @Override
  public void saveDefault() {
    if (exist()) {
      return;
    }

    if (defaultResource == null) {
      return;
    }

    try {
      touch();
    } catch (IOException e) {
      LOGGER.error("Touching file error", e);
    }

    try (var in = defaultResource.newInputStream()) {
      if (in == null) {
        return;
      }

      Files.copy(in, toPath(), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      LOGGER.error("Saving default resource error", e);
    }
  }

  @Override
  public @Nullable ResourcePath defaultResource() {
    return defaultResource;
  }

  static class BuilderImpl implements Builder {
    private Path root;
    private String[] fullPath;
    private ResourcePath defaultResource;

    @Override
    public @NotNull Builder root(@NotNull Path root) {
      this.root = root;
      return this;
    }

    @Override
    public @NotNull Builder path(@NotNull String path) {
      this.fullPath = new String[]{path};
      return this;
    }

    @Override
    public @NotNull Builder path(@NotNull ResourcePath path) {
      this.fullPath = path.fullPath();
      return this;
    }

    @Override
    public @NotNull Builder path(@NotNull String first, String... more) {
      this.fullPath = join(first, more);
      return this;
    }

    @Override
    public @NotNull Builder path(@NotNull Path path) {
      this.fullPath = path.toString().split("/");
      return this;
    }

    @Override
    public @NotNull Builder defaultResource(ResourcePath defResource) {
      this.defaultResource = defResource;
      return this;
    }

    @Override
    public @NotNull ConfigPath create() {
      return new ConfigPathImpl(root, fullPath, defaultResource);
    }
  }
}
