/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.services;

import com.bizstudio.imdbmine.models.Movie;
import com.bizstudio.imdbmine.models.Request;
import com.bizstudio.imdbmine.repositories.InstanceRepository;
import org.springframework.stereotype.Service;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;

/**
 *
 * @author obinna.asuzu
 */
@Service
public class MachineLearningService {

    @Autowired
    InstanceRepository instanceRepository;
    @Autowired
    IMDBService iMDBService;

    List<String> originCountries = new ArrayList<>();
    List<String> originalLanguages = new ArrayList<>();
    List<String> statuses = new ArrayList<>();
    List<String> inProductionOptions = Arrays.asList("TRUE", "FALSE");

    /**
     * file names are defined
     */
    public final String TRAINING_DATA_SET_FILENAME = "project_imdb_training_data.arff";

    RandomForest forest = new RandomForest();
    Instances trainingDataSet;
    Evaluation eval;

    @PostConstruct
    public void init() throws Exception {
        if (instanceRepository.count() < 1000) {
            instanceRepository.deleteAll();
            iMDBService.fetchMovies();
        }
        trainingDataSet = getTrainingInstances();
        /**
         *
         */

    }

    public String process(Request request) throws Exception {
        forest = new RandomForest();
        forest.buildClassifier(trainingDataSet);

        eval = new Evaluation(trainingDataSet);
        eval.evaluateModel(forest, trainingDataSet);

        Instances testDataSet = create(request);

        double label = forest.classifyInstance(testDataSet.instance(0));
        testDataSet.instance(0).setClassValue(label);

        return testDataSet.instance(0).stringValue(testDataSet.attribute("label"));
    }

    public Instances create(Request request) throws Exception {

        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute cast_1 = new Attribute("cast_1");
        attributes.add(cast_1);
        Attribute cast_2 = new Attribute("cast_2");
        attributes.add(cast_2);
        Attribute cast_3 = new Attribute("cast_3");
        attributes.add(cast_3);
        Attribute cast_4 = new Attribute("cast_4");
        attributes.add(cast_4);
        Attribute genre_1 = new Attribute("genre_1");
        attributes.add(genre_1);
        Attribute genre_2 = new Attribute("genre_2");
        attributes.add(genre_2);
        Attribute networks_1 = new Attribute("networks_1");
        attributes.add(networks_1);
        Attribute networks_2 = new Attribute("networks_2");
        attributes.add(networks_2);
        Attribute production_company_1 = new Attribute("production_company_1");
        attributes.add(production_company_1);
        Attribute production_company_2 = new Attribute("production_company_2");
        attributes.add(production_company_2);
        Attribute origin_country_1 = new Attribute("origin_country_1", originCountries);
        attributes.add(origin_country_1);
        Attribute origin_country_2 = new Attribute("origin_country_2", originCountries);
        attributes.add(origin_country_2);
        Attribute original_language = new Attribute("original_language", originalLanguages);
        attributes.add(original_language);
        Attribute status = new Attribute("status", statuses);
        attributes.add(status);
        Attribute avg_episode_run_time = new Attribute("avg_episode_run_time");
        attributes.add(avg_episode_run_time);
        Attribute in_production = new Attribute("in_production",inProductionOptions);
        attributes.add(in_production);
        Attribute popularity = new Attribute("popularity");
        attributes.add(popularity);

        Instance instance = new DenseInstance(17);
        instance.setValue(cast_1, request.getCast1());
        instance.setValue(cast_2, request.getCast2());
        instance.setValue(cast_3, request.getCast3());
        instance.setValue(cast_4, request.getCast4());
        instance.setValue(genre_1, request.getGenre1());
        instance.setValue(genre_2, request.getGenre2());
        instance.setValue(networks_1, request.getNetwork1());
        instance.setValue(networks_2, request.getNetwork2());
        instance.setValue(production_company_1, request.getProductionCompany1());
        instance.setValue(production_company_2, request.getProductionCompany2());
        instance.setValue(origin_country_1, request.getOriginCountry1());
        instance.setValue(origin_country_2, request.getOriginCountry2());

        instance.setValue(original_language, request.getOriginalLanguage());
        instance.setValue(status, request.getStatus());
        instance.setValue(avg_episode_run_time, request.getAvgRunTime());
        instance.setValue(in_production, request.getInProduction() ? "TRUE" : "FALSE");
        instance.setValue(popularity, Double.NaN);

        Instances result = new Instances("tv_shows", attributes, 0);
        result.add(instance);
        result.setClass(popularity);
        System.out.println(result);
        return result;
    }

