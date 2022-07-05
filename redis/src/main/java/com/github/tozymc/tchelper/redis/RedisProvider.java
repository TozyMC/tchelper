package com.github.tozymc.tchelper.redis;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.tozymc.tchelper.path.ConfigPath;
import java.util.concurrent.ExecutorService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface RedisProvider {
  @Contract("_,_ -> new")
  static @NotNull RedisProvider create(@NotNull ExecutorService executorService,
      @NotNull ConfigPath globalRedisConfigPath) {
    checkNotNull(executorService, "executorService");
    checkNotNull(globalRedisConfigPath, "globalRedisConfigPath");
    return new RedisProviderImpl(executorService, globalRedisConfigPath);
  }

  @NotNull RedisSource get();

  @NotNull RedisSource get(@NotNull ConfigPath configPath);
}
