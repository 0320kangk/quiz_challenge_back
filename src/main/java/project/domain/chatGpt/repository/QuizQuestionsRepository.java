package project.domain.chatGpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.domain.chatGpt.model.entity.QuizQuestion;
import project.domain.chatGpt.model.entity.QuizTitle;

import java.util.List;

@Repository
public interface QuizQuestionsRepository extends JpaRepository<QuizQuestion, Long> {
    List<QuizQuestion> findByQuizTitle(QuizTitle title);
}
