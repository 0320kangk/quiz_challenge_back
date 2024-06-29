package project.domain.chatGpt.util;

import project.domain.chatGpt.model.enums.QuizLevel;
import project.domain.chatGpt.model.enums.QuizType;

public class QuestionsFormatting {
    public static final String oxType = """
            Generate a simple OX quiz based on the following statement:
            Please provide the statement, options 'O' for true and 'X' for false, and the correct answer.
            I would like each question to be provided in JSON format.
             The JSON structure should look like this: [{
                           "question": "Question content",
                           "options": [
                               "O",
                               "X"
                           ],
                           "answer": "Correct answer option ('O' or 'X')",
                           "quizType": "OX"
                       },]
                      
                       Example:
                       [{
                           "question": "Is Spring Framework based on JAVA?",
                           "options": [
                               "O",
                               "X"
                           ],
                           "answer": "O",
                           "quizType": "OX"
                       },]
                       
                       """;
    public static final String choiceType = """
             I would like each question to be provided in JSON format. The JSON structure should look like this: [{
                          "question": "Question content",
                          "options": [
                            "Choice 1",
                            "Choice 2",
                            "Choice 3",
                            "Choice 4",
                            "Choice 5"
                          ],
                          "answer": "Correct answer option number (0-4)",
                          "quizType": "CHOICE_5"
                        },]
                         
                         Example: [{
                          "question": "What are the core features of Spring Framework?",
                          "options": [
                            "Dependency Injection",
                            "Aspect-Oriented Programming",
                            "Spring Boot",
                            "Spring Cloud",
                            "Modularization"
                          ],
                          "answer": 0,
                          "quizType": "CHOICE_5"
                        },]
                        """;

    public static String getQuestionPart1(QuizLevel quizLevel ){
        return String.format("You need to create %s quiz questions. Answer all questions in Korean.", quizLevel);
    }

    public static String getQuestionPart2(String title){
        return String.format("about the %s.", title);
    }

    public static String getQuestionPart3(QuizType quizType){
        if (quizType == QuizType.CHOICE_5){
            return choiceType;
        }else{
            return oxType;
        }
    }

    public static String getUserQuestionPart1(Integer count) {
        return "%d개 질문을 만들어라.\n".formatted(count);
    }
    public static String getUserQuestionPart2(String topic) {
        return "주제는 %s.".formatted(topic);
    }
}
