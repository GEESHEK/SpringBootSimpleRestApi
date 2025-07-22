package com.gee.restapi.service;

import com.gee.restapi.exception.NotFoundException;
import com.gee.restapi.model.dto.GameDto;
import com.gee.restapi.model.entity.Game;
import com.gee.restapi.model.entity.Publisher;
import com.gee.restapi.model.request.GameRequest;
import com.gee.restapi.respository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService implements IGameService {

    private final GameRepository gameRepository;
    private final PublisherService publisherService;

    public GameService(GameRepository gameRepository, PublisherService publisherService) {
        this.gameRepository = gameRepository;
        this.publisherService = publisherService;
    }

    @Override
    public GameDto createGame(GameRequest request) {
        Publisher publisher = publisherService.getPublisherEntity(request.getPublisherId());

        Game game = new Game();
        game.setName(request.getName());
        game.setPublisher(publisher);

        return mapToDto(gameRepository.save(game));
    }

    @Override
    public GameDto getGame(Long id) {
        return mapToDto(getGameEntity(id));
    }

    @Override
    public List<GameDto> getAllGames() {
        List<Game> games = gameRepository.findAll();
        return games.stream()
                .map(game -> new GameDto(game.getId(), game.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public GameDto updateGame(Long id, GameRequest request) {
        Game game = getGameEntity(id);
        game.setName(request.getName());

        return mapToDto(gameRepository.save(game));
    }

    @Override
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    private Game getGameEntity(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Game with id " + id + " not found"));
    }

    private GameDto mapToDto(Game game) {
        return GameDto.builder()
                .id(game.getId())
                .name(game.getName())
                .build();
    }
}
