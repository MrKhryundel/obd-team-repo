public class Question {
    private Long questionID;
    private Long formID;
    private String text;
    private String type;
    private Boolean isRequired;

    public Long getQuestionID() { return questionID; }
    public void setQuestionID(Long questionID) { this.questionID = questionID; }
    public Long getFormID() { return formID; }
    public void setFormID(Long formID) { this.formID = formID; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Boolean getIsRequired() { return isRequired; }
    public void setIsRequired(Boolean isRequired) { this.isRequired = isRequired; }
}