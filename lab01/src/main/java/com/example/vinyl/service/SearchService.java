package com.example.vinyl.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.vinyl.model.Cover;
import com.example.vinyl.model.Genre;
import com.example.vinyl.model.Group;
import com.example.vinyl.model.Performer;
import com.example.vinyl.model.Record;
import com.example.vinyl.model.RecordBrief;
import com.example.vinyl.model.Track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private GenreService genreService;
    private RecordService recordService;
    private GroupService groupService;
    private PerformerService performerService;

    @Autowired
    public SearchService(GenreService genreService, RecordService recordService, GroupService groupService,
            PerformerService performerService) {
        this.genreService = genreService;
        this.recordService = recordService;
        this.groupService = groupService;
        this.performerService = performerService;
    }

    public List<RecordBrief> searchByBarcode(String barcode) {
        // сначала ищем во внутр сервисе есть ли у нас такие пластинки. если нет - ищем
        // по дискогсу
        List<Record> records = recordService.getByBarcode(barcode);
        if (records.size() > 0) {
            List<RecordBrief> recordsBriefs = new ArrayList<RecordBrief>();
            for (int i = 0; i < records.size(); i++) {
                RecordBrief newRecord = new RecordBrief();
                Record recI = records.get(i);
                newRecord.setTitle(recI.getName());
                newRecord.setYear(recI.getYear());
                newRecord.setGenre(recI.getGenre().getName());
                Cover[] covers = recI.getCovers().toArray(new Cover[0]);
                if (covers.length > 0)
                    newRecord.setCover(covers[0].getPicture());
                recordsBriefs.add(newRecord);
            }
            return recordsBriefs;
        } else {
            return searchByBarcodeDiscogs(barcode);
        }
    }

    public List<RecordBrief> searchByBarcodeDiscogs(String barcode) {
        try {
            String url = "https://api.discogs.com/database/search?barcode=" +
                    URLEncoder.encode(barcode, StandardCharsets.UTF_8.toString()) +
                    "&token=zxUaBYodbchnbbgwQexbAhGpbSXFCowWloLjxSVK";
            String respSearch = HttpRequest.get(url);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(respSearch);
            int cnt = jsonNode.get("pagination").get("items").asInt();

            List<RecordBrief> records = new ArrayList<RecordBrief>();

            for (int i = 0; i < cnt; i++) {
                RecordBrief newRecord = new RecordBrief();
                JsonNode nodeI = jsonNode.get("results").get(i);
                newRecord.setTitle(nodeI.get("title").asText());
                var nodeYear = nodeI.get("year");
                if (nodeYear != null)
                    newRecord.setYear(nodeYear.asInt());
                String genreStr = nodeI.get("genre").get(0).asText();
                newRecord.setGenre(genreStr);
                newRecord.setBarcode(nodeI.get("barcode").get(0).asText());
                newRecord.setCover(nodeI.get("cover_image").asText());
                newRecord.setSourceUID(nodeI.get("resource_url").asText());
                records.add(newRecord);
            }

            return records;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Performer getPerformer(JsonNode jsonNodeArtist, Group group) {
        String perfName = jsonNodeArtist.get("name").asText();
        // сначала смотрим есть ли в бд
        Performer performer = performerService.getByName(perfName);
        // если нет
        if (performer == null) {
            performer = new Performer();
            performer.setName(perfName);
            if (group != null)
                performer.setGroup(group);
            // понять откуда брать картинки потому что в response body пустые строки
            performer.setPicture("");
            performerService.add(performer);
        }
        return performer;
    }

    public Group getGroup(JsonNode jsonNodeArtist) {
        String groupName = jsonNodeArtist.get("name").asText();
        // сначала смотрим есть ли в бд
        Group group = groupService.getByName(groupName);
        // если нет
        if (group == null) {
            group = new Group();
            group.setName(groupName);
            // понять откуда брать картинки потому что в response body пустые строки
            group.setPicture("");
            groupService.add(group);

            // теперь добавляем всех участников
            var mems = jsonNodeArtist.get("members");
            for (int i = 0; i < mems.size(); i++) {
                try {
                    JsonNode jsonNodePerformer = mems.get(i);
                    this.getPerformer(jsonNodePerformer, group);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return group;
    }

    public Record addFullBriefs(RecordBrief recordBrief) {
        try {
            // сначала смотрим есть ли у нас такая пластинка в бд
            Record newRecord = recordService.searchRecord(recordBrief.getTitle());

            if (newRecord == null) {
                String url = recordBrief.getSourceUID();
                String respSearch = HttpRequest.get(url);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(respSearch);

                newRecord = new Record();
                newRecord.setName(recordBrief.getTitle());
                Integer year = recordBrief.getYear();
                if (year != null)
                    newRecord.setYear(year);
                Genre genre = genreService.getByName(recordBrief.getGenre());
                if (genre != null)
                    newRecord.setGenre(genre);
                newRecord.setPublisher(jsonNode.get("labels").get(0).get("name").asText());
                newRecord.setBarcode(recordBrief.getBarcode());
                newRecord.addCover(recordBrief.getCover());

                // здесь полное описание артиста
                // чтобы понимать артист у нас или группа смотрим на поле members
                // если такого нет, то это соло исполнитель, если есть - группа
                var artist = jsonNode.get("artists"); // их несколько
                for (int i = 0; i < artist.size(); i++) {
                    String artistURL = artist.get(0).get("resource_url").asText();
                    String respArtist = HttpRequest.get(artistURL);
                    JsonNode jsonNodeArtist = objectMapper.readTree(respArtist);
                    if (jsonNodeArtist.get("members") != null) {
                        Group group = this.getGroup(jsonNodeArtist);
                        newRecord.addGroup(group);
                    } else {
                        Performer performer = this.getPerformer(jsonNodeArtist, null);
                        newRecord.addPerformer(performer);
                    }
                }

                // добавляем треки
                Set<Track> tracksToAdd = new HashSet<>();
                var tracks = jsonNode.get("tracklist");
                for (int i = 0; i < tracks.size(); i++) {
                    JsonNode jsonNodeTrack = tracks.get(i);
                    String title = jsonNodeTrack.get("title").asText();
                    tracksToAdd.add(new Track(title));
                }
                newRecord.setTracks(tracksToAdd);
                return recordService.addNewRecord(newRecord);
            }
            return newRecord;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
