/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.controllers;

import com.bizstudio.imdbmine.models.Cast;
import com.bizstudio.imdbmine.models.Genre;
import com.bizstudio.imdbmine.models.Network;
import com.bizstudio.imdbmine.models.ProductionCompany;
import com.bizstudio.imdbmine.models.Request;
import com.bizstudio.imdbmine.repositories.InstanceRepository;
import com.bizstudio.imdbmine.services.IMDBService;
import java.io.IOException;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author obinna.asuzu
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/imdbmine")
public class ExecutionController {
    @Autowired
    IMDBService service;
    @Autowired
    InstanceRepository instanceRepository;
    @GetMapping("/start")
    public ResponseEntity start() throws Exception{
        service.startStream();
        return ResponseEntity.ok().build();
    }
    
    
    @GetMapping("/stop")
    public ResponseEntity stop(){
        service.stopStream();
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/export")
    public ResponseEntity export(){
        service.generateArff();
        return ResponseEntity.ok().build();
    }
    
     @GetMapping("/predict")
    public ResponseEntity export(Request body){
        service.generateArff();
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/casts")
    public ResponseEntity casts(){
        List<Cast> result = instanceRepository.findAll().stream().map(m -> m.getCast())
                .flatMap(casts -> casts.stream().limit(4))
                .distinct()
                .sorted((lhs,rhs) -> lhs.getName().compareToIgnoreCase(rhs.getName()))
                .collect(toList());
        return ResponseEntity.ok(result);
    }
    
     @GetMapping("/genre")
    public ResponseEntity genre(){
        List<Genre> result = instanceRepository.findAll().stream().map(m -> m.getDetails().getGenres())
                .flatMap(genr -> genr.stream().limit(2))
                .distinct()
                .sorted((lhs,rhs) -> lhs.getName().compareToIgnoreCase(rhs.getName()))
                .collect(toList());
        return ResponseEntity.ok(result);
    }
    
     @GetMapping("/production-companies")
    public ResponseEntity productionCompanies(){
        List<ProductionCompany> result = instanceRepository.findAll().stream().map(m -> m.getDetails().getProductionCompanies())
                .flatMap(genr -> genr.stream().limit(2))
                .distinct()
                .sorted((lhs,rhs) -> lhs.getName().compareToIgnoreCase(rhs.getName()))
                .collect(toList());
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/networks")
    public ResponseEntity networks(){
         List<Network> result = instanceRepository.findAll().stream().map(m -> m.getDetails().getNetworks())
                .flatMap(genr -> genr.stream().limit(2))
                .distinct()
                 .sorted((lhs,rhs) -> lhs.getName().compareToIgnoreCase(rhs.getName()))
                .collect(toList());
        return ResponseEntity.ok(result);
    }
    
    
    @GetMapping("/origin-countries")
    public ResponseEntity originCountry(){
         List<String> result = instanceRepository.findAll().stream().map(m -> m.getDetails().getOriginCountry())
                .flatMap(c -> c.stream().limit(2))
                .distinct()
                .collect(toList());
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/original-languages")
    public ResponseEntity originalLanguage(){
         List<String> result = instanceRepository.findAll().stream().map(m ->m.getDetails().getOriginalLanguage())
                .distinct()
                .collect(toList());
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/statuses")
    public ResponseEntity statuses(){
         List<String> result = instanceRepository.findAll().stream().map(m ->m.getDetails().getStatus())
                .distinct()
                .collect(toList());
        return ResponseEntity.ok(result);
    }
}






























