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
/*
제목
주제
질문을 갖는 테이블을 만들까

제목은 외래키 로 사용될 듯

 */
