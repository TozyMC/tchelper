package com.github.tozymc.tchelper.sql;

import static com.github.tozymc.tchelper.sql.SqlProviderImpl.LOGGER;

import java.util.concurrent.atomic.AtomicBoolean;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.NotNull;

class SqlImpl implements Sql {
  private final ServiceRegistry serviceRegistry;
  private final AtomicBoolean available = new AtomicBoolean(false);

  private SessionFactory sessionFactory;

  SqlImpl(ServiceRegistry serviceRegistry) {this.serviceRegistry = serviceRegistry;}

  private void testConnection() {
    try (var session = sessionFactory.openSession()) {
      available.set(session.isConnected());
    } catch (HibernateException e) {
      available.set(false);
      LOGGER.error("Cannot connect to " + "", e);
    }
  }

  public void connect() {
    sessionFactory = new MetadataSources(serviceRegistry)
        .addAnnotatedClasses()
        .buildMetadata().buildSessionFactory();

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
