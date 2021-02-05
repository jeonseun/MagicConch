package team.univ.magic_conch.question;

public enum QuestionStatus {
    ING("미해결"),
    END("해결");

    private String status;

    QuestionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
