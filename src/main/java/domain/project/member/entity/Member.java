package domain.project.member.entity;


import domain.project.game.entity.ChatMessage;
import domain.project.game.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @OneToMany(mappedBy = "sender")
    List<ChatMessage> chatMessages;

    @OneToMany(mappedBy = "host")
    List<ChatRoom> chatRooms;

}
