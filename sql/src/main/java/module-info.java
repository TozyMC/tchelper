module tchelper.sql {
  requires static transitive org.jetbrains.annotations;

  requires static net.kyori.examination.api;
  requires static net.kyori.adventure;
  requires static net.kyori.adventure.text.minimessage;
  requires static net.kyori.adventure.text.serializer.gson;
  requires static net.kyori.adventure.text.serializer.plain;
  requires static net.kyori.adventure.text.serializer.legacy;

  requires transitive tchelper.path;
  requires transitive jakarta.persistence;
  requires transitive java.naming;

  requires org.slf4j;
  requires com.google.common;

  requires org.hibernate.orm.core;

  exports com.github.tozymc.tchelper.sql;
  exports com.github.tozymc.tchelper.sql.converter;
}