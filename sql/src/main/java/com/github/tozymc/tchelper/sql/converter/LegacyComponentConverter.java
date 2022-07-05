package com.github.tozymc.tchelper.sql.converter;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacyAmpersand;

import jakarta.persistence.AttributeConverter;
import net.kyori.adventure.text.Component;

public final class LegacyComponentConverter implements AttributeConverter<Component, String> {
  @Override
  public String convertToDatabaseColumn(Component attribute) {
    return legacyAmpersand().serialize(attribute == null ? empty() : attribute);
  }

  @Override
  public Component convertToEntityAttribute(String dbData) {
    return dbData == null ? empty() : legacyAmpersand().deserialize(dbData);
  }
}
