package com.github.tozymc.tchelper.sql;

import com.github.tozymc.tchelper.path.ConfigPath;
import java.util.function.Consumer;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public sealed interface SqlConfigurator permits SqlConfiguratorImpl {
  static @NotNull SqlConfigurator configurator(@NotNull ConfigPath configPath) {
    return new SqlConfiguratorImpl().configPath(configPath);
  }

  @Contract(value = " -> new", pure = true)
  static @NotNull SqlConfigurator configurator() {
    return new SqlConfiguratorImpl();
  }

  @NotNull SqlConfigurator configPath(@NotNull ConfigPath configPath);

  @NotNull SqlConfigurator configureBoostrapServiceRegistry(
      Consumer<BootstrapServiceRegistryBuilder> configurator);

  @NotNull SqlConfigurator configureStandardServiceRegistry(
      Consumer<StandardServiceRegistryBuilder> configurator);

  @NotNull SqlConfigurator configureMetadataSources(Consumer<MetadataSources> configurator);

  @NotNull SqlConfigurator configureMetadata(Consumer<MetadataBuilder> configurator);

  @NotNull SqlConfigurator configureSessionFactory(Consumer<SessionFactoryBuilder> configurator);
}
