package com.gee.restapi.service;

import com.gee.restapi.exception.NotFoundException;
import com.gee.restapi.model.Publisher;
import com.gee.restapi.model.PublisherRequest;
import com.gee.restapi.respository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService implements IPublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Publisher createPublisher(PublisherRequest request) {

        Publisher publisher = new Publisher();
        publisher.setName(request.getName());

        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisher(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publisher with id: " + id + " not found"));
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public Publisher updatePublisher(Long id, PublisherRequest request) {
        Publisher publisher = getPublisher(id);
        publisher.setName(request.getName());

        return publisherRepository.save(publisher);
    }

    @Override
    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }
}
