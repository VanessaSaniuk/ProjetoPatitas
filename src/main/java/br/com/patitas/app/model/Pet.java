package br.com.patitas.app.model;

import br.com.patitas.app.enums.Species;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_pet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @EqualsAndHashCode.Exclude
    private String name;

    @Column(name = "species")
    @EqualsAndHashCode.Exclude
    private Species species;

    @Column(name = "race")
    @EqualsAndHashCode.Exclude
    private String race;

    @Column(name = "age")
    @EqualsAndHashCode.Exclude
    private Integer age;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties(value = {"pet"})
    private Set<Appointment> appointments = new HashSet<>();
}
