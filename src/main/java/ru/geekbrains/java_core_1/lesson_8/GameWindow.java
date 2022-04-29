package ru.geekbrains.java_core_1.lesson_8;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final int WINDOW_WIDTH = 480;
    private static final int WINDOW_HEIGHT = 480;
    private GameMap gameMap;

    public GameWindow() {
        setTitle("TicTacToe");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setLayout(new BorderLayout());
        JButton buttonStart = new JButton("<html><body><b>START</b></body></html>");
        JButton buttonExit = new JButton("EXIT");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(buttonStart);
        buttonPanel.add(buttonExit);
        add(buttonPanel, BorderLayout.SOUTH);
        gameMap = new GameMap();
        add(gameMap, BorderLayout.CENTER);
        SettingsWindow settings = new SettingsWindow(this);
        buttonStart.addActionListener(e -> settings.setVisible(true));
        buttonExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    public void startGame(int gameMode, int fieldSize, int winLength) {
        gameMap.startNewGame(gameMode, fieldSize, winLength);
        System.out.printf("Mode: %d, Size: %d length: %d\n", gameMode, fieldSize, winLength);
    }

}
