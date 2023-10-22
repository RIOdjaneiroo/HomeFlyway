package RIO.example.util;

import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final Database INSTANCE = new Database(); // робимо статичне поле INSTANCE що містить єдиний екземпляр класу Database.
    private static Connection connection;

    private Database() {   //приватний конструктор що вказує на сінглтон  - ініціалізуємо зєднання з БД
        String url = PropertyReader.getConnectionUrlForPostgres();
        String user = PropertyReader.getUserForPostgres();
        String pass = PropertyReader.getPasswordForPostgres();

        try {
            connection = DriverManager.getConnection(url, user, pass);
            Flyway flyway = Flyway.configure().dataSource(url, user, pass).load(); // ініціалізуємо Flyway
            flyway.migrate();                                                    // виконуємо міграцію бд
        } catch (SQLException e) {
            System.out.println(String.format("SQL exception. Can not create connection. Reason: %s", e.getMessage()));
            throw new RuntimeException("Can not create connection");
        }
    }
    public static Database getInstance() {
        return INSTANCE;
    }
    public Connection getConnection() {
        return connection;
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error closing connection: " + e.getMessage());
        }
    }
}
