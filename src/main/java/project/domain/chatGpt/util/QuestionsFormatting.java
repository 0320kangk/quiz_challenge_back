package project.domain.chatGpt.util;

import project.domain.chatGpt.model.enums.QuizLevel;

public class QuestionsFormatting {

    public static String getQuestionPart1(QuizLevel quizLevel){
        return String.format("You need to create quiz questions %s 5-choice quiz questions. Answer all questions in Korean.", quizLevel);
    }
    public static String getQuestionPart2(Integer count){
        return String.format("Please create %d 5-choice quiz questions", count);
    }
    public static String getQuestionPart3(String title){
        return String.format("about the %s", title);
    }
    public static final String questionPart4 = """
             I would like each question to be provided in JSON format. The JSON structure should look like this: {
                          "question": "Question content",
                          "options": [
                            "Choice 1",
                            "Choice 2",
                            "Choice 3",
                            "Choice 4",
                            "Choice 5"
                          ],
                          "answer": "Correct answer option number (0-4)"
                        } Example: {
                          "question": "Spring Framework의 핵심 기능은 무엇인가요?",
                          "options": [
                            "의존성 주입",
                            "관점 지향 프로그래밍",
                            "스프링 부트",
                            "스프링 클라우드",
                            "모듈화"
                          ],
                          "answer": 0
                        }""";


}
