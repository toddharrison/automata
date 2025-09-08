package com.eharrison.automata.game;

import com.eharrison.automata.game.rockpaperscissors.RPSConfig;
import com.eharrison.automata.game.rockpaperscissors.RPSGame;
import com.eharrison.automata.game.rockpaperscissors.bot.AlwaysRockBot;
import com.eharrison.automata.game.rockpaperscissors.bot.AlwaysScissorsBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RPSGameTest {
    private RPSGame game;

    @BeforeEach
    public void setUp() {
        game = new RPSGame();
    }

    @Test
    public void call() {
        // Arrange
        val gamesToPlay = 1;
        val rounds = 5;
        val config = new RPSConfig(gamesToPlay, rounds);
        val bots = List.of(new AlwaysRockBot(), new AlwaysScissorsBot());

        // Act
        val result = game.run(config, bots);

        // Assert
        assertEquals(rounds, result.rounds());
        assertEquals(rounds, result.finalScores().get(bots.get(0))); // AlwaysRockBot should win all rounds
        assertEquals(0, result.finalScores().get(bots.get(1))); // AlwaysScissorsBot should lose all rounds
    }
}
