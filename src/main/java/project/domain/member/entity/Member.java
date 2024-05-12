package project.domain.member.entity;


import lombok.*;
import project.domain.game.entity.ChatMessage;
import project.domain.game.entity.ChatRoom;
import jakarta.persistence.*;
import project.domain.member.entity.enumerate.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    LocalDateTime createdDate;

    @OneToMany(mappedBy = "sender")
    List<ChatMessage> chatMessages;

    @OneToMany(mappedBy = "host")
    List<ChatRoom> chatRooms;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

}
