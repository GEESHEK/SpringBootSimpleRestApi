package com.gee.restapi.service;

import com.gee.restapi.exception.NotFoundException;
import com.gee.restapi.model.dto.GameDto;
import com.gee.restapi.model.dto.PublisherDto;
import com.gee.restapi.model.dto.PublisherWithGamesDto;
import com.gee.restapi.model.entity.Publisher;
import com.gee.restapi.model.request.PublisherRequest;
import com.gee.restapi.respository.PublisherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherService implements IPublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public PublisherDto createPublisher(PublisherRequest request) {

        Publisher publisher = new Publisher();
        publisher.setName(request.getName());

        return mapToDto(publisherRepository.save(publisher));
    }

    @Override
    public PublisherDto getPublisher(Long id) {
        return mapToDto(getPublisherEntity(id));
    }

    @Override
    public PublisherWithGamesDto getPublisherWithGames(Long id) {
        Publisher publisher = publisherRepository.findByIdWithGames(id)
                .orElseThrow(() -> new NotFoundException("Publisher with id: " + id + " not found"));

        List<GameDto> gameDtos = publisher.getGames().stream()
                .map(g -> new GameDto(g.getId(), g.getName()))
                .collect(Collectors.toList());

        return PublisherWithGamesDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .games(gameDtos)
                .build();

//        return new PublisherWithGamesDto(
//                publisher.getId(),
//                publisher.getName(),
//                publisher.getGames().stream()
//                        .map(g -> new GameDto(g.getId(), g.getName()))
//                        .collect(Collectors.toList())
//        );
    }

    @Override
    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(publisher -> new PublisherDto(publisher.getId(),publisher.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDto updatePublisher(Long id, PublisherRequest request) {
        Publisher publisher = getPublisherEntity(id);
        publisher.setName(request.getName());

        return mapToDto(publisherRepository.save(publisher));
    }

    @Override
    public void deletePublisher(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new NotFoundException("Publisher with id: " + id + " not found");
        }

        publisherRepository.deleteById(id);
    }

    @Override
    public Publisher getPublisherEntity(Long id) {
        return publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Publisher with id: " + id + " not found"));
    }

    private PublisherDto mapToDto(Publisher publisher) {
        return PublisherDto.builder()
                .id(publisher.getId())
                .name(publisher.getName())
                .build();
    }
}
