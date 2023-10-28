package com.mitocode.model;

import lombok.*;

import javax.persistence.*;
import javax.swing.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity//(name = "posiciones")
//@Table(name = "posiciones")
public class Posiciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;

}
