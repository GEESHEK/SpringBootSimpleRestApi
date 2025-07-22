package com.gee.restapi.service;

import com.gee.restapi.model.Game;
import com.gee.restapi.model.GameRequest;

import java.util.List;

public interface IGameService {

    Game createGame(GameRequest request);
    Game getGame(Long id);
    List<Game> getAllGames();
    Game updateGame(Long id, GameRequest request);
    void deleteGame(Long id);

}
