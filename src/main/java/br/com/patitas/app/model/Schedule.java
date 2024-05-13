package br.com.patitas.app.model;

import br.com.patitas.app.enums.DayOfWeek;
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

    @Column(name = "day_of_week")
    @EqualsAndHashCode.Exclude
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    @EqualsAndHashCode.Exclude
    private Instant startTime;

    @Column(name = "end_time")
    @EqualsAndHashCode.Exclude
    private Instant endTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id")
    @EqualsAndHashCode.Exclude
    private Appointment appointment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vet_id")
    @EqualsAndHashCode.Exclude
    private Vet vet;

}
