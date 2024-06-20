package project.domain.chatGpt.model.enums;

public enum QuizType {

    CHOICE,
    OX;
    @Override
    public String toString() {
        if(this.name().equals("CHOICE")){
            return "5-" + this.name().toLowerCase();
        }
        return this.name().toLowerCase();
    }
}
