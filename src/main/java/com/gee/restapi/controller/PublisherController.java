package com.gee.restapi.controller;

import com.gee.restapi.model.dto.PublisherDto;
import com.gee.restapi.model.dto.PublisherWithGamesDto;
import com.gee.restapi.model.request.PublisherRequest;
import com.gee.restapi.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @GetMapping
    public List<PublisherDto> getPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("{id}")
    public PublisherDto getPublisher(@PathVariable Long id) {
        return publisherService.getPublisher(id);
    }

    @GetMapping("/{id}/with-games")
    public PublisherWithGamesDto getPublisherWithGames(@PathVariable Long id) {
        return publisherService.getPublisherWithGames(id);
    }

    @PostMapping
    public ResponseEntity<PublisherDto> createPublisher(@RequestBody PublisherRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.createPublisher(request));
    }

    @PutMapping("{id}")
    public PublisherDto updatePublisher(@PathVariable Long id, @RequestBody PublisherRequest request) {
        return publisherService.updatePublisher(id, request);
    }
}
