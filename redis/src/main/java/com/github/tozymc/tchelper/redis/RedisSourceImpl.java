package com.github.tozymc.tchelper.redis;

import static com.github.tozymc.tchelper.redis.RedisProviderImpl.LOGGER;
import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.NotNull;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.client.codec.Codec;
import org.redisson.codec.Kryo5Codec;
import org.redisson.codec.LZ4Codec;
import org.redisson.config.Config;

class RedisSourceImpl implements RedisSource {
  private static final Codec DEFAULT_CODEC = new LZ4Codec(new Kryo5Codec());

  private final Config config;
  private final AtomicBoolean available = new AtomicBoolean(false);

  private Redisson client;

  RedisSourceImpl(Config config, ExecutorService executorService) {
    this.config = config;
    this.config.setCodec(DEFAULT_CODEC).setExecutor(executorService);
  }

  private void testConnection() {
    available.set(client.getRedisNodes(RedisNodes.SINGLE).pingAll());
    if (available()) {
      LOGGER.info("Connected to {}!", config.useSingleServer().getAddress());
    } else {
      LOGGER.error("Cannot connect to {}!", config.useSingleServer().getAddress());
    }
  }

  public void connect() {
    client = (Redisson) Redisson.create(config);

    testConnection();
  }

  @Override
  public boolean available() {
    return available.get();
  }

  @Override
  public @NotNull RedissonClient client() {
    checkState(available(), "Client isn't connected to ", config.useSingleServer().getAddress());
    return client;
  }

  @Override
  public void close() {
    if (!client.isShutdown()) {
      client.shutdown();
    }
  }
}
