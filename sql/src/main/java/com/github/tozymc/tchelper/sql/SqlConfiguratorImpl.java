package com.github.tozymc.tchelper.sql;

import static java.util.Objects.requireNonNullElse;

import com.github.tozymc.tchelper.path.ConfigPath;
import java.util.function.Consumer;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jetbrains.annotations.NotNull;

final class SqlConfiguratorImpl implements SqlConfigurator {
  private static final Consumer<?> EMPTY_CONFIGURATOR = ignored -> {};

  private ConfigPath configPath;
  private Consumer<BootstrapServiceRegistryBuilder> bootstrapServiceRegistryConfigurator;
  private Consumer<StandardServiceRegistryBuilder> standardServiceRegistryConfigurator;
  private Consumer<MetadataSources> metadataSourcesConfigurator;
  private Consumer<MetadataBuilder> metadataConfigurator;
  private Consumer<SessionFactoryBuilder> sessionFactoryConfigurator;

  SqlConfiguratorImpl() {}

  private static <T> Consumer<T> emptyConfigurator() {
    //noinspection unchecked
    return (Consumer<T>) EMPTY_CONFIGURATOR;
  }

  @Override
  public @NotNull SqlConfigurator configPath(@NotNull ConfigPath configPath) {
    this.configPath = configPath;
    return this;
  }

  @Override
  public @NotNull SqlConfigurator configureBoostrapServiceRegistry(
      Consumer<BootstrapServiceRegistryBuilder> configurator) {
    this.bootstrapServiceRegistryConfigurator = configurator;
    return this;
  }

  @Override
  public @NotNull SqlConfigurator configureStandardServiceRegistry(
      Consumer<StandardServiceRegistryBuilder> configurator) {
    this.standardServiceRegistryConfigurator = configurator;
    return this;
  }

  @Override
  public @NotNull SqlConfigurator configureMetadataSources(Consumer<MetadataSources> configurator) {
    this.metadataSourcesConfigurator = configurator;
    return this;
  }

  @Override
  public @NotNull SqlConfigurator configureMetadata(Consumer<MetadataBuilder> configurator) {
    this.metadataConfigurator = configurator;
    return this;
  }

  @Override
  public @NotNull SqlConfigurator configureSessionFactory(
      Consumer<SessionFactoryBuilder> configurator) {
    this.sessionFactoryConfigurator = configurator;
    return this;
  }

  ConfigPath configPath() {
    return configPath;
  }

  Consumer<BootstrapServiceRegistryBuilder> bootstrapServiceRegistryConfigurator() {
    return requireNonNullElse(bootstrapServiceRegistryConfigurator, emptyConfigurator());
  }

  Consumer<StandardServiceRegistryBuilder> standardServiceRegistryConfigurator() {
    return requireNonNullElse(standardServiceRegistryConfigurator, emptyConfigurator());
  }

  Consumer<MetadataSources> metadataSourcesConfigurator() {
    return requireNonNullElse(metadataSourcesConfigurator, emptyConfigurator());
  }

  Consumer<MetadataBuilder> metadataConfigurator() {
    return requireNonNullElse(metadataConfigurator, emptyConfigurator());
  }

  Consumer<SessionFactoryBuilder> sessionFactoryConfigurator() {
    return requireNonNullElse(sessionFactoryConfigurator, emptyConfigurator());
  }
}
