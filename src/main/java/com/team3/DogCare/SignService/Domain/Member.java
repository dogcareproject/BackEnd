package com.team3.DogCare.SignService.Domain;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;


    @Column(nullable = false , unique = true)
    private String account;

    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String name;

    @Column(nullable = false , unique = true)
    private String email;

    @Column @Nullable
    private LocalDateTime ban;


    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    @ElementCollection(targetClass=Authority.class)
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setMember(this));
    }
    public List<Authority> getRoles() {
        return roles;
    }


}
