package com.github.tozymc.tchelper.path;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holds the resources path where located in .jar file.
 */
public interface ResourcePath {
  @Contract("_, _ -> new")
  static @NotNull ResourcePath of(@NotNull String first, String... more) {
    checkNotNull(first, "first");
    return new ResourcePathImpl(ResourcePathImpl.join(first, more));
  }

  @NotNull String @NotNull [] fullPath();

  @NotNull String name();

  @NotNull String join();

  @Nullable InputStream newInputStream() throws IOException;

  @Nullable BufferedReader newBufferedReader() throws IOException;
}
