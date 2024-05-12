package project.domain.game.entity;

import project.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String message;

    @Column(nullable = false)
    LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "Member_id")
    Member sender;

    @ManyToOne
    @JoinColumn(name = "ChatRoom_id")
    ChatRoom chatRoom;



}
