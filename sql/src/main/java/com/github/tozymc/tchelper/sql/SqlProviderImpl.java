package com.github.tozymc.tchelper.sql;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.tozymc.tchelper.path.ConfigPath;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SqlProviderImpl implements SqlProvider {
  static final Logger LOGGER = LoggerFactory.getLogger("original-storage");

  private final ConfigPath globalStorageConfigPath;

  SqlProviderImpl(ConfigPath globalStorageConfigPath) {
    this.globalStorageConfigPath = globalStorageConfigPath;
  }

  @Override
  public @NotNull Sql get() {
    return get(globalStorageConfigPath);
  }

  @Override
  public @NotNull Sql get(@NotNull ConfigPath configPath) {
    checkNotNull(configPath, "configPath");
    var builder = new StandardServiceRegistryBuilder().loadProperties(configPath.toPath().toFile());
    var sql = new SqlImpl(builder.build());
    sql.connect();
    return sql;
  }
}
