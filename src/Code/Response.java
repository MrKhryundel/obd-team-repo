import java.sql.Timestamp;

public class Response {
    private Long responseID;
    private Long formID;
    private Timestamp submittedAt;

    public Long getResponseID() { return responseID; }
    public void setResponseID(Long responseID) { this.responseID = responseID; }
    public Long getFormID() { return formID; }
    public void setFormID(Long formID) { this.formID = formID; }
    public Timestamp getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Timestamp submittedAt) { this.submittedAt = submittedAt; }
}
