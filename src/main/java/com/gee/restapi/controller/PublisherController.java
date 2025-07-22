package com.gee.restapi.controller;

import com.gee.restapi.model.Publisher;
import com.gee.restapi.model.PublisherRequest;
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
    public List<Publisher> getPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("{id}")
    public Publisher getPublisher(@PathVariable Long id) {
        return publisherService.getPublisher(id);
    }

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@RequestBody PublisherRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.createPublisher(request));
    }

    @PutMapping("{id}")
    public Publisher updatePublisher(@PathVariable Long id, @RequestBody PublisherRequest request) {
        return publisherService.updatePublisher(id, request);
    }
}
