package com.eharrison.automata.game;

import com.eharrison.automata.game.tictactoe.TTTConfig;
import com.eharrison.automata.game.tictactoe.TTTGame;
import com.eharrison.automata.game.tictactoe.bot.RandomMoveBot;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TTTGameTest {
    private @Mock Random random;

    private TTTGame game;

    @BeforeEach
    public void setUp() {
        game = new TTTGame(random);
    }

    @Test
    public void call() {
        // Arrange
        val gamesToPlay = 5;
        val config = new TTTConfig(gamesToPlay);
        val bots = List.<TTTBot>of(new RandomMoveBot(), new RandomMoveBot());

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
