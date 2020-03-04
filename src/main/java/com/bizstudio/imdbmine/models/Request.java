/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.models;

import lombok.Data;

/**
 *
 * @author obinna.asuzu
 */
@Data
public class Request {

    String name;

    Integer genre1;
    Integer genre2;

    Integer cast1;
    Integer cast2;
    Integer cast3;
    Integer cast4;

    String originCountry1;
    String originCountry2;

    Integer productionCompany1;
    Integer productionCompany2;

    Integer network1;
    Integer network2;

    Boolean inProduction;
    String status;
    String originalLanguage;

    Double avgRunTime;
}

