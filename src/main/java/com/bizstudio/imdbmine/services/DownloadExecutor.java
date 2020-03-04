/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.services;

import com.bizstudio.imdbmine.models.Cast;
import com.bizstudio.imdbmine.models.Details;
import com.bizstudio.imdbmine.models.Movie;
import com.bizstudio.imdbmine.models.MovieResponse;
import com.bizstudio.imdbmine.repositories.InstanceRepository;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 *
 * @author obinna.asuzu
 */
@Component
public class DownloadExecutor {

    @Autowired
    InstanceRepository instanceRepository;

    private final OkHttpClient client = new OkHttpClient();

    Logger logger = LoggerFactory.getLogger(DownloadExecutor.class);

    Gson gson = new Gson();

    String apiKey = "272f90487c62dcac50aa67502dac3a7f";

    @Async
    public void execute(int page, Function<Movie, List<Cast>> fetchCast, Function<Movie, Details> fetchDetails) {
        logger.info("Fetching page " + page);
        try {
            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/popular?api_key=" + apiKey + "&language=en-US&page=" + page)
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            MovieResponse movieResponse = gson.fromJson(response.body().string(), MovieResponse.class);
            movieResponse.getResults().stream().forEach(movie -> {
                movie.setCast(fetchCast.apply(movie));
                movie.setDetails(fetchDetails.apply(movie));
                instanceRepository.save(movie);
            });
        } catch (IOException ex) {
            logger.error("{}", ex);
        }
    }
}



