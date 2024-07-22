package project.domain.chatGpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.domain.chatGpt.model.entity.QuizTheme;

import java.util.Optional;

@Repository
public interface QuizThemeRepository extends JpaRepository<QuizTheme, Long> {
    Optional<QuizTheme> findByTheme(String theme);
}
