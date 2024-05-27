package br.com.patitas.app.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "tb_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    @EqualsAndHashCode.Exclude
    private Instant startTime;

    @Column(name = "end_time")
    @EqualsAndHashCode.Exclude
    private Instant endTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "vet_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties(value = {"schedules", "appointments"})
    private Vet vet;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "appointment_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties(value = {"schedule", "vet"})
    private Appointment appointment;
}
