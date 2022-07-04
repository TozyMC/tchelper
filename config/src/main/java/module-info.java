module tchelper.config {
  requires static transitive org.jetbrains.annotations;

  requires transitive tchelper.path;

  requires org.spongepowered.configurate;
  requires org.spongepowered.configurate.yaml;
  requires org.spongepowered.configurate.gson;

  exports com.github.tozymc.tchelper.config;
}