package com.deviget.minesweepergame.repositories;

import com.deviget.minesweepergame.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

	List<Game> findByUsername(String username);

	Optional<Game> findByUsernameAndId(String username, String id);

}
