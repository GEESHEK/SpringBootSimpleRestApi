package com.gee.restapi.controller;

import com.gee.restapi.model.Game;
import com.gee.restapi.model.GameRequest;
import com.gee.restapi.service.IGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/games")
public class GameController {

    private final IGameService gameService;

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("{id}")
    public Game getGame(@PathVariable Long id) {
        return gameService.getGame(id);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody GameRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(request));
    }

    @PutMapping("{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody GameRequest request) {
        return gameService.updateGame(id, request);
    }
}
