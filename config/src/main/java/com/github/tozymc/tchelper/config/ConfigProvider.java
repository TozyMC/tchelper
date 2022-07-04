package com.github.tozymc.tchelper.config;

import com.github.tozymc.tchelper.path.ConfigPath;
import java.nio.file.Path;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.reference.ConfigurationReference;

/**
 * Provides configuration instance.
 */
public interface ConfigProvider {
  @Contract("_ -> new")
  static @NotNull ConfigProvider createDefault(@NotNull Path globalDirectory) {
    return new DefaultConfigProvider(globalDirectory);
  }

  /**
   * Gets the global configuration reference.
   *
   * @return The global configuration reference
   * @see #get(ConfigPath)
   */
  @NotNull ConfigurationReference<ConfigurationNode> get();

  /**
   * Gets configuration reference that will only update when loaded.
   *
   * @param path path of configuration.
   * @return The configuration reference from pool, newly created if it does not exist.
   */
  @NotNull ConfigurationReference<ConfigurationNode> get(ConfigPath path);

  /**
   * Gets the directory where global configuration is store in.
   *
   * @return The directory where global configuration is store in
   */
  @NotNull Path directory();
}
