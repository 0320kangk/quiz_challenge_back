package project.domain.quizQuestion.entity;

import jakarta.persistence.*;
import lombok.*;
import project.domain.quizTheme.entity.QuizTheme;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name= "QuizTheme_id")
    QuizTheme quizTheme;

    @Column(nullable = false)
    String topic;

}
