/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.services;

import com.bizstudio.imdbmine.models.Movie;
import com.bizstudio.imdbmine.repositories.InstanceRepository;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author obinna.asuzu
 */
@Component
public class DocExecutor {

    @Autowired
    TwitterStreamFactory twitterStreamFactory;

    @Autowired
    InstanceRepository instanceRepository;

    public void execute(int count, Movie movie, Consumer< String[]> onComplete) {

        String[] row = new String[19];
        row[0] = String.valueOf(movie.getId());
        row[1] = movie.getName();
        row[2] = movie.getCast().size() > 0 ? String.valueOf(movie.getCast().get(0)) : "?";
        row[3] = movie.getCast().size() > 1 ? String.valueOf(movie.getCast().get(1)) : "?";
        row[4] = movie.getCast().size() > 2 ? String.valueOf(movie.getCast().get(2)) : "?";
        row[5] = movie.getCast().size() > 3 ? String.valueOf(movie.getCast().get(3)) : "?";

        row[6] = movie.getDetails().getGenres().size() > 0 ? String.valueOf(movie.getDetails().getGenres().get(0).getId()) : "?";
        row[7] = movie.getDetails().getGenres().size() > 1 ? String.valueOf(movie.getDetails().getGenres().get(1).getId()) : "?";

        row[8] = movie.getDetails().getNetworks().size() > 0 ? String.valueOf(movie.getDetails().getNetworks().get(0).getId()) : "?";
        row[9] = movie.getDetails().getNetworks().size() > 1 ? String.valueOf(movie.getDetails().getNetworks().get(1).getId()) : "?";

        row[10] = movie.getDetails().getProductionCompanies().size() > 0 ? String.valueOf(movie.getDetails().getProductionCompanies().get(0).getId()) : "?";
        row[11] = movie.getDetails().getProductionCompanies().size() > 1 ? String.valueOf(movie.getDetails().getProductionCompanies().get(1).getId()) : "?";

        row[12] = movie.getDetails().getOriginCountry().size() > 0 ? movie.getDetails().getOriginCountry().get(0) : "?";
        row[13] = movie.getDetails().getOriginCountry().size() > 1 ? movie.getDetails().getOriginCountry().get(1) : "?";

        row[14] = movie.getDetails().getOriginalLanguage() != null ? movie.getDetails().getOriginalLanguage() : "?";

        row[15] = movie.getDetails().getStatus();

        row[16] = String.format("%.1f", Math.abs(movie.getDetails().getEpisodeRunTime().stream().mapToInt(i -> i).average().orElse(0)));

        row[17] = String.valueOf(movie.getDetails().getInProduction());
//        row[18] = count <= 100 ? "5" : count <= 200 ? "4" : count <= 300 ? "3" : count <= 400 ? "2" : "1";
        row[18] = "?";

        onComplete.accept(row);
        System.out.println("Executed item: " + count);
    }
}



