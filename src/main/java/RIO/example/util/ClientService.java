package RIO.example.util;

import RIO.example.entities.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    private static final String INSERT_STRING = "INSERT INTO client (name) VALUES (?) RETURNING id";
    private static final String SELECT_CLIENT_BYID = "SELECT name FROM client WHERE id = ?";
    private static final String UPDATE_CLIENT_BYID = "UPDATE client SET name = ? WHERE id = ?";
    private static final String DELETE_CLIENT_BYID = "DELETE FROM client WHERE id = ?";
    private static final String GET_ALL_CLIENT = "SELECT id, name FROM client";

    private Connection connection;

    private PreparedStatement insertStatement;
    private PreparedStatement selectClientByIdStatement;
    private PreparedStatement updateClientByIdStatement;
    private PreparedStatement deleteClientByIdStatement;
    private PreparedStatement getAllClientStatement;


    public ClientService(Connection connection) {
        this.connection = connection;
        try {
            this.insertStatement = connection.prepareStatement(INSERT_STRING);
            this.selectClientByIdStatement = connection.prepareStatement(SELECT_CLIENT_BYID);
            this.updateClientByIdStatement = connection.prepareStatement(UPDATE_CLIENT_BYID);
            this.deleteClientByIdStatement = connection.prepareStatement(DELETE_CLIENT_BYID);
            this.getAllClientStatement = connection.prepareStatement(GET_ALL_CLIENT);
        } catch (SQLException e) {
            System.out.println("User Service construction exception. Reason: " + e.getMessage());
        }
    }

    public void deleteById(long id) {
        try {
            this.deleteClientByIdStatement.setLong(1, id);
            int rowsDeleted = this.deleteClientByIdStatement.executeUpdate();

            if (rowsDeleted == 0) {
                throw new RuntimeException("Помилка під час видалення клієнта з ID " + id);
            }else {
                System.out.println("Клієнт з ID " + id + " був успішно видалений.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Помилка при видаленні клієнта: " + e.getMessage());
        }
    }
    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();
        try{
            ResultSet resultSet = this.getAllClientStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Client client = new Client(id, name);
                clients.add(client);
            }

        }catch (SQLException e) {
            throw new RuntimeException("Помилка при вибіркі клієнтів: " + e.getMessage());
        }
        return clients;
    }

    public void setName(long id, String name) {
        try {
            this.updateClientByIdStatement.setString(1, name);
            this.updateClientByIdStatement.setLong(2, id);
            int rowsUpdated = this.updateClientByIdStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Client with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating client name: " + e.getMessage());
        }
    }
    public String getById(long id) {
        try {
            this.selectClientByIdStatement.setLong(1, id);
            try (ResultSet resultSet = this.selectClientByIdStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving client by ID: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving client by ID: " + e.getMessage());
        }
        return null; // повертаємо null, коли клієнт не знайдений
    }
    public long create(String name) {
        try {
            this.insertStatement.setString(1, name);
            ResultSet resultSet = insertStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating a client: " + e.getMessage());
        }
        return -1; // повертаємо -1 у випадку невдачі
    }

    public static void main(String[] args) {
        Connection connection = Database.getInstance().getConnection();
        ClientService clientService = new ClientService(connection);
        Client client = new Client("Funtikkkkkkkpkkkkkk10");

        try {
            long clientId = clientService.create(client.getName());

            if (clientId != -1) {
                System.out.println("Клієнт був успішно створений з ідентифікатором: " + clientId);

                // Видалення клієнта за його ID
                //clientService.deleteById(clientId);
                //System.out.println("Клієнт з ID " + clientId + " був видалений.");
            } else {
                System.out.println("Не вдалося створити клієнта.");
            }
        } catch (RuntimeException e) {
            System.err.println("Помилка: " + e.getMessage());
        }
    }
}