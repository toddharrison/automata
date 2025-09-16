package com.eharrison.automata.ui;

import com.eharrison.automata.game.*;
import com.eharrison.automata.game.claim.ClaimConfig;
import com.eharrison.automata.game.claim.ClaimGame;
import com.eharrison.automata.game.claim.bot.ClaimBot;
import com.eharrison.automata.game.claim.bot.ClaimDoNothingBot;
import com.eharrison.automata.game.claim.bot.ClaimRandomBot;
import com.eharrison.automata.game.harvest.HarvestConfig;
import com.eharrison.automata.game.harvest.HarvestGame;
import com.eharrison.automata.game.harvest.bot.HarvestBot;
import com.eharrison.automata.game.harvest.bot.HarvestDoNothingBot;
import com.eharrison.automata.game.harvest.bot.HarvestRandomBot;
import com.eharrison.automata.game.pente.PenteConfig;
import com.eharrison.automata.game.pente.PenteGame;
import com.eharrison.automata.game.pente.bot.PenteBot;
import com.eharrison.automata.game.pente.bot.PenteForfeitBot;
import com.eharrison.automata.game.pente.bot.PenteMinMaxBot;
import com.eharrison.automata.game.pente.bot.PenteRandomMoveBot;
import com.eharrison.automata.game.rockpaperscissors.RPSConfig;
import com.eharrison.automata.game.rockpaperscissors.RPSGame;
import com.eharrison.automata.game.rockpaperscissors.bot.*;
import com.eharrison.automata.game.tictactoe.TTTConfig;
import com.eharrison.automata.game.tictactoe.TTTGame;
import com.eharrison.automata.game.tictactoe.bot.TTTBot;
import com.eharrison.automata.game.tictactoe.bot.TTTForfeitBot;
import com.eharrison.automata.game.tictactoe.bot.TTTRandomMoveBot;
import lombok.val;

import java.io.File;
import java.util.*;

public class Application {
    public static void main(final String[] args) {
        val botLoader = new BotLoader();
        registerBuiltInBots(botLoader);
        botLoader.registerBotsFromExternalJars(new File("bots"));

        System.out.println();
        runMatches(new RPSGame(), new RPSConfig(100000, 5), botLoader.getBots(RPSBot.class));
        System.out.println();
        runMatches(new TTTGame(new Random()), new TTTConfig(1000), botLoader.getBots(TTTBot.class));
        System.out.println();
        runMatches(new PenteGame(new Random()), new PenteConfig(100), botLoader.getBots(PenteBot.class));
        System.out.println();
        runMatches(new ClaimGame(), new ClaimConfig(100, 25, 5), botLoader.getBots(ClaimBot.class));
        System.out.println();
        runMatches(new HarvestGame(), new HarvestConfig(100, 25, 5), botLoader.getBots(HarvestBot.class));
    }

    private static void registerBuiltInBots(final BotLoader botLoader) {
        // Rock-Paper-Scissors
        botLoader.registerBot(new RPSAlwaysRockBot());
        botLoader.registerBot(new RPSAlwaysSameBot());
        botLoader.registerBot(new RPSAlwaysScissorsBot());
        botLoader.registerBot(new RPSRandomBot());
        botLoader.registerBot(new RPSForfeitBot());

        // Tic-Tac-Toe
        botLoader.registerBot(new TTTRandomMoveBot());
        botLoader.registerBot(new TTTForfeitBot());

        // Pente
        botLoader.registerBot(new PenteMinMaxBot());
        botLoader.registerBot(new PenteRandomMoveBot());
        botLoader.registerBot(new PenteForfeitBot());

        // Claim
        botLoader.registerBot(new ClaimRandomBot());
        botLoader.registerBot(new ClaimDoNothingBot());

        // Harvest
        botLoader.registerBot(new HarvestRandomBot());
        botLoader.registerBot(new HarvestDoNothingBot());
    }

    private static <
            C extends Config,
            S extends State<C,S,V,A,R,B>,
            V extends View,
            A extends Action,
            R extends Result<C,S,V,A,R,B>,
            B extends Bot<C,S,V,A,R,B>
    > void runMatches(final Game<C,S,V,A,R,B> game, final C config, final Set<B> bots2) {
        val bots = bots2.stream().sorted(Comparator.comparing(Bot::getType)).toList();
        System.out.println(game.getName());
        System.out.println("Bot1\tTeam1\tBot2\tTeam2\tBot1 Wins\tBot2 Wins\tDraws");
        for (int i = 0; i < bots.size(); i++) {
            for (int j = i + 1; j < bots.size(); j++) {
                val bot1 = bots.get(i);
                val bot2 = bots.get(j);
                val match = game.runMatch(config, List.of(bot1, bot2));

                val bot1Wins = Optional.ofNullable(match.wins().get(bot1)).orElse(0);
                val bot2Wins = Optional.ofNullable(match.wins().get(bot2)).orElse(0);
                val draws = match.draws();
                System.out.println(bot1.getType() + "\t" + bot1.getTeamName() + "\t" + bot2.getType() + "\t" +
                        bot2.getTeamName() + "\t" + bot1Wins + "\t" + bot2Wins + "\t" + draws);
            }
        }
    }
}
