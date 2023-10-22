package RIO.example;

import RIO.example.entities.Client;
import RIO.example.util.ClientService;
import RIO.example.util.Database;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");
        Connection connection = Database.getInstance().getConnection();
        ClientService clientService = new ClientService(connection);

        //1 метод створення склієнта
        long clientId = clientService.create("Kalashnik");
        System.out.println("Клієнт був успішно створений з ідентифікатором: " +clientId);

        //2 метод пошук за ідентифікатором
        long clientIID = 2l;
        String clientName = clientService.getById(clientIID);
        if (clientName != null) {
            System.out.println("Ім'я клієнта з ідентифікатором " + clientIID + ": " + clientName);
        } else {
            System.out.println("Клієнт не знайдений.");
        }

        //3 метод оновлення імені
        try {
            clientService.setName(1L, "Kali");
        }catch (RuntimeException e) {
            throw new RuntimeException("Помилка при оновленні клієнта: " + e.getMessage());
        }

        //4 метод видалення клієнта
        try {
            long clientIdd = 5L;
                clientService.deleteById(clientIdd);
                System.out.println("Клієнт з ID " + clientIdd + " був видалений.");

            } catch (RuntimeException e) {
            System.err.println("Помилка: " + e.getMessage());
        }

        // 5 метод вибрати всіх
        List<Client> clients = clientService.listAll();
        for (Client client : clients) {
            System.out.println("ID: " + client.getId() + ", Name: " + client.getName());
        }

    }
}