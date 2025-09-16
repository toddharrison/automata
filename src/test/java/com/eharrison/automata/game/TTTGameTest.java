package com.eharrison.automata.game;

import com.eharrison.automata.game.tictactoe.TTTConfig;
import com.eharrison.automata.game.tictactoe.TTTGame;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import com.eharrison.automata.game.tictactoe.bot.TTTForfeitBot;
import com.eharrison.automata.game.tictactoe.bot.TTTRandomMoveBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TTTGameTest {
    private @Mock Random random;

    private TTTGame game;

    @BeforeEach
    public void setUp() {
        game = new TTTGame(random);
    }

    @Nested
    public class ForfeitTest {
        private @Mock TTTBot bot1;

        @Test
        public void loseEveryGameInEveryMatch() {
            // Arrange
            val gamesToPlay = 5;
            val config = new TTTConfig(gamesToPlay);
            val bot2 = new TTTForfeitBot();

            when(random.nextBoolean()).thenReturn(false);

            // Act
            val result = game.runMatch(config, List.of(bot1, bot2));

            // Assert
            assertEquals(gamesToPlay, result.wins().get(bot1));
            assertTrue(result.results().stream().allMatch(r -> r.winner() == bot1));
            assertTrue(result.results().stream().allMatch(r -> r.rounds() == 0));
        }
    }

    @Test
    public void call() {
        // Arrange
        val gamesToPlay = 5;
        val config = new TTTConfig(gamesToPlay);
        val bots = List.<TTTBot>of(new TTTRandomMoveBot(), new TTTRandomMoveBot());

        when(random.nextBoolean()).thenReturn(true);

        // Act
        val result = game.runMatch(config, bots);

//        result.results().forEach(r -> System.out.println(r.state().display()));
//        System.out.println(result.wins());

        // Assert
        assertEquals(gamesToPlay, result.gamesPlayed());
        assertEquals(gamesToPlay, result.wins().values().stream().mapToInt(Integer::intValue).sum() + result.draws());
    }
}
