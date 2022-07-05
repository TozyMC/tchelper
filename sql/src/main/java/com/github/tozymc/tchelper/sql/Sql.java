package com.github.tozymc.tchelper.sql;

import java.io.Closeable;
import java.io.IOException;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;

public interface Sql extends Closeable {
  boolean available();

  @NotNull SessionFactory factory();

  @Override
  void close() throws IOException;
}
