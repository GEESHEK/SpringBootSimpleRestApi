package com.gee.restapi;

import com.gee.restapi.model.entity.Game;
import com.gee.restapi.model.entity.Publisher;
import com.gee.restapi.respository.GameRepository;
import com.gee.restapi.respository.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestapiApplication.class, args);
    }

    @Bean
    CommandLineRunner initData(PublisherRepository publisherRepo, GameRepository gameRepo) {
        return args -> {
            Publisher nintendo = new Publisher();
            nintendo.setName("Nintendo");
            publisherRepo.save(nintendo);

            Publisher sony = new Publisher();
            sony.setName("Sony");
            publisherRepo.save(sony);

            Game zelda = new Game();
            zelda.setName("Zelda");
            zelda.setPublisher(nintendo);
            gameRepo.save(zelda);

            Game godOfWar = new Game();
            godOfWar.setName("God of War");
            godOfWar.setPublisher(sony);
            gameRepo.save(godOfWar);

            System.out.println("Sample data inserted");
        };

    }
}
