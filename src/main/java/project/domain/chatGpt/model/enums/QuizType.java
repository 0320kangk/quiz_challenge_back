package project.domain.chatGpt.model.enums;

public enum QuizType {

    CHOICE_5,
    OX;
    @Override
    public String toString() {
        if(this.name().equals("CHOICE_5")){
            return "5-" + this.name().toLowerCase();
        }
        return this.name().toLowerCase();
    }
}
