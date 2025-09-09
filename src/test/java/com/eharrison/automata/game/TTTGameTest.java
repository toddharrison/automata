package com.eharrison.automata.game;

import com.eharrison.automata.game.tictactoe.TTTConfig;
import com.eharrison.automata.game.tictactoe.TTTGame;
import com.eharrison.automata.game.tictactoe.bot.RandomMoveBot;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TTTGameTest {
    private TTTGame game;

    @BeforeEach
    public void setUp() {
        game = new TTTGame();
    }

    @Test
    public void call() {
        // Arrange
        val config = new TTTConfig(5);
        val bots = List.<TTTBot>of(new RandomMoveBot(), new RandomMoveBot());

        // Act
        val result = game.run(config, bots);

        // Assert
        System.out.println(result.winner() == null ? "Draw" : "Winner: " + (result.winner() == bots.get(0) ? "Bot 1" : "Bot 2"));
    }
}
