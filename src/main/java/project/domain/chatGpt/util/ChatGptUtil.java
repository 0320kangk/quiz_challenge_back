package project.domain.chatGpt.util;

import project.domain.chatGpt.model.dto.QuestionRequestDto;

public class ChatGptUtil {

    //우선 클래스를 받아서 가공해야 함
    public static String[] createSystemQuestion(QuestionRequestDto questionRequestDto) {

        String[] questions = new String[3];
        questions[0] =  QuestionsFormatting.getQuestionPart1(questionRequestDto.getQuizLevel());
        questions[1]  = QuestionsFormatting.getQuestionPart2(questionRequestDto.getTitle());
        questions[2] = QuestionsFormatting.getQuestionPart3(questionRequestDto.getQuizType());
//        String content =
//                """
//                %s %s %s %s
//                """.formatted(questionPart1,
//                        questionPart2,
//                        questionPart3,
//                        QuestionsFormatting.questionPart4);
        return questions;
    }
    public static String createSystemContent(String[] questions) {
        StringBuilder content = new StringBuilder();

        for (int i = 0; i < questions.length; i++) {
            content.append(i + 1).append(". ").append(questions[i]).append("\n");
        }
        //메시지에 주제가 들어가야함
        return content.toString();
    }

    public static String createUserContent(Integer count, String topic) {
        // 총 몇 문제 만들었습니다.
         return QuestionsFormatting.getUserQuestionPart1(count) + QuestionsFormatting.getUserQuestionPart2(topic);
    }
}
