package com.gee.restapi.service;

import com.gee.restapi.model.Publisher;
import com.gee.restapi.model.PublisherRequest;

import java.util.List;

public interface IPublisherService {

   Publisher createPublisher(PublisherRequest request);
   Publisher getPublisher(Long id);
   List<Publisher> getAllPublishers();
   Publisher updatePublisher(Long id, PublisherRequest request);
   void deletePublisher(Long id);

}
