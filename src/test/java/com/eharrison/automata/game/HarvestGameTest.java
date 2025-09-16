package com.eharrison.automata.game;

import com.eharrison.automata.game.harvest.HarvestConfig;
import com.eharrison.automata.game.harvest.HarvestGame;
import com.eharrison.automata.game.harvest.bot.HarvestRandomBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class HarvestGameTest {
    private HarvestGame game;

    @BeforeEach
    public void setUp() {
        game = new HarvestGame();
    }

    @Nested
    public class FunctionalTest {
        @Test
        public void call() {
            // Arrange
            val gamesToPlay = 1;
            val maxRounds = 25;
            val size = 5;
            val config = new HarvestConfig(gamesToPlay, maxRounds, size);
            val bot1 = new HarvestRandomBot();
            val bot2 = new HarvestRandomBot();

            // Act
            val result = game.runMatch(config, List.of(bot1, bot2));

            // Assert
            assertEquals(gamesToPlay, result.gamesPlayed());

            result.results().forEach(r -> System.out.println(r.state().display()));
            System.out.println("Wins:");
            System.out.println(result.wins().entrySet().stream()
                    .map(e -> (e.getKey() == bot1 ? "Bot1" : "Bot2") + " = " + e.getValue())
                    .toList());
            System.out.println("Draws: " + result.draws());
        }
    }
}
