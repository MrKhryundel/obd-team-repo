import dao.*;
import db_instances.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        UserDAO userDAO = new UserDAO();

        // Створення користувача
        User user = new User();
        user.Name = "Vlad";
        user.Email = "vladik7427@icloud.com";
        user.CreatedAt = "2025-10-30";
        user.role = "Admin";
        userDAO.create(user);
        System.out.println("Created User ID: " + user.UserId);

        // Пошук по ID
        User foundUser = userDAO.findById(user.UserId);
        System.out.println("Found User: " + foundUser.Name);

        // Створення форми
        FormDAO formDAO = new FormDAO();
        Form form = new Form();
        form.Title = "Test Form";
        form.Description = 1;
        form.CreatedAt = new Timestamp(System.currentTimeMillis());
        form.UserID = user.UserId;
        formDAO.create(form);
        System.out.println("Created Form ID: " + form.FormID);

        // Пошук усіх форм
        List<Form> forms = formDAO.findAll();
        System.out.println("Forms count: " + forms.size());
    }
}
