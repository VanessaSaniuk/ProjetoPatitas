package br.com.patitas.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "tb_appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @JoinColumns({
            @JoinColumn(name = "petId", referencedColumnName = "id"),
            @JoinColumn(name = "petName", referencedColumnName = "name")
    })
    @ManyToOne(fetch = FetchType.LAZY)
    private Pet pet;

    @JoinColumns({
            @JoinColumn(name = "vetId", referencedColumnName = "id"),
            @JoinColumn(name = "vetName", referencedColumnName = "name")
    })
    @ManyToOne(fetch = FetchType.LAZY)
    private Vet vet;

    @Column(name = "date")
    private Instant date;
}
