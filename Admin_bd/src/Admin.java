
import java.sql.*;
import java.util.Scanner;

public class Admin{

    private static final String URL = "jdbc:mysql://localhost:3306/Admin?useSSL=false&serverTimezone=Europe/Moscow"; // URL к вашей БД
    private static final String USER = "root"; // Замените на ваше имя пользователя
    private static final String PASSWORD = ""; // Замените на ваш пароль

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Меню:");
                System.out.println("1. Войти в аккаунт");
                System.out.println("2. Добавить пользователя");
                System.out.println("3. Добавить товар");
                System.out.println("4. Добавить категорию");
                System.out.println("5. Добавить отзыв");
                System.out.println("6. Выход");
                System.out.print("Выберите действие: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        loginUser(connection, scanner);
                        break;
                    case 2:
                        addUser(connection, scanner);
                        break;
                    case 3:
                        addProduct(connection, scanner);
                        break;
                    case 4:
                        addCategory(connection, scanner);
                        break;
                    case 5:
                        addReview(connection, scanner);
                        break;
                    case 6:
                        System.out.println("Выход...");
                        return;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loginUser(Connection connection, Scanner scanner) {
        try {
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();

            String sql = "SELECT * FROM Пользователи WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password); // Для реальных приложений пароли должны шифроваться
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Вход выполнен успешно! Добро пожаловать, " + username);
            } else {
                System.out.println("Неверные учетные данные. Попробуйте снова.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addUser(Connection connection, Scanner scanner) {
        try {
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            System.out.print("Введите email: ");
            String email = scanner.nextLine();

            String sql = "INSERT INTO Пользователи (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password); // Для реальных приложений пароли должны шифроваться
            statement.setString(3, email);
            statement.executeUpdate();
            System.out.println("Пользователь успешно добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Connection connection, Scanner scanner) {
        try {
            System.out.print("Введите ID категории: ");
            int categoryId = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.print("Введите название товара: ");
            String name = scanner.nextLine();
            System.out.print("Введите описание товара: ");
            String description = scanner.nextLine();
            System.out.print("Введите цену товара: ");
            double price = scanner.nextDouble();
            System.out.print("Введите количество на складе: ");
            int stock = scanner.nextInt();

            String sql = "INSERT INTO Товары (category_id, name, description, price, stock) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setDouble(4, price);
            statement.setInt(5, stock);
            statement.executeUpdate();
            System.out.println("Товар успешно добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addCategory(Connection connection, Scanner scanner) {
        try {
            System.out.print("Введите название категории: ");
            String name = scanner.nextLine();

            String sql = "INSERT INTO Категории (name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.executeUpdate();
            System.out.println("Категория успешно добавлена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addReview(Connection connection, Scanner scanner) {
        try {
            System.out.print("Введите ID товара: ");
            int productId = scanner.nextInt();
            System.out.print("Введите ID пользователя: ");
            int userId = scanner.nextInt();
            System.out.print("Введите рейтинг (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.print("Введите комментарий: ");
            String comment = scanner.nextLine();

            String sql = "INSERT INTO Отзывы (product_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);
            statement.setInt(2, userId);
            statement.setInt(3, rating);
            statement.setString(4, comment);
            statement.executeUpdate();
            System.out.println("Отзыв успешно добавлен.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}