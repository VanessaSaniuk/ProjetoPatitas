package br.com.patitas.app.model;

import br.com.patitas.app.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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

    @EqualsAndHashCode.Exclude
    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @Column(name = "specialization")
    private Specialization specialization;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vet", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"vet", "appointment"})
    private Set<Schedule> schedules = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "vet", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnoreProperties(value = {"vet"})
    private Set<Appointment> appointments = new HashSet<>();
}
