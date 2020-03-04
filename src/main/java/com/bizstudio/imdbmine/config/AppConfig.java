/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bizstudio.imdbmine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author obinna.asuzu
 */
@Configuration
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public Twitter twitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(env.getProperty("oauth.consumerKey"))
                .setOAuthConsumerSecret(env.getProperty("oauth.consumerSecret"))
                .setOAuthAccessToken(env.getProperty("oauth.accessToken"))
                .setOAuthAccessTokenSecret(env.getProperty("oauth.accessTokenSecret"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }
    
    
    @Bean
    public TwitterStreamFactory twitterStreamFactory() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(env.getProperty("oauth.consumerKey"))
                .setOAuthConsumerSecret(env.getProperty("oauth.consumerSecret"))
                .setOAuthAccessToken(env.getProperty("oauth.accessToken"))
                .setOAuthAccessTokenSecret(env.getProperty("oauth.accessTokenSecret"));
        return new TwitterStreamFactory(cb.build());
    }
}
















