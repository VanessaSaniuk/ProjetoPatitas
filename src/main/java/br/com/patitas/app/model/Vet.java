package br.com.patitas.app.model;

import br.com.patitas.app.enums.Specialization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_vet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "specialization")
    private Specialization specialization;

    @OneToMany(mappedBy = "vet")
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "vet")
    private Set<Schedule> schedules = new HashSet<>();

}
