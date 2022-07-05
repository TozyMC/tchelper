package com.github.tozymc.tchelper.sql.converter;

import static net.kyori.adventure.text.Component.empty;
import static net.kyori.adventure.text.minimessage.MiniMessage.miniMessage;

import jakarta.persistence.AttributeConverter;
import net.kyori.adventure.text.Component;

public final class MiniMessageComponentConverter implements AttributeConverter<Component, String> {
  @Override
  public String convertToDatabaseColumn(Component attribute) {
    return miniMessage().serialize(attribute == null ? empty() : attribute);
  }

  @Override
  public Component convertToEntityAttribute(String dbData) {
    return dbData == null ? empty() : miniMessage().deserialize(dbData);
  }
}
