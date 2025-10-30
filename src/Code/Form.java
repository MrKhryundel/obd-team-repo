import java.sql.Timestamp;

public class Form {
    private Long formID;
    private String title;
    private String description;
    private Timestamp createdAt;
    private Long userID;

    public Long getFormID() { return formID; }
    public void setFormID(Long formID) { this.formID = formID; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Long getUserID() { return userID; }
    public void setUserID(Long userID) { this.userID = userID; }
}
