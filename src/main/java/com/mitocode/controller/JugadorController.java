package com.mitocode.controller;

import com.mitocode.dto.JugadorDTO;
import com.mitocode.dto.PosicionesDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Jugador;
import com.mitocode.model.Posiciones;
import com.mitocode.repo.IJugadorRepo;
import com.mitocode.service.IJugadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    private IJugadorService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<JugadorDTO>> findAll() {
        List<JugadorDTO> list = service.findAll().stream()
                .map(jugador -> {
                    JugadorDTO jugadorDTO = mapper.map(jugador, JugadorDTO.class);
                    // Mapea manualmente la relación Position
                    jugadorDTO.setPosiciones(mapper.map(jugador.getPosiciones(), PosicionesDTO.class));
                    return jugadorDTO;
                })
                .collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<JugadorDTO> findById(@PathVariable("id") Integer id) {
        JugadorDTO dtoResponse;
        Jugador jugador = service.findById(id);
        if (jugador == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(jugador, JugadorDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody JugadorDTO dto) {
        Jugador jugador = service.save(mapper.map(dto, Jugador.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(jugador.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Jugador> update(@Valid @RequestBody JugadorDTO dto) {
        Jugador jugador = service.findById(dto.getId());
        if (jugador == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getId());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Jugador.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Jugador jugador = service.findById(id);
        if (jugador == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}