package com.mitocode.model;

import lombok.*;

import javax.persistence.*;
import javax.swing.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity//(name = "jugador")
//@Table(name = "jugador")
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Integer edad;
    private String equipo;

    @ManyToOne
    @JoinColumn(name="id_Posiciones")
    private Posiciones posiciones;

    // Constructor, getters y setters
}
