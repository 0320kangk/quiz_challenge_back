package domain.project.game.entity;

import domain.project.game.entity.enumerate.ChatRoomStatus;
import domain.project.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(nullable = false)
    private Integer problemCount;

    @Column(nullable = false)
    private ChatRoomStatus status;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "Member_id")
    private Member host;

    @OneToMany(mappedBy = "chatRoom")
    List<ChatMessage> chatRooms = new ArrayList<>();


}
