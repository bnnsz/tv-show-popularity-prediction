/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.repositories;

import com.bizstudio.imdbmine.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author obinna.asuzu
 */
public interface InstanceRepository extends MongoRepository<Movie, Integer> {
    
}







