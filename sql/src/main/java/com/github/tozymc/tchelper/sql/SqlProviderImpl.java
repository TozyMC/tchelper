package com.github.tozymc.tchelper.sql;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.tozymc.tchelper.path.ConfigPath;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SqlProviderImpl implements SqlProvider {
  static final Logger LOGGER = LoggerFactory.getLogger("original-storage");

  private final SqlConfiguratorImpl sqlConfigurator;

  SqlProviderImpl(SqlConfigurator sqlConfigurator) {
    this.sqlConfigurator = (SqlConfiguratorImpl) sqlConfigurator;
  }

  @Override
  public @NotNull Sql get() {
    return get(sqlConfigurator);
  }

  @Override
  public @NotNull Sql get(@NotNull ConfigPath configPath) {
    checkNotNull(configPath, "configPath");
    return get(SqlConfigurator.configurator(configPath));
  }

  @Override
  public @NotNull Sql get(@NotNull SqlConfigurator configurator) {
    checkNotNull(configurator, "configurator");
    var casted = (SqlConfiguratorImpl) configurator;
    checkNotNull(casted.configPath(), "configPath");
    return new SqlImpl(casted);
  }
}
