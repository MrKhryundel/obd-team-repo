import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/proj?useSSL=false", "root", "1234");

            UserDAO userDAO = new UserDAO(conn);

            // Створення користувача
            User u1 = new User();
            u1.setName("Ivan");
            u1.setEmail("ivan@example.com");
            u1.setRole("Student");
            userDAO.insert(u1);
            System.out.println("Додано користувача: " + u1.getUserID());

            // Вивід всіх користувачів
            for (User u : userDAO.getAll()) {
                System.out.println(u.getUserID() + " | " + u.getName() + " | " + u.getEmail() + " | " + u.getRole());
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

