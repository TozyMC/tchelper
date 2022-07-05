package com.github.tozymc.tchelper.redis;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.tozymc.tchelper.path.ConfigPath;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.ExecutorService;
import org.jetbrains.annotations.NotNull;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RedisProviderImpl implements RedisProvider {
  static final Logger LOGGER = LoggerFactory.getLogger("original-redis");

  private final ExecutorService executorService;
  private final ConfigPath globalRedisConfigPath;

  RedisProviderImpl(ExecutorService executorService, ConfigPath globalRedisConfigPath) {
    this.executorService = executorService;
    this.globalRedisConfigPath = globalRedisConfigPath;
  }

  @Override
  public @NotNull RedisSource get() {
    return get(globalRedisConfigPath);
  }

  @Override
  public @NotNull RedisSource get(@NotNull ConfigPath configPath) {
    checkNotNull(configPath, "configPath");
    Config config;
    try {
      config = Config.fromYAML(configPath.newBufferedReader());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    var source = new RedisSourceImpl(config, executorService);
    source.connect();
    return source;
  }
}