    public Instances getTrainingInstances() throws Exception {
        List<Movie> movies = instanceRepository.findAll();
        originCountries = movies.stream().map(m -> m.getDetails().getOriginCountry())
                .flatMap(c -> c.stream())
                .distinct()
                .collect(toList());
        
        originalLanguages = movies.stream().map(m ->m.getDetails().getOriginalLanguage())
                .distinct()
                .collect(toList());
        
        statuses = movies.stream().map(m ->m.getDetails().getStatus())
                .distinct()
                .collect(toList());
        
        System.out.println(originCountries);
        System.out.println(originalLanguages);
        
        ArrayList<Attribute> attributes = new ArrayList<>();

        Attribute cast_1 = new Attribute("cast_1");
        attributes.add(cast_1);
        Attribute cast_2 = new Attribute("cast_2");
        attributes.add(cast_2);
        Attribute cast_3 = new Attribute("cast_3");
        attributes.add(cast_3);
        Attribute cast_4 = new Attribute("cast_4");
        attributes.add(cast_4);
        Attribute genre_1 = new Attribute("genre_1");
        attributes.add(genre_1);
        Attribute genre_2 = new Attribute("genre_2");
        attributes.add(genre_2);
        Attribute networks_1 = new Attribute("networks_1");
        attributes.add(networks_1);
        Attribute networks_2 = new Attribute("networks_2");
        attributes.add(networks_2);
        Attribute production_company_1 = new Attribute("production_company_1");
        attributes.add(production_company_1);
        Attribute production_company_2 = new Attribute("production_company_2");
        attributes.add(production_company_2);
        Attribute origin_country_1 = new Attribute("origin_country_1", originCountries);
        attributes.add(origin_country_1);
        Attribute origin_country_2 = new Attribute("origin_country_2", originCountries);
        attributes.add(origin_country_2);
        Attribute original_language = new Attribute("original_language", originalLanguages);
        attributes.add(original_language);
        Attribute status = new Attribute("status", statuses);
        attributes.add(status);
        Attribute avg_episode_run_time = new Attribute("avg_episode_run_time");
        attributes.add(avg_episode_run_time);
        Attribute in_production = new Attribute("in_production",inProductionOptions);
        attributes.add(in_production);
        Attribute popularity = new Attribute("popularity");
        attributes.add(popularity);

        Instances result = new Instances("tv_shows", attributes, 0);
        
        

        instanceRepository.findAll().forEach(movie -> {
            Instance instance = new DenseInstance(17);
            instance.setValue(cast_1, movie.getCast().size() > 0 ? movie.getCast().get(0).getId() : Double.NaN);
            instance.setValue(cast_2, movie.getCast().size() > 1 ? movie.getCast().get(1).getId() : Double.NaN);
            instance.setValue(cast_3, movie.getCast().size() > 2 ? movie.getCast().get(2).getId() : Double.NaN);
            instance.setValue(cast_4, movie.getCast().size() > 3 ? movie.getCast().get(3).getId() : Double.NaN);
            instance.setValue(genre_1, movie.getDetails().getGenres().size() > 0 ? movie.getDetails().getGenres().get(0).getId() : Double.NaN);
            instance.setValue(genre_2, movie.getDetails().getGenres().size() > 1 ? movie.getDetails().getGenres().get(1).getId() : Double.NaN);
            instance.setValue(networks_1, movie.getDetails().getNetworks().size() > 0 ? movie.getDetails().getNetworks().get(0).getId() : Double.NaN);
            instance.setValue(networks_2, movie.getDetails().getNetworks().size() > 1 ? movie.getDetails().getNetworks().get(1).getId() : Double.NaN);
            instance.setValue(production_company_1, movie.getDetails().getProductionCompanies().size() > 0 ? movie.getDetails().getProductionCompanies().get(0).getId() : Double.NaN);
            instance.setValue(production_company_2, movie.getDetails().getProductionCompanies().size() > 1 ? movie.getDetails().getProductionCompanies().get(1).getId() : Double.NaN);
            if (movie.getDetails().getOriginCountry().size() > 0) {
                instance.setValue(origin_country_1, movie.getDetails().getOriginCountry().get(0));
            }

            if (movie.getDetails().getOriginCountry().size() > 1) {
                instance.setValue(origin_country_2, movie.getDetails().getOriginCountry().get(1));
            }

            if (movie.getDetails().getOriginalLanguage() != null) {
                System.out.println("--> "+movie.getDetails().getOriginalLanguage());
                instance.setValue(original_language, movie.getDetails().getOriginalLanguage());
            }
            
            if (movie.getDetails().getStatus() != null) {
                System.out.println("stat-> "+movie.getDetails().getStatus());
                instance.setValue(status, movie.getDetails().getStatus());
            }

            instance.setValue(avg_episode_run_time, Math.abs(movie.getDetails().getEpisodeRunTime().stream().mapToInt(i -> i).average().orElse(0)));
            instance.setValue(in_production, movie.getDetails().getInProduction() ? "TRUE" : "FALSE");
            instance.setValue(popularity, movie.getDetails().getPopularity());
            result.add(instance);
        });

        result.setClass(popularity);
        return result;
    }

}



















