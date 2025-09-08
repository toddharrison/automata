package com.eharrison.automata.game;

import com.eharrison.automata.game.pente.PenteConfig;
import com.eharrison.automata.game.pente.PenteGame;
import com.eharrison.automata.game.pente.bot.MinMaxPenteBot;
import com.eharrison.automata.game.pente.bot.PenteBot;
import com.eharrison.automata.game.pente.bot.RandomMoveBot;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PenteGameTest {
    private PenteGame game;

    @BeforeEach
    public void setUp() {
        game = new PenteGame();
    }

    @Test
    public void call() {
        // Arrange
        val config = new PenteConfig(1);
        val bots = List.<PenteBot>of(new RandomMoveBot(), new MinMaxPenteBot());

        // Act
        val result = game.run(config, bots);
        System.out.println(result.state().display());

        // Assert
        System.out.println(result.winner() == null ? "Draw" : "Winner: " + (result.winner() == bots.getFirst() ? "Bot 1" : "Bot 2"));
    }
}
