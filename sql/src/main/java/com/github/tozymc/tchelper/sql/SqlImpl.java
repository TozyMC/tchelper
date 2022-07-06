package com.github.tozymc.tchelper.sql;

import static com.github.tozymc.tchelper.sql.SqlProviderImpl.LOGGER;

import java.util.concurrent.atomic.AtomicBoolean;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.NotNull;

class SqlImpl implements Sql {
  private final SqlConfiguratorImpl configurator;
  private final AtomicBoolean available = new AtomicBoolean(false);

  private ServiceRegistry serviceRegistry;
  private SessionFactory sessionFactory;

  SqlImpl(SqlConfiguratorImpl configurator) {
    this.configurator = configurator;
  }

  private void testConnection() {
    try (var session = sessionFactory.openSession()) {
      available.set(session.isConnected());
    } catch (HibernateException e) {
      available.set(false);
      LOGGER.error("Cannot connect to " + "", e);
    }
  }

  public void connect() {
    var bootstrapBuilder = new BootstrapServiceRegistryBuilder().enableAutoClose();
    configurator.bootstrapServiceRegistryConfigurator().accept(bootstrapBuilder);

    var standardBuilder = new StandardServiceRegistryBuilder(bootstrapBuilder.build());
    standardBuilder.loadProperties(configurator.configPath().toPath().toFile());
    configurator.standardServiceRegistryConfigurator().accept(standardBuilder);

    serviceRegistry = standardBuilder.build();

    var metadataSources = new MetadataSources(serviceRegistry);
    configurator.metadataSourcesConfigurator().accept(metadataSources);

    var metadataBuilder = metadataSources.getMetadataBuilder();
    configurator.metadataConfigurator().accept(metadataBuilder);

    var sessionFactoryBuilder = metadataBuilder.build().getSessionFactoryBuilder();
    configurator.sessionFactoryConfigurator().accept(sessionFactoryBuilder);

    sessionFactory = sessionFactoryBuilder.build();

    testConnection();
  }

  @Override
  public boolean available() {
    return available.get();
  }

  @Override
  public @NotNull SessionFactory factory() {
    return sessionFactory;
  }

  @Override
  public void close() {
    if (sessionFactory.isOpen()) {
      sessionFactory.close();
    }
    StandardServiceRegistryBuilder.destroy(serviceRegistry);
  }
}
