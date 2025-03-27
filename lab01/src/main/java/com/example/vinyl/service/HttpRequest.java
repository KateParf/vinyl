package com.example.vinyl.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static String get(String url) {
        try {
            URL url2 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("GET");

            // Читаем ответ
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDiscogs(String url) {
        /*
         * Логин потребителя VIwcmSwWIEKfATTkIggJ
         * Пароль потребителя eEQHxuBeXuWeovMteHLOoZnCgIsIVYHg
         * Запросить URL-токен https://api.discogs.com/oauth/request_token
         * Разрешить URL-адрес https://www.discogs.com/ru/oauth/authorize
         * Доступ к URL https://api.discogs.com/oauth/access_token
        */

        String DISCOGS_TOKEN = "zxUaBYodbchnbbgwQexbAhGpbSXFCowWloLjxSVK";

        try {
            URL url2 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("GET");

            // Читаем ответ
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}