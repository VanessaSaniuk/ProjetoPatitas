package br.com.patitas.app.model;

import br.com.patitas.app.enums.Species;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_pet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JoinColumns({
            @JoinColumn(name = "ownerId", referencedColumnName = "id"),
            @JoinColumn(name = "ownerName", referencedColumnName = "name")
    })
    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    @JoinColumn(name = "appointmentId", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Appointment appointment;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private Species species;

    @Column(name = "race")
    private String race;

    @Column(name = "age")
    private Integer age;

}
