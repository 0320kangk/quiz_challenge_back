package project.domain.chatGpt.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name= "QuizTitle_id")
    QuizTitle quizTitle;

    @Column(nullable = false)
    String topic;

    @Column(nullable = false)
    String question;
}
