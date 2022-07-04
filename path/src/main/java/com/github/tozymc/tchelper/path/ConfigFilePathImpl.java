package com.github.tozymc.tchelper.path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class ConfigFilePathImpl extends ResourcePathImpl implements ConfigFilePath {
  private final Path path;

  ConfigFilePathImpl(Path root, String[] fullPath) {
    super(fullPath);
    if (root == null) {
      this.path = Path.of(join()).toAbsolutePath().normalize();
    } else {
      this.path = root.resolve(join()).toAbsolutePath().normalize();
    }
  }

  @Override
  public void touch() throws IOException {
    if (Files.notExists(path.getParent())) {
      Files.createDirectories(path.getParent());
    }

    if (notExist()) {
      Files.createFile(path);
    }
  }

  @Override
  public boolean exist() {
    return Files.exists(path);
  }

  @Override
  public boolean notExist() {
    return Files.notExists(path);
  }

  @Override
  public @NotNull Path toPath() {
    return path;
  }

  @Override
  public @Nullable InputStream newInputStream() throws IOException {
    return Files.newInputStream(path);
  }

  @Override
  public @Nullable BufferedReader newBufferedReader() throws IOException {
    return Files.newBufferedReader(path, StandardCharsets.UTF_8);
  }

  @Override
  public @Nullable OutputStream newOutputStream() throws IOException {
    return Files.newOutputStream(path);
  }

  @Override
  public @Nullable BufferedWriter newBufferedWriter() throws IOException {
    return Files.newBufferedWriter(path, StandardCharsets.UTF_8);
  }
}
