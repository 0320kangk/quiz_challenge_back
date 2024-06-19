package project.domain.chatGpt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.domain.chatGpt.model.entity.QuizTitle;

import java.util.Optional;

@Repository
public interface QuizTitleRepository extends JpaRepository<QuizTitle, Long> {
    Optional<QuizTitle> findByTitle(String title);
}
