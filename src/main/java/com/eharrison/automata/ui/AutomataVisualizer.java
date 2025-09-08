package com.eharrison.automata.ui;

import com.eharrison.automata.automata.Automata;
import com.eharrison.automata.automata.AutomataState;
import com.eharrison.automata.game.Game;
import com.eharrison.automata.game.GameState;
import com.eharrison.automata.game.Vector2D;
import com.eharrison.automata.game.Zone;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AutomataVisualizer extends JFrame {
    private final Zone zone;
    private final Game game;
    private GameState gameState;
    private final List<RenderElement> activePings = new ArrayList<>();

    public AutomataVisualizer(final Zone zone, final Map<Automata, AutomataState> automataStates) {
        this.game = new Game();
        this.zone = zone;
        gameState = new GameState(zone, automataStates, List.of(), 0);

        setTitle("Automata Visualizer");
        setMinimumSize(new Dimension((int) zone.width(), (int) zone.height()));
        setSize(800, 600); // Initial size
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        AutomataPanel panel = new AutomataPanel();
        add(panel);
        setVisible(true);

        // Timer to run the game loop
        new Timer(1000, e -> {
            activePings.clear();
            updateStates();
        }).start();

        // Timer to animate pings
        new Timer(30, e -> {
            activePings.forEach(ring -> ring.expand(30));
            repaint();
        }).start();
    }

    public void updateStates() {
        this.gameState = game.runRound(gameState);

        // Process all signals (emissions and reflections) and add corresponding render elements
        gameState.signals().forEach(signal -> {
            double angleLength = Vector2D.arcLength(signal.minAngle(), signal.maxAngle());
            if (angleLength == Vector2D.TAU) {
                activePings.add(new PingRing(100, signal, 500));
            } else {
                val delay = signal.isReflection() ? 250 : 0;
                activePings.add(new ScanRing(100, signal, delay, 500));
            }
        });

        repaint();
    }

    private class AutomataPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Get the current panel dimensions
            int panelWidth = getWidth();
            int panelHeight = getHeight();

            // Draw the black background
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, panelWidth, panelHeight);

            // Fixed zone dimensions
            int originalZoneWidth = (int) zone.width();
            int originalZoneHeight = (int) zone.height();

            // Calculate scaling factor to fit the zone proportionally within the panel
            double scaleX = (double) panelWidth / originalZoneWidth;
            double scaleY = (double) panelHeight / originalZoneHeight;
            double scale = Math.min(scaleX, scaleY);

            // Scaled zone dimensions
            int zoneWidth = (int) Math.round(originalZoneWidth * scale);
            int zoneHeight = (int) Math.round(originalZoneHeight * scale);

            // Center the scaled zone within the panel
            int zoneX = (panelWidth - zoneWidth) / 2;
            int zoneY = (panelHeight - zoneHeight) / 2;

            // Draw the zone
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(zoneX, zoneY, zoneWidth, zoneHeight);

            // Draw the grid
            g2d.setColor(Color.GRAY);
            int gridSize = 50; // Original grid size
            int scaledGridSize = (int) Math.round(gridSize * scale); // Scale the grid size
            for (int x = zoneX; x <= zoneX + zoneWidth; x += scaledGridSize) {
                g2d.drawLine(x, zoneY, x, zoneY + zoneHeight);
            }
            for (int y = zoneY; y <= zoneY + zoneHeight; y += scaledGridSize) {
                g2d.drawLine(zoneX, y, zoneX + zoneWidth, y);
            }

            // Draw automata
            gameState.automata().forEach((automata, state) -> {
                Vector2D location = state.location();
                int x = zoneX + (int) Math.round(location.x() * scale);
                int y = zoneY + (int) Math.round(location.y() * scale);

                // Draw automata as a circle
                g2d.setColor(automata.brain().color());
                g2d.fillOval(x - 5, y - 5, 10, 10);

                // Draw automata label
                g2d.setColor(Color.BLACK);
                FontMetrics metrics = g2d.getFontMetrics();
                int textWidth = metrics.stringWidth(automata.brain().name());
                g2d.drawString(automata.brain().name(), x - textWidth / 2, y - 10);
            });

            // Draw scan rings
            for (RenderElement ring : activePings) {
                if (ring instanceof PingRing pingRing) {
                    int x = zoneX + (int) Math.round(pingRing.center().x() * scale);
                    int y = zoneY + (int) Math.round(pingRing.center().y() * scale);
                    int radius = (int) Math.round(pingRing.radius() * scale);

                    // Set transparency and color
                    float alpha = pingRing.getAlpha();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2d.setColor(pingRing.getColor());
                    g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);
                } else if (ring instanceof ScanRing scanRing) {
                    int x = zoneX + (int) Math.round(scanRing.center().x() * scale);
                    int y = zoneY + (int) Math.round(scanRing.center().y() * scale);
                    int radius = (int) Math.round(scanRing.radius() * scale);

                    // Set transparency and color
                    float alpha = scanRing.getAlpha();
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2d.setColor(scanRing.getColor());

                    // Draw arc
                    g2d.drawArc(
                            x - radius, y - radius, radius * 2, radius * 2,
                            (int) scanRing.getStartDegrees(),
                            (int) scanRing.getArcDegrees()
                    );
                }
            }

            // Reset transparency
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
    }
}
