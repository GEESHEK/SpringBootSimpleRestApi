package com.gee.restapi.respository;

import com.gee.restapi.model.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

//    @EntityGraph(attributePaths = "games")
    @Query("SELECT p FROM Publisher p LEFT JOIN FETCH p.games WHERE p.id = :id")
    Optional<Publisher> findByIdWithGames(Long id);

}
