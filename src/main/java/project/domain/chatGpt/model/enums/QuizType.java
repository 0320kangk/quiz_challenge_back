package project.domain.chatGpt.model.enums;

public enum QuizType {

    CHOICE_4,
    OX;
    @Override
    public String toString() {
        if(this.name().equals("CHOICE_4")){
            return "5-" + this.name().toLowerCase();
        }
        return this.name().toLowerCase();
    }
}
