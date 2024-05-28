package com.example.fitnessspring.controllers;
import com.rometools.rome.feed.synd.SyndFeed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class RSSController {

    @GetMapping("/rss")
    public ResponseEntity<String> getTheVergeRss() {
        String rssUrl = "https://feeds.feedburner.com/AceFitFacts.xml";
        RestTemplate restTemplate = new RestTemplate();
        String rssResponse = restTemplate.getForObject(rssUrl, String.class);
        return ResponseEntity.ok(rssResponse);
    }
}
