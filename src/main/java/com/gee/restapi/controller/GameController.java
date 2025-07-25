package com.gee.restapi.controller;

import com.gee.restapi.model.dto.GameDto;
import com.gee.restapi.model.request.GameRequest;
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
    public List<GameDto> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("{id}")
    public GameDto getGame(@PathVariable Long id) {
        return gameService.getGame(id);
    }

    @PostMapping
    public ResponseEntity<GameDto> createGame(@RequestBody GameRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(request));
    }

    @PutMapping("{id}")
    public GameDto updateGame(@PathVariable Long id, @RequestBody GameRequest request) {
        return gameService.updateGame(id, request);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
