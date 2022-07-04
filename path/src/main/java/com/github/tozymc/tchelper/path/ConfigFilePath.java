package com.github.tozymc.tchelper.path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Holds the config file location.
 */
public interface ConfigFilePath extends ResourcePath {
  void touch() throws IOException;

  boolean exist();

  boolean notExist();

  @NotNull Path toPath();

  @Nullable OutputStream newOutputStream() throws IOException;

  @Nullable BufferedWriter newBufferedWriter() throws IOException;
}
