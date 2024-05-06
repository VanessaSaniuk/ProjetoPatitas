package br.com.patitas.app.model;

import br.com.patitas.app.enums.Specialization;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_vet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "specialization")
    private Specialization specialization;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vet")
    private List<Appointment> appointments = new ArrayList<>();

}
