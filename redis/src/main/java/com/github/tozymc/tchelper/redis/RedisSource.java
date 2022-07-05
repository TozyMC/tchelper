package com.github.tozymc.tchelper.redis;

import java.io.Closeable;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RedissonClient;

public interface RedisSource extends Closeable {
  boolean available();

  @NotNull RedissonClient client();

  @Override
  void close() throws IOException;
}
