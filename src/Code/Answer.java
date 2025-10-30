public class Answer {
    private Long answerID;
    private Long responseID;
    private Long questionID;
    private Long optionID; // Nullable
    private String textAnswer; // Nullable

    public Long getAnswerID() { return answerID; }
    public void setAnswerID(Long answerID) { this.answerID = answerID; }
    public Long getResponseID() { return responseID; }
    public void setResponseID(Long responseID) { this.responseID = responseID; }
    public Long getQuestionID() { return questionID; }
    public void setQuestionID(Long questionID) { this.questionID = questionID; }
    public Long getOptionID() { return optionID; }
    public void setOptionID(Long optionID) { this.optionID = optionID; }
    public String getTextAnswer() { return textAnswer; }
    public void setTextAnswer(String textAnswer) { this.textAnswer = textAnswer; }
}
