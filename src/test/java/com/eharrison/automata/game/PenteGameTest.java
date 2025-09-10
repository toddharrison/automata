package com.eharrison.automata.game;

import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteConfig;
import com.eharrison.automata.game.pente.PenteGame;
import com.eharrison.automata.game.pente.bot.ForfeitBot;
import com.eharrison.automata.game.pente.bot.MinMaxPenteBot;
import com.eharrison.automata.game.pente.bot.PenteBot;
import com.eharrison.automata.game.pente.bot.RandomMoveBot;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PenteGameTest {
    private @Mock Random random;

    private PenteGame game;

    @BeforeEach
    public void setUp() {
        game = new PenteGame(random);
    }

    @Nested
    public class RulesTest {
        private @Mock PenteConfig config;
        private @Mock PenteBot bot1;
        private @Mock PenteBot bot2;
        private @Mock PenteAction action;

        @Test
        public void firstMoveMustBeInCenterOrYouLose() {
            // Arrange
            val size = 5;
            val gamesToPlay = 5;

            when(config.gamesInMatch()).thenReturn(gamesToPlay);
            when(config.size()).thenReturn(size);
            when(bot1.act(any())).thenReturn(action);
            when(action.row()).thenReturn(size / 2);
            when(action.col()).thenReturn(0);
            when(random.nextBoolean()).thenReturn(true);

            // Act
            val result = game.runMatch(config, List.of(bot1, bot2));

            // Assert
            assertEquals(gamesToPlay, result.gamesPlayed());
            assertEquals(gamesToPlay, result.wins().get(bot2)); // bot2 should win because bot1 made an invalid move
        }
    }

    @Nested
    public class ForfeitTest {
        private @Mock PenteBot bot1;

        @Test
        public void loseEveryGameInEveryMatch() {
            // Arrange
            val gamesToPlay = 5;
            val config = new PenteConfig(gamesToPlay);
            val bot2 = new ForfeitBot();

            when(random.nextBoolean()).thenReturn(false);

            // Act
            val result = game.runMatch(config, List.of(bot1, bot2));

            // Assert
            assertEquals(gamesToPlay, result.wins().get(bot1));
            assertTrue(result.results().stream().allMatch(r -> r.winner() == bot1));
            assertTrue(result.results().stream().allMatch(r -> r.rounds() == 0));
        }
    }

    @Nested
    public class FunctionalTest {
        @Test
        public void call() {
            // Arrange
            val gamesToPlay = 5;
            val config = new PenteConfig(gamesToPlay);
            val bot1 = new RandomMoveBot();
            val bot2 = new MinMaxPenteBot();

            when(random.nextBoolean()).thenReturn(false);

            // Act
            val result = game.runMatch(config, List.of(bot1, bot2));

//            result.results().forEach(r -> System.out.println(r.state().display()));
//            System.out.println(result.wins());

            // Assert
            assertEquals(gamesToPlay, result.gamesPlayed());
            assertEquals(gamesToPlay, result.wins().get(bot2)); // MinMaxPenteBot should win all games against random
        }
    }
}
