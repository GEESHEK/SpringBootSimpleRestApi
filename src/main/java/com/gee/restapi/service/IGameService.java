package com.gee.restapi.service;

import com.gee.restapi.model.dto.GameDto;
import com.gee.restapi.model.request.GameRequest;

import java.util.List;

public interface IGameService {

    GameDto createGame(GameRequest request);
    GameDto getGame(Long id);
    List<GameDto> getAllGames();
    GameDto updateGame(Long id, GameRequest request);
    void deleteGame(Long id);

}
