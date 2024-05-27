package br.com.patitas.app.model;

import br.com.patitas.app.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "pet_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties(value = "appointments")
    private Pet pet;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "vet_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties(value = {"appointments", "schedules"})
    private Vet vet;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "schedule_id")
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties(value = {"appointment", "vet"})
    private Schedule schedule;

    @Column(name = "status")
    @EqualsAndHashCode.Exclude
    private AppointmentStatus status;
}
