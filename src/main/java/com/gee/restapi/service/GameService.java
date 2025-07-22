package com.gee.restapi.service;

import com.gee.restapi.exception.NotFoundException;
import com.gee.restapi.model.Game;
import com.gee.restapi.model.GameRequest;
import com.gee.restapi.model.Publisher;
import com.gee.restapi.respository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService implements IGameService {

    private final GameRepository gameRepository;
    private final PublisherService publisherService;

    public GameService(GameRepository gameRepository, PublisherService publisherService) {
        this.gameRepository = gameRepository;
        this.publisherService = publisherService;
    }

    @Override
    public Game createGame(GameRequest request) {
        Publisher publisher = publisherService.getPublisher(request.getPublisherId());

        Game game = new Game();
        game.setName(request.getName());
        game.setPublisher(publisher);

        return gameRepository.save(game);
    }

    @Override
    public Game getGame(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game with id " + id + " not found"));
    }

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public Game updateGame(Long id, GameRequest request) {
        Game game = getGame(id);
        game.setName(request.getName());

        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

}
