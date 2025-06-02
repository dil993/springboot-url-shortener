package com.shorturl.domain.service;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

@Slf4j
public class UrlValidatorService {

    private static final Logger log = LoggerFactory.getLogger(UrlValidatorService.class);

    public static boolean isValidUrl(String urlString) {
        try{
          log.debug("Checking if url is valid: {}", urlString);
          URL url = new URI(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestMethod("HEAD");
          return (connection.getResponseCode() >= 200 && connection.getResponseCode() < 400);
        }catch (Exception e){
            log.error("Error validating url: {}", urlString, e);
            return false;
        }
    }
}
