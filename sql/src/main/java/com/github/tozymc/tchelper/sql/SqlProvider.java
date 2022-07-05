package com.github.tozymc.tchelper.sql;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.tozymc.tchelper.path.ConfigPath;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface SqlProvider {
  @Contract(value = "_ -> new", pure = true)
  static @NotNull SqlProvider create(@NotNull ConfigPath globalStorageConfigPath) {
    checkNotNull(globalStorageConfigPath, "globalStorageConfigPath");
    return new SqlProviderImpl(globalStorageConfigPath);
  }

  @NotNull Sql get();

  @NotNull Sql get(@NotNull ConfigPath configPath);
}
