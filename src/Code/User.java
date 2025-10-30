import java.sql.Timestamp;

public class User {
    private Long userID;
    private String name;
    private String email;
    private Timestamp createdAt;
    private String role;

    // Геттери та сеттери
    public Long getUserID() { return userID; }
    public void setUserID(Long userID) { this.userID = userID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}