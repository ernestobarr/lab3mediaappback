package com.mitocode.service.impl;

import com.mitocode.model.Posiciones;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPosicionesRepo;
import com.mitocode.service.IPosicionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosicionesServiceImpl extends CRUDImpl<Posiciones, Integer> implements IPosicionesService {

    @Autowired
    private IPosicionesRepo repo;

    @Override
    protected IGenericRepo<Posiciones, Integer> getRepo() {
        return repo;
    }
}

