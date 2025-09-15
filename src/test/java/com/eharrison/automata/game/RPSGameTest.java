package com.eharrison.automata.game;

import com.eharrison.automata.game.rockpaperscissors.RPSAction;
import com.eharrison.automata.game.rockpaperscissors.RPSConfig;
import com.eharrison.automata.game.rockpaperscissors.RPSGame;
import com.eharrison.automata.game.rockpaperscissors.bot.AlwaysRockBot;
import com.eharrison.automata.game.rockpaperscissors.bot.AlwaysScissorsBot;
import com.eharrison.automata.game.rockpaperscissors.bot.ForfeitBot;
import com.eharrison.automata.game.rockpaperscissors.bot.RPSBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RPSGameTest {
    private RPSGame game;

    @BeforeEach
    public void setUp() {
        game = new RPSGame();
    }

    @Nested
    public class ForfeitTest {
        private @Mock RPSBot bot1;

        @Test
        public void loseEveryGameInEveryMatch() {
            // Arrange
            val gamesToPlay = 5;
            val rounds = 3;
            val config = new RPSConfig(gamesToPlay, rounds);
            val bot2 = new ForfeitBot();

            when(bot1.act(any())).thenReturn(RPSAction.ROCK);

            // Act
            val result = game.runMatch(config, List.of(bot1, bot2));

            // Assert
            assertEquals(gamesToPlay, result.wins().get(bot1));
            assertTrue(result.results().stream().allMatch(r -> r.finalScores().get(bot1) == rounds));
        }
    }

    @Test
    public void call() {
        // Arrange
        val gamesToPlay = 5;
        val rounds = 3;
        val config = new RPSConfig(gamesToPlay, rounds);
        val bot1 = new AlwaysRockBot();
        val bot2 = new AlwaysScissorsBot();

        // Act
        val result = game.runMatch(config, List.of(bot1, bot2));

//        result.results().forEach(r -> System.out.println(r.state().display()));
//        System.out.println(result.wins());

        // Assert
        assertEquals(gamesToPlay, result.gamesPlayed());
        assertEquals(gamesToPlay, result.wins().get(bot1)); // AlwaysRockBot should win all games
        assertFalse(result.wins().containsKey(bot2)); // AlwaysScissorsBot should never win any games
    }
}
