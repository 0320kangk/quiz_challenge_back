package project.domain.chatGpt.util;

import project.domain.chatGpt.model.dto.QuestionRequestDto;

public class ChatGptUtil {

    //우선 클래스를 받아서 가공해야 함

    public static String createSystemContent(QuestionRequestDto questionRequestDto) {
        String questionPart1 = QuestionsFormatting.getQuestionPart1(questionRequestDto.getQuizLevel());
        String questionPart2 = QuestionsFormatting.getQuestionPart2(questionRequestDto.getCount());
        String questionPart3 = QuestionsFormatting.getQuestionPart3(questionRequestDto.getTitle());

        //메시지에 주제가 들어가야함
        String content =
        """
        %s %s %s %s
        """.formatted(questionPart1,
                                 questionPart2,
                                 questionPart3,
                                 QuestionsFormatting.questionPart4);
        return content;
    }
//    public static String createUserContent(String topic){
//
//    }

}
