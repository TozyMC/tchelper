package com.github.tozymc.tchelper.path;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ResourcePathImpl implements ResourcePath {
  static final Logger LOGGER = LoggerFactory.getLogger("tchelper-path");

  private final String[] fullPath;

  ResourcePathImpl(String[] fullPath) {
    checkArgument(fullPath.length > 0, "Requires at least path name");
    this.fullPath = fullPath;
  }

  static String @NotNull [] join(String first, String @NotNull ... more) {
    var strings = new String[more.length + 1];
    strings[0] = first;
    System.arraycopy(more, 0, strings, 1, more.length);
    return strings;
  }

  @Override
  public @NotNull String @NotNull [] fullPath() {
    return fullPath;
  }

  @Override
  public @NotNull String name() {
    return fullPath[fullPath.length - 1];
  }

  @Override
  public @NotNull String join() {
    return String.join("/", fullPath);
  }

  @Override
  public @Nullable InputStream newInputStream() throws IOException {
    return ResourcePathImpl.class.getClassLoader().getResourceAsStream(join());
  }

  @Override
  public @Nullable BufferedReader newBufferedReader() throws IOException {
    try (var in = newInputStream()) {
      if (in != null) {
        return new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
      }
      return null;
    }
  }
}
