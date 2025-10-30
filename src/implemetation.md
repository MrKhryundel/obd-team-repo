# Лабораторна робота №6
Тема: Реалізація об’єктно-реляційного відображення (proj)

## Структура проєкту
```
src/
└─ db_instances/
├─ Answer.java
├─ AnswerOption.java
├─ Form.java
├─ Question.java
├─ Response.java
└─ User.java
└─ dao/
├─ AbstractDAO.java
├─ AnswerDAO.java
├─ AnswerOptionDAO.java
├─ FormDAO.java
├─ QuestionDAO.java
├─ ResponseDAO.java
└─ UserDAO.java
└─ util/
└─ DBConnection.java
└─ MainTest.java
```

## SQL: Створення бази даних
```sql
CREATE DATABASE proj
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE proj;

CREATE TABLE `User` (
UserID INT AUTO_INCREMENT PRIMARY KEY,
Name VARCHAR(100) NOT NULL,
Email VARCHAR(255) UNIQUE NOT NULL,
Role VARCHAR(50),
CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE Form (
FormID INT AUTO_INCREMENT PRIMARY KEY,
Title VARCHAR(255) NOT NULL,
Description TEXT,
CreatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
UserID INT NOT NULL,
FOREIGN KEY (UserID) REFERENCES `User`(UserID) ON DELETE CASCADE
);

CREATE TABLE Question (
QuestionID INT AUTO_INCREMENT PRIMARY KEY,
FormID INT NOT NULL,
Text TEXT NOT NULL,
Type VARCHAR(50) NOT NULL CHECK (Type IN ('text', 'multiple_choice', 'checkbox', 'scale')),
IsRequired BOOLEAN DEFAULT FALSE,
FOREIGN KEY (FormID) REFERENCES Form(FormID) ON DELETE CASCADE
);

CREATE TABLE AnswerOption (
OptionID INT AUTO_INCREMENT PRIMARY KEY,
QuestionID INT NOT NULL,
OptionText VARCHAR(255) NOT NULL,
FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID) ON DELETE CASCADE
);

CREATE TABLE Response (
ResponseID INT AUTO_INCREMENT PRIMARY KEY,
FormID INT NOT NULL,
SubmittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
FOREIGN KEY (FormID) REFERENCES Form(FormID) ON DELETE CASCADE
);

CREATE TABLE Answer (
AnswerID INT AUTO_INCREMENT PRIMARY KEY,
ResponseID INT NOT NULL,
QuestionID INT NOT NULL,
OptionID INT NULL,
TextAnswer TEXT,
FOREIGN KEY (ResponseID) REFERENCES Response(ResponseID) ON DELETE CASCADE,
FOREIGN KEY (QuestionID) REFERENCES Question(QuestionID) ON DELETE CASCADE,
FOREIGN KEY (OptionID) REFERENCES AnswerOption(OptionID) ON DELETE SET NULL
);
```

# Bean-класи
### Приклади класів

---
## User.java
```java
package db_instances;

import java.sql.Timestamp;

public class User {
public int UserID;
public String Name;
public String Email;
public String Role;
public Timestamp CreatedAt;
}
```

## Form.java
```java
package db_instances;

import java.sql.Timestamp;

public class Form {
public int FormID;
public String Title;
public String Description;
public Timestamp CreatedAt;
public int UserID;
}
```


# DAO-інфраструктура

## AbstractDAO.java

```java
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO<T> {
protected Connection connection;

    public AbstractDAO(Connection connection) {
        this.connection = connection;
    }

    public abstract void insert(T obj) throws SQLException;
    public abstract List<T> getAll() throws SQLException;
    public abstract T getById(int id) throws SQLException;
}
```

## UserDAO.java
```java
package dao;

import db_instances.User;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO<User> {

    public UserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO User (Name, Email, Role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.Name);
            stmt.setString(2, user.Email);
            stmt.setString(3, user.Role);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) user.UserID = keys.getInt(1);
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM User")) {
            while (rs.next()) {
                User u = new User();
                u.UserID = rs.getInt("UserID");
                u.Name = rs.getString("Name");
                u.Email = rs.getString("Email");
                u.Role = rs.getString("Role");
                u.CreatedAt = rs.getTimestamp("CreatedAt");
                list.add(u);
            }
        }
        return list;
    }

    @Override
    public User getById(int id) throws SQLException {
        String sql = "SELECT * FROM User WHERE UserID=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.UserID = rs.getInt("UserID");
                u.Name = rs.getString("Name");
                u.Email = rs.getString("Email");
                u.Role = rs.getString("Role");
                u.CreatedAt = rs.getTimestamp("CreatedAt");
                return u;
            }
        }
        return null;
    }
}
```

## DBConnection.java
```java
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
private static final String URL = "jdbc:mysql://localhost:3306/proj";
private static final String USER = "root";
private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

```


## MainTest.java
```java
import dao.UserDAO;
import db_instances.User;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Timestamp;

public class MainTest {
public static void main(String[] args) {
try (Connection conn = DBConnection.getConnection()) {
UserDAO userDAO = new UserDAO(conn);

            // Створення користувача
            User user = new User();
            user.Name = "Ivan";
            user.Email = "ivan@example.com";
            user.Role = "Student";
            user.CreatedAt = new Timestamp(System.currentTimeMillis());
            userDAO.insert(user);
            System.out.println("Created User ID: " + user.UserID);

            // Пошук по ID
            User foundUser = userDAO.getById(user.UserID);
            System.out.println("Found User: " + foundUser.Name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

```

## Вивід програми
```
Додано користувача: 1
1 | Ivan | ivan@example.com | Student

Process finished with exit code 0
```

Користувач був успішно доданий до бази даних proj.

![result of execution](./image/result-of-execution.png)
