package com.example.vinyl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

// сервис для получения 30сек мп3 с яндекса
// https://github.com/MarshalX/yandex-music-api
@Service
public class PlayService {

    @Autowired
    public PlayService() {
    }

    // возвращает ссылку на mp3 файл для указанного имени трека
    // если по имени ничего не нашлось то возвращает null
    public String getTrackMp3URL(String trackName) {
        try {
            // 1й запрос на поиск композиции по имени
            // see - https://github.com/MarshalX/yandex-music-api/blob/main/yandex_music/client.py
            String url = "https://api.music.yandex.net/search?text=" +
                    URLEncoder.encode(trackName, StandardCharsets.UTF_8) +
              "&type=all&playlist_in_best=false&page=0";
            String respSearch = HttpRequest.get(url);

            // ссылка на инфу о треке содержится по пути - result\tracks\results[0]\id
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(respSearch);
            String trackId = jsonNode.get("result").get("tracks").get("results").get(0).get("id").asText();

            // делаем запрос для получения информации о треке
            // see - https://github.com/MarshalX/yandex-music-api/blob/main/yandex_music/download_info.py
            String urlTrackInfo = "https://api.music.yandex.net/tracks/" + trackId + "/download-info";
            String respTrackInfo = HttpRequest.get(urlTrackInfo);

            // ссылка на след инфу о скачивании трека по пути - \result\downloadInfoUrl
            jsonNode = objectMapper.readTree(respTrackInfo);
            String downloadInfoUrl = jsonNode.get("result").get(0).get("downloadInfoUrl").asText();

            // делаем запрос для получения почти совсем точной инфы о получении ссылки на мп3
            // see - https://github.com/MarshalX/yandex-music-api/blob/15fc42bdda0e00d6eb637cfe7e41016c57d61f47/yandex_music/download_info.py#L13
            String respTrackInfo2 = HttpRequest.get(downloadInfoUrl);

            XmlMapper xmlMapper = new XmlMapper();
            JsonNode rootNode = xmlMapper.readTree(respTrackInfo2);
            String host = rootNode.get("host").asText();
            String path = rootNode.get("path").asText();
            String ts = rootNode.get("ts").asText();
            String s = rootNode.get("s").asText();
            String sign = "XGRlBW9FXlekgbPrRHuSiA" + path.substring(1) + s;

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(sign.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0'); // Добавляем ведущий ноль, если нужно
                hexString.append(hex);
            }

            String directLink = "https://" + host + "/get-mp3/" + hexString + "/" + ts + path;
            return directLink;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String downloadMp3(String url) {
        String MP3 = HttpRequest.get(url);
        return MP3;
    }
}
