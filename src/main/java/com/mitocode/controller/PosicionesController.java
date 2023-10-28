package com.mitocode.controller;

import com.mitocode.dto.PosicionesDTO;
import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Posiciones;
import com.mitocode.repo.IPosicionesRepo;
import com.mitocode.service.IPosicionesService;
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
@RequestMapping("/posiciones")
public class PosicionesController {

    @Autowired
    private IPosicionesService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<PosicionesDTO>> findAll() {
        List<PosicionesDTO> list = service.findAll().stream().map(p -> mapper.map(p, PosicionesDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PosicionesDTO> findById(@PathVariable("id") Integer id) {
        PosicionesDTO dtoResponse;
        Posiciones posiciones = service.findById(id);
        if (posiciones == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            dtoResponse = mapper.map(posiciones, PosicionesDTO.class);
        }
        return new ResponseEntity<>(dtoResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PosicionesDTO dto) {
        Posiciones posiciones = service.save(mapper.map(dto, Posiciones.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(posiciones.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Posiciones> update(@Valid @RequestBody PosicionesDTO dto) {
        Posiciones posiciones = service.findById(dto.getId());
        if (posiciones == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + dto.getId());
        }

        return new ResponseEntity<>(service.update(mapper.map(dto, Posiciones.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
        Posiciones posiciones = service.findById(id);
        if (posiciones == null) {
            throw new ModelNotFoundException("ID NOT FOUND: " + id);
        } else {
            service.delete(id);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}