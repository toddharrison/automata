package com.eharrison.automata.ui;

import com.eharrison.automata.automata.Automata;
import com.eharrison.automata.automata.AutomataState;
import com.eharrison.automata.automata.Sensor;
import com.eharrison.automata.brain.*;
import com.eharrison.automata.game.Vector2D;
import com.eharrison.automata.game.Zone;
import lombok.val;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StartupGUI extends JFrame {
    private final DefaultListModel<Automata> automataListModel = new DefaultListModel<>();
    private final Map<String, Class<? extends AutomataBrain>> builtInBrains = Map.of(
            "Do Nothing", DoNothingBrain.class,
            "Ping", PingBrain.class,
            "Scan", ScanBrain.class,
            "Random Move", RandomMoveBrain.class,
            "Flee", FleeBrain.class
    );

    public StartupGUI() throws Exception {
        val brainLoader = new AutomataBrainLoader("brains");
        val jarredBrains = brainLoader.loadBrains();

        val brainMap = new HashMap<String, Class<? extends AutomataBrain>>();
        brainMap.putAll(builtInBrains);
        brainMap.putAll(jarredBrains);

        setTitle("Automata Setup");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Dropdown for selecting brains
        JComboBox<String> brainDropdown = new JComboBox<>(brainMap.keySet().toArray(new String[0]));

        // List to display added automata
        JList<Automata> automataList = new JList<>(automataListModel);
        JScrollPane listScrollPane = new JScrollPane(automataList);

        // Buttons
        JButton addButton = new JButton("Add Automata");
        JButton startButton = new JButton("Start Game");

        // Add automata to the list
        addButton.addActionListener(event -> {
            try {
                String selectedBrain = (String) brainDropdown.getSelectedItem();
                if (selectedBrain != null) {
                    AutomataBrain brain = (AutomataBrain) brainMap.get(selectedBrain).getDeclaredConstructor().newInstance();
                    Sensor sensor = new Sensor(4, 5); // Example sensor
                    Automata automata = new Automata(UUID.randomUUID(), brain, sensor);
                    automataListModel.addElement(automata);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });

        // Start the game
        startButton.addActionListener(e -> {
            int zoneWidth = 700; // Example zone width
            int zoneHeight = 500; // Example zone height
            val zone = new Zone(zoneWidth, zoneHeight);

            if (!automataListModel.isEmpty()) {
                Map<Automata, AutomataState> automataStates = new HashMap<>();
                for (int i = 0; i < automataListModel.size(); i++) {
                    Automata automata = automataListModel.get(i);
                    double randomX = (Math.random() * (zoneWidth - 100)) + 50;
                    double randomY = (Math.random() * (zoneHeight - 100)) + 50;
                    automataStates.put(automata, new AutomataState(new Vector2D(randomX, randomY), null, null, 10));
                }
                new AutomataVisualizer(zone, automataStates);
                dispose(); // Close the setup window
            }
        });

        // Layout setup
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Select Brain:"), BorderLayout.WEST);
        topPanel.add(brainDropdown, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(startButton);

        add(topPanel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
