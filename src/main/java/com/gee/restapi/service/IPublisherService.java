package com.gee.restapi.service;

import com.gee.restapi.model.dto.PublisherDto;
import com.gee.restapi.model.dto.PublisherWithGamesDto;
import com.gee.restapi.model.entity.Publisher;
import com.gee.restapi.model.request.PublisherRequest;

import java.util.List;

public interface IPublisherService {

   PublisherDto createPublisher(PublisherRequest request);
   PublisherDto getPublisher(Long id);
   PublisherWithGamesDto getPublisherWithGames(Long id);
   Publisher getPublisherEntity(Long id);
   List<PublisherDto> getAllPublishers();
   PublisherDto updatePublisher(Long id, PublisherRequest request);
   void deletePublisher(Long id);

}
