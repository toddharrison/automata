package com.eharrison.automata.game;

import com.eharrison.automata.game.pente.PenteAction;
import com.eharrison.automata.game.pente.PenteConfig;
import com.eharrison.automata.game.pente.PenteGame;
import com.eharrison.automata.game.pente.PenteState;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
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

        @BeforeEach
        public void setUp() {
            lenient().when(config.capturesToWin()).thenReturn(5);
            lenient().when(config.size()).thenReturn(5);
            lenient().when(bot1.getId()).thenReturn(UUID.randomUUID());
            lenient().when(bot2.getId()).thenReturn(UUID.randomUUID());
        }

        @Test
        public void firstMoveMustBeInCenterOrYouLose() {
            // Arrange
            val size = 5;
            val board = new UUID[size][size];
            val state = new PenteState(bot1, bot2, board, true);

            when(bot1.act(any())).thenReturn(action);
            when(action.row()).thenReturn(size / 2);
            when(action.col()).thenReturn(0);

            // Act
            val result = game.run(config, List.of(bot1, bot2), 0, state);

            // Assert
            assertEquals(0, result.rounds());
            assertEquals(bot2, result.winner());
        }

        @Nested
        public class HorizontalSequenceWinTest {
            @Test
            public void oneHorizontalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                when(config.size()).thenReturn(size);
                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(0);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void twoHorizontalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[2][0] = bot1.getId();

                when(config.size()).thenReturn(size);
                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(1);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void threeHorizontalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot1.getId();

                when(config.size()).thenReturn(size);
                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fourHorizontalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot1.getId();
                board[2][2] = bot1.getId();

                when(config.size()).thenReturn(size);
                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fiveHorizontalWins() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot1.getId();
                board[2][2] = bot1.getId();
                board[2][3] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(4);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertTrue(update.result().isPresent());
                assertEquals(bot1, update.result().get().winner());
            }
        }

        @Nested
        public class VerticalSequenceWinTest {
            @Test
            public void oneVerticalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(0);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void twoVerticalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][2] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(1);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void threeVerticalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][2] = bot1.getId();
                board[1][2] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fourVerticalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][2] = bot1.getId();
                board[1][2] = bot1.getId();
                board[2][2] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(3);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fiveVerticalWins() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][2] = bot1.getId();
                board[1][2] = bot1.getId();
                board[2][2] = bot1.getId();
                board[3][2] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(4);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertTrue(update.result().isPresent());
                assertEquals(bot1, update.result().get().winner());
            }
        }

        @Nested
        public class DiagonalSequenceWinTest {
            @Test
            public void oneDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(0);
                when(action.col()).thenReturn(0);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void twoDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][0] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(1);
                when(action.col()).thenReturn(1);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void threeDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][0] = bot1.getId();
                board[1][1] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fourDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][0] = bot1.getId();
                board[1][1] = bot1.getId();
                board[2][2] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(3);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fiveDiagonalWins() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][0] = bot1.getId();
                board[1][1] = bot1.getId();
                board[2][2] = bot1.getId();
                board[3][3] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(4);
                when(action.col()).thenReturn(4);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertTrue(update.result().isPresent());
                assertEquals(bot1, update.result().get().winner());
            }
        }

        @Nested
        public class InverseDiagonalSequenceWinTest {
            @Test
            public void oneDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(0);
                when(action.col()).thenReturn(4);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void twoDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][4] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(1);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void threeDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][4] = bot1.getId();
                board[1][3] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(2);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fourDiagonalDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][4] = bot1.getId();
                board[1][3] = bot1.getId();
                board[2][2] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(3);
                when(action.col()).thenReturn(1);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fiveDiagonalWins() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[0][4] = bot1.getId();
                board[1][3] = bot1.getId();
                board[2][2] = bot1.getId();
                board[3][1] = bot1.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(4);
                when(action.col()).thenReturn(0);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertTrue(update.result().isPresent());
                assertEquals(bot1, update.result().get().winner());
            }
        }

        @Nested
        public class CaptureWinTest {
            @Test
            public void oneCaptureDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 0, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot2.getId();
                board[2][2] = bot2.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertEquals(1, update.state().bot1Captures());
                assertFalse(update.result().isPresent());
            }

            @Test
            public void twoCapturesDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 1, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot2.getId();
                board[2][2] = bot2.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertEquals(2, update.state().bot1Captures());
                assertFalse(update.result().isPresent());
            }

            @Test
            public void threeCapturesDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 2, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot2.getId();
                board[2][2] = bot2.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertEquals(3, update.state().bot1Captures());
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fourCapturesDoesNotWin() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 3, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot2.getId();
                board[2][2] = bot2.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertEquals(4, update.state().bot1Captures());
                assertFalse(update.result().isPresent());
            }

            @Test
            public void fiveCapturesWins() {
                // Arrange
                val round = 2;
                val size = 5;
                val board = new UUID[size][size];
                val state = new PenteState(round, bot1, bot2, board, null, 4, 0, bot1);

                board[2][0] = bot1.getId();
                board[2][1] = bot2.getId();
                board[2][2] = bot2.getId();

                when(bot1.act(any())).thenReturn(action);
                when(action.row()).thenReturn(2);
                when(action.col()).thenReturn(3);

                // Act
                val update = game
                        .updateState(config, 0, state, List.of(bot1, bot2));

                // Assert
                assertEquals(5, update.state().bot1Captures());
                assertTrue(update.result().isPresent());
                assertEquals(bot1, update.result().get().winner());
            }
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

            // Assert
            assertEquals(gamesToPlay, result.gamesPlayed());
            assertEquals(gamesToPlay, result.wins().get(bot2)); // MinMaxPenteBot should win all games against random
        }
    }
}
