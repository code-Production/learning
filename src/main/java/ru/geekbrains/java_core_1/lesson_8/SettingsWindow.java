package ru.geekbrains.java_core_1.lesson_8;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JFrame {
    private static final int WINDOWS_WIDTH = 480;
    private static final int WINDOWS_HEIGHT = 480;
    private static final int MIN_WIN_LENGTH = 3;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final String FIELD_SIZE_PREFIX = "Field size: ";
    private static final String WIN_LENGTH_PREFIX = "Win Length: ";

    private GameWindow gameWindow;
    private JSlider winLengthSlider;
    private JSlider fieldSizeSlider;
    private JRadioButton humanVsAi;
    private JRadioButton humanVsHuman;

    public SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setSize(WINDOWS_WIDTH, WINDOWS_HEIGHT);
        //setLocationRelativeTo(gameWindow);
        setResizable(false);
        setTitle("Settings");
        setLayout(new GridLayout(10, 1));
        addFieldSizes();
        addGameMode();
        JButton buttonStart = new JButton("START");
        buttonStart.addActionListener(e -> submitSettings(gameWindow));
        add(buttonStart);
    }

    public void addFieldSizes() {
        JLabel fieldSizeLabel = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel winLengthLabel = new JLabel(WIN_LENGTH_PREFIX + MIN_WIN_LENGTH);
        winLengthSlider = new JSlider(MIN_WIN_LENGTH, MAX_FIELD_SIZE, MIN_WIN_LENGTH);
        fieldSizeSlider = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
        fieldSizeSlider.addChangeListener(l -> {
            int value = fieldSizeSlider.getValue();
            fieldSizeLabel.setText(FIELD_SIZE_PREFIX + String.valueOf(value));
            winLengthSlider.setMaximum(value);
        });
        winLengthSlider.addChangeListener(l -> {
            winLengthLabel.setText(WIN_LENGTH_PREFIX + String.valueOf(winLengthSlider.getValue()));
        });
        add(new JLabel("Choose field size:"));
        add(fieldSizeLabel);
        add(fieldSizeSlider);
        add(new JLabel("Choose win length:"));
        add(winLengthLabel);
        add(winLengthSlider);
    }

    public void addGameMode() {
        add(new JLabel("Choose game mode:"));
        humanVsAi = new JRadioButton("Human vs AI", true);
        humanVsHuman = new JRadioButton("Human vs Human");
        ButtonGroup group = new ButtonGroup();
        group.add(humanVsAi);
        group.add(humanVsHuman);
        add(humanVsAi);
        add(humanVsHuman);
    }

    public void submitSettings(GameWindow gameWindow) {
        int gameMode;
        if (humanVsAi.isSelected()){
            gameMode = GameMap.HUMAN_VS_AI;
        } else {
            gameMode = GameMap.HUMAN_VS_HUMAN;
        }
        int fieldSize = fieldSizeSlider.getValue();
        int winLength = winLengthSlider.getValue();
        gameWindow.startGame(gameMode, fieldSize, winLength);
        setVisible(false);
    }



}
