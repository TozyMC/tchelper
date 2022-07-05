package com.github.tozymc.tchelper.sql.converter;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.serializer.gson.GsonComponentSerializer.gson;

import jakarta.persistence.AttributeConverter;
import net.kyori.adventure.text.Component;

public final class GsonComponentConverter implements AttributeConverter<Component, String> {
  @Override
  public String convertToDatabaseColumn(Component attribute) {
    return gson().serialize(attribute == null ? empty() : attribute);
  }

  @Override
  public Component convertToEntityAttribute(String dbData) {
    return dbData == null ? empty() : gson().deserialize(dbData);
  }
}
