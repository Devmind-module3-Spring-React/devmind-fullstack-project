package ro.devmind.devmind_fullstack_project.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="vendors")
@Getter
@Setter
@RequiredArgsConstructor
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private User user; // Link to user account ->> vendor is also an user

    @Column
    private String companyName;

    @Column
    private Double rating;


    //TODO: add portfolio

}
