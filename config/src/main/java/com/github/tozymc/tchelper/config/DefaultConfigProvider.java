package com.github.tozymc.tchelper.config;

import com.github.tozymc.tchelper.path.ConfigPath;
import com.google.common.base.Preconditions;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.reference.ConfigurationReference;

@SuppressWarnings("resource")
class DefaultConfigProvider implements ConfigProvider {
  static final Logger LOGGER = LoggerFactory.getLogger("tchelper-config");

  private final Path globalDirectory;
  private final ConfigPath globalConfigPath;

  private final ConcurrentHashMap<ConfigPath, ConfigurationReference<ConfigurationNode>> configPool = new ConcurrentHashMap<>();

  DefaultConfigProvider(Path globalDirectory) {
    this.globalDirectory = globalDirectory;
    this.globalConfigPath = ConfigPath.create(globalDirectory, "config.yml");
    createGlobalConfig();
  }

  private void createGlobalConfig() {
    create(globalConfigPath);
    LOGGER.info("Global configuration created: {}", globalConfigPath.join());
  }

  private ConfigurationReference<ConfigurationNode> create(ConfigPath path) {
    try {
      if (path.notExist()) {
        LOGGER.debug("Creating configuration {}", path.join());
      }
      ConfigurationLoader<ConfigurationNode> loader = ConfigLoaders.createLoader(path);
      var base = loader.loadToReference();
      configPool.put(path, base);
      return base;
    } catch (ConfigurateException e) {
      throw new UncheckedIOException("Creating global configuration error", e);
    }
  }

  private @NotNull ConfigurationReference<ConfigurationNode> get0(ConfigPath configPath) {
    var ref = configPool.get(configPath);
    if (ref == null) {
      ref = create(configPath);
    }
    LOGGER.info("Configuration loaded: {}", configPath.join());
    return ref;
  }

  @Override
  public @NotNull ConfigurationReference<ConfigurationNode> get() {
    return Preconditions.checkNotNull(configPool.get(globalConfigPath), "globalConfig");
  }

  @Override
  public @NotNull ConfigurationReference<ConfigurationNode> get(ConfigPath path) {
    return get0(path);
  }

  @Override
  public @NotNull Path directory() {
    return globalDirectory;
  }
}
