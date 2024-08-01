package project.domain.quizQuestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.domain.quizQuestion.entity.QuizQuestion;
import project.domain.quizTheme.model.entity.QuizTheme;

import java.util.List;

@Repository
public interface QuizQuestionsRepository extends JpaRepository<QuizQuestion, Long> {
    List<QuizQuestion> findByQuizTheme(QuizTheme theme);
}
