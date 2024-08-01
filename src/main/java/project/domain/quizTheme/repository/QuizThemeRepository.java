package project.domain.quizTheme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.domain.quizTheme.model.entity.QuizTheme;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizThemeRepository extends JpaRepository<QuizTheme, Long> {
    Optional<QuizTheme> findByTheme(String theme);
    @Query("SELECT qt.theme FROM QuizTheme qt")
    List<String> findAllThemes();
}