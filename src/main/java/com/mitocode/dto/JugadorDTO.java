package com.mitocode.dto;


import com.mitocode.model.Posiciones;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JugadorDTO {
    private Integer id;
    private String nombre;
    private Integer edad;
    private String equipo;
    private Posiciones posiciones;

}
