/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.models;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 *
 * @author obinna.asuzu
 */
@Data
@NoArgsConstructor
public class Instance {

    @Id
    Integer id;
    String name;
    Map<String, Double> tf_idf = new HashMap<>();
    String label;
}








