package project.domain.chatGpt.model.enums;

public enum QuizLevel {
    EASY,
    NORMAL,
    HARD;
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
