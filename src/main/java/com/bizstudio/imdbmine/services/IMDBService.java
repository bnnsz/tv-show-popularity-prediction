/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.services;

import com.bizstudio.imdbmine.models.Cast;
import com.bizstudio.imdbmine.models.CastResponse;
import com.bizstudio.imdbmine.models.Details;
import com.bizstudio.imdbmine.models.Movie;
import com.bizstudio.imdbmine.models.MovieResponse;
import com.bizstudio.imdbmine.repositories.InstanceRepository;
import com.google.gson.Gson;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import static java.util.stream.Collectors.toList;
import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author obinna.asuzu
 */
@Service
public class IMDBService {

    private final OkHttpClient client = new OkHttpClient();

    @Autowired
    InstanceRepository instanceRepository;

    @Autowired
    DocExecutor executor;
    @Autowired
    DownloadExecutor downloadExecutor;

    Logger logger = LoggerFactory.getLogger(IMDBService.class);

    Gson gson = new Gson();

    String apiKey = "272f90487c62dcac50aa67502dac3a7f";

    public void startStream() throws IOException {
        fetchMovies();
    }

    public void stopStream() {

    }

       
     public void fetchMovies() {

        IntStream.range(1,100)
                .sorted()
                .forEach(page -> {
                    downloadExecutor.execute(page, this::fetchCast, this::fetchDetails);
                });

    }

    public Details fetchDetails(Movie movie) {
        try {

            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/" + movie.getId() + "?api_key=" + apiKey + "&language=en-US")
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            return gson.fromJson(response.body().string(), Details.class);
        } catch (IOException ex) {
            logger.error("{}", ex);
        }
        return null;
    }

    public List<Cast> fetchCast(Movie movie) {
        List<Cast> cast = new ArrayList<>();
        try {

            Request request = new Request.Builder()
                    .url("https://api.themoviedb.org/3/tv/" + movie.getId() + "/credits?api_key=" + apiKey + "&language=en-US")
                    .get()
                    .build();

            Response response = client.newCall(request).execute();
            CastResponse castResponse = gson.fromJson(response.body().string(), CastResponse.class);
            cast = castResponse.getCast();
        } catch (IOException ex) {
            logger.error("{}", ex);
        }
        return cast;
    }

    public void generateArff() {
        List<Movie> instances = instanceRepository.findAll();
        List<String> coo = new ArrayList<>(Arrays.asList("US", "BE", "SG", "JP", "CA", "GB", "TR", "ZA", "AT", "DE"
                , "CH", "CN", "RU", "DK", "ES", "BG"
                , "FR", "IT", "KR", "MX", "NL", "IN", "CO", "FI", "AU"));
        
        instances.stream()
                .map(o -> o.getDetails().getOriginCountry())
                .flatMap(i -> i.stream())
                .collect(toList())
                .forEach(oo -> coo.add(oo));
        
        List<String> coo1 = coo.stream().distinct().collect(toList());
        
        
        List<String> loo = new ArrayList<>(Arrays.asList("en","nl","ja","tr","de","zh","ru","no","es","bg","fr","it","ko","sr","hi","fi"));
        
        instances.stream()
                .map(i -> i.getDetails().getOriginalLanguage())
                .forEach(lo -> loo.add(lo));
        
        List<String> loo1 = loo.stream().distinct().collect(toList());
        
        System.out.println("countries: " + coo1);

        System.out.println("languages: " + loo1);

        int count[] = {0};
        List<String[]> csv = new ArrayList<>();

        String[] headers = {
            "id",
            "name",
            "cast_1",
            "cast_2",
            "cast_3",
            "cast_4",
            "genre_1",
            "genre_2",
            "networks_1",
            "networks_2",
            "production_company_1",
            "production_company_2",
            "origin_country_1",
            "origin_country_2",
            "original_language",
            "status",
            "avg_episode_run_time",
            "in_production",
            "popularity"
        };
        csv.add(headers);

        instances.forEach(movie -> {
            count[0]++;
            executor.execute(count[0], movie, csv::add);
        });
        csvWriterOneByOne(csv, Paths.get("/Applications")
                .toAbsolutePath().normalize().resolve("imdbmine"));
    }

    public void csvWriterOneByOne(List<String[]> stringArray, Path path) {
        Path resolve = path.resolve("export.csv");
        try {

            Files.deleteIfExists(resolve);
            Files.deleteIfExists(path);
            Files.createDirectories(path);
            System.out.println("Write to: " + path.toString());

            System.out.println("->> " + resolve.toString());
            Files.createFile(resolve);
        } catch (Exception ex) {
            System.out.println("-->" + ex.getMessage());
            logger.error("{}", ex);
        }

        try {
            System.out.println("Write to: " + resolve.toString());
            CSVWriter writer = new CSVWriter(new FileWriter(resolve.toString()));
            writer.writeAll(stringArray);
            writer.close();
        } catch (Exception ex) {
            logger.error("{}", ex);
        }
    }

}




























