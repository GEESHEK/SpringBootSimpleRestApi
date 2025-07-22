package com.gee.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gee.restapi.model.entity.Game;
import com.gee.restapi.model.entity.Publisher;
import com.gee.restapi.model.request.PublisherRequest;
import com.gee.restapi.respository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PublisherControllerIntegrationTests {

    private MockMvc mockMvc;
    private PublisherRepository publisherRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public PublisherControllerIntegrationTests(MockMvc mockMvc, PublisherRepository publisherRepository) {
        this.mockMvc = mockMvc;
        this.publisherRepository = publisherRepository;
        this.objectMapper = new ObjectMapper();
    }

    @BeforeEach
    public void setup() {
        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher1");

        Game game = new Game();
        game.setName("Test Game1");
        game.setPublisher(publisher); // must set the publisher on the key, not as smart as EF

        List<Game> games = new ArrayList<>();
        games.add(game);
        publisher.setGames(games);

        publisherRepository.save(publisher);
    }

    @Test
    public void testFindAllPublishersReturnsHttpStatus200AndListOfPublishers() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/publishers")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$[0].id").value(1),
                MockMvcResultMatchers.jsonPath("$[0].name").value("Test Publisher1")
        );
    }

    @Test
    public void testFindPublisherByIdReturnsHttpStatus200AndPublisher() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/publishers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.id").value(1),
                MockMvcResultMatchers.jsonPath("$.name").value("Test Publisher1")
        );
    }

//    @GetMapping("/{id}/with-games")
//    public PublisherWithGamesDto getPublisherWithGames(@PathVariable Long id) {
//        return publisherService.getPublisherWithGames(id);
//    }

    @Test
    public void testFindPublisherByIdReturnsHttpStatus200AndPublisherIncludingGames() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/publishers/1/with-games")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.id").value(1),
                MockMvcResultMatchers.jsonPath("$.name").value("Test Publisher1"),
                MockMvcResultMatchers.jsonPath("$.games[0].name").value("Test Game1"),
                MockMvcResultMatchers.jsonPath("$.games.length()").value(1)
        );
    }

    @Test
    public void testFindPublisherByIdReturnsHttpStatus201AndSavedPublisher() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/publishers/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testCreatePublisherReturnsHttpStatus201WhenPublisherDoesNotExist() throws Exception {
        Publisher testPublisher = new Publisher();
        testPublisher.setName("Tester");
        String json = objectMapper.writeValueAsString(testPublisher);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/publishers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.jsonPath("$.id").isNumber(),
                MockMvcResultMatchers.jsonPath("$.name").value(testPublisher.getName())
        );
    }

    @Test
    public void testThatUpdatePublisherByIdReturnsHttpStatus200WhenPublisherExist() throws Exception {
        PublisherRequest publisherRequest = new PublisherRequest();
        publisherRequest.setName("Updated Publisher");
        String json = objectMapper.writeValueAsString(publisherRequest);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/v1/publishers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath("$.id").isNumber(),
                MockMvcResultMatchers.jsonPath("$.name").value(publisherRequest.getName())
        );
    }

    @Test
    public void testThatDeletePublisherByIdReturnsHttpStatus404WhenPublisherDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/publishers/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatDeletePublisherByIdReturnsHttpStatus204WhenPublisherExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/publishers/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
