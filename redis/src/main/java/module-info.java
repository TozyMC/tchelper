module tchelper.redis {
  requires static transitive org.jetbrains.annotations;

  requires transitive tchelper.path;

  requires org.slf4j;
  requires com.google.common;

  requires redisson;

  exports com.github.tozymc.tchelper.redis;
}