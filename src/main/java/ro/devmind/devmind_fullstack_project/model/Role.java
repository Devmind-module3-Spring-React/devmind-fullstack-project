package ro.devmind.devmind_fullstack_project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ro.devmind.devmind_fullstack_project.enums.UserRoles;

@Entity
@Table(name="roles")
@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude="id")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoles name;
}
