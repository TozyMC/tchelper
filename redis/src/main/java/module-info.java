module tchelper.redis {
  requires static transitive org.jetbrains.annotations;

  requires transitive tchelper.path;

  requires redisson;

  exports com.github.tozymc.tchelper.redis;
}