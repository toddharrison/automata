package com.eharrison.automata.game;

import com.eharrison.automata.game.claim.ClaimConfig;
import com.eharrison.automata.game.claim.ClaimGame;
import com.eharrison.automata.game.claim.bot.RandomClaimBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ClaimGameTest {
    private ClaimGame game;

    @BeforeEach
    public void setUp() {
        game = new ClaimGame();
    }

    @Nested
    public class FunctionalTest {
        @Test
        public void call() {
            // Arrange
            val gamesToPlay = 1;
            val maxRounds = 20;
            val size = 5;
            val config = new ClaimConfig(gamesToPlay, maxRounds, size);
            val bot1 = new RandomClaimBot();
            val bot2 = new RandomClaimBot();

            // Act
            val result = game.runMatch(config, List.of(bot1, bot2));

            result.results().forEach(r -> System.out.println(r.state().display()));

            // Assert
//            assertEquals(gamesToPlay, result.gamesPlayed());
//            assertEquals(gamesToPlay, result.wins().get(bot2)); // MinMaxPenteBot should win all games against random
        }
    }
}
