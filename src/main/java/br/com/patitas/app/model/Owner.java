//package br.com.patitas.app.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Table(name = "tb_owner")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Owner {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @OneToMany(mappedBy = "owner")
//    private Set<Pet> pets = new HashSet<>();
//
//    @Column(name = "document")
//    private String document;
//}
