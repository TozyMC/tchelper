package com.github.tozymc.tchelper.config;

import com.github.tozymc.tchelper.path.ConfigPath;
import java.util.Locale;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.loader.AbstractConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

final class ConfigLoaders {
  private static final String YAML_FILE_EXT = "yml";
  private static final String JSON_FILE_EXT = "json";

  private static final ObjectMapper.Factory FACTORY = ObjectMapper.factoryBuilder()
      .addNodeResolver((name, element) -> {
        if (!element.isAnnotationPresent(NodePath.class)) {
          return null;
        }
        var nodePath = element.getAnnotation(NodePath.class).value();
        if (nodePath.isEmpty()) {
          return null;
        }
        var nodeNames = nodePath.split("\\.");
        if (nodeNames.length == 0) {
          return null;
        }
        return node -> node.node((Object[]) nodeNames);
      }).build();

  static <T extends ConfigurationNode> ConfigurationLoader<T> createLoader(ConfigPath path) {
    path.saveDefault();
    //noinspection unchecked
    return (ConfigurationLoader<T>) parserBuilder(path).path(path.toPath())
        .defaultOptions(opt -> opt.serializers(bld -> bld.registerAnnotatedObjects(FACTORY)))
        .build();
  }

  private static AbstractConfigurationLoader.Builder<?, ?> parserBuilder(ConfigPath path) {
    var name = path.name().toLowerCase(Locale.ROOT);
    if (name.endsWith(YAML_FILE_EXT)) {
      return YamlConfigurationLoader.builder().nodeStyle(NodeStyle.BLOCK).indent(2);
    }
    if (name.endsWith(JSON_FILE_EXT)) {
      return GsonConfigurationLoader.builder().indent(2);
    }

    throw new IllegalArgumentException("Unsupported file type: " + path.name());
  }
}
