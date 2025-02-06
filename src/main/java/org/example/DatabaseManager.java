package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseManager {
  private static final Logger log = LoggerFactory.getLogger(DatabaseManager.class);
  private static DatabaseManager instance;

  static {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private DatabaseManager() {
  }

  public static synchronized DatabaseManager getInstance() {
    if (instance == null) {
      instance = new DatabaseManager();
    }
    return instance;
  }

  /**
   * Auto migrate database inside resources/migration natively not using any library
   */
  static void migrate() throws IOException {
    DatabaseManager.createMigrationTable();

    Connection conn;
    try {
      conn = DatabaseManager.getInstance().getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      for (Path path : getMigrationFiles()) {
        String sql = Files.readString(path);

        // if migration already executed, skip
        if (conn.createStatement()
            .executeQuery("SELECT * FROM migration WHERE name = '" + path.getFileName() + "'")
            .next()) {
          log.info("Migration already executed, skipping");
        } else {
          log.info("Executing migration: {}", path.getFileName());

          // execute migration
          conn.createStatement().executeUpdate(sql);
          // insert migration record
          conn.createStatement()
              .executeUpdate("INSERT INTO migration (name) VALUES ('" + path.getFileName() + "')");
        }
      }
    } catch (IOException | SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create migration table if not exists
   *
   * @throws IOException
   */
  public static void createMigrationTable() throws IOException {
    try (Connection conn = DatabaseManager.getInstance().getConnection()) {
      String sql =
          "CREATE TABLE IF NOT EXISTS migration (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255) NOT NULL)";
      conn.createStatement().execute(sql);
    } catch (SQLException e) {
      throw new IOException("Failed to create migration table", e);
    }
  }

  /**
   * Get all migration files by human order
   */
  public static List<Path> getMigrationFiles() throws IOException {
    try (Stream<Path> paths = Files.walk(Paths.get(
        Objects.requireNonNull(DatabaseManager.class.getClassLoader().getResource("migration"))
            .toURI()))) {
      return paths.filter(Files::isRegularFile).collect(Collectors.toList());
    } catch (URISyntaxException e) {
      throw new IOException("Invalid URI syntax", e);
    }
  }

  public Connection getConnection() throws SQLException {
    Connection connection = null;
    String url = Prop.get("db.url") + ":" + Prop.get("db.port") + "/" + Prop.get(
        "db.name") + "?allowMultiQueries=true";
    connection = DriverManager.getConnection(url, Prop.get("db.user"), Prop.get("db.password"));

    return connection;
  }
}
