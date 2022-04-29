package ru.geekbrains.java_core_1.lesson_8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameMap extends JPanel {
    public static final int HUMAN_VS_AI = 0;
    public static final int HUMAN_VS_HUMAN = 1;

    public static final int DOT_EMPTY = 0;
    public static final int DOT_HUMAN = 1;
    public static final int DOT_AI = 2;

    public static final int STATE_DRAW = 0;
    public static final int STATE_WIN_HUMAN = 1;
    public static final int STATE_WIN_AI = 2;

    public static final int DOT_PADDING = 7;


    public static final Random random = new Random();
    private int stateGameOver;
    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLength;
    private int cellWidth;
    private int cellHeight;
    private boolean isGameOver;
    private boolean isInitialized;
    private int gameMode;
    private int playerNumTurn = 1;

    public GameMap() {
        isInitialized = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                update(e);
            }
        });
        repaint();
    }

    public void startNewGame(int gameMode, int fieldSize, int winLength) {
        this.gameMode = gameMode;
        fieldSizeX = fieldSize;
        fieldSizeY = fieldSize;
        this.winLength = winLength;
        field = new int[fieldSizeY][fieldSizeX];
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized) {
            return;
        }

        if (gameMode == HUMAN_VS_AI) {

            if (!playerTurn(e, DOT_HUMAN)) {
                return;
            }

            if (gameCheck(DOT_HUMAN, STATE_WIN_HUMAN)) {
                return;
            }

            aiTurn();
            repaint();

            if (gameCheck(DOT_AI, STATE_WIN_AI)) {
                return;
            }

        } else if (gameMode == HUMAN_VS_HUMAN) {

            int dot;
            int state;
            if (playerNumTurn == 1) {
                dot = DOT_HUMAN;
                state = STATE_WIN_HUMAN;
                playerNumTurn = 2;
            } else {
                dot = DOT_AI;
                state = STATE_WIN_AI;
                playerNumTurn = 1;
            }

            if (!playerTurn(e, dot)) {
                return;
            }

            if (gameCheck(dot, state)) {
                return;
            }
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isInitialized) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        cellHeight = height / fieldSizeY;
        cellWidth = width / fieldSizeX;
        g.setColor(Color.BLACK);

        for (int i = 1; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
        }

        for (int i = 1; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);
        }

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y, x)) {
                    continue;
                }

                if (field[y][x] == DOT_HUMAN) {
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                }
            }
        }

        if (isGameOver) {
            showGameOverMessage(g);
        }

    }

    private void showGameOverMessage(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, getHeight() / 2 - 55, getWidth(), 110);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times New Roman", Font.BOLD, 35));
        String result = "";
        int corr = 1;
        if (gameMode == HUMAN_VS_HUMAN) {
            result = "ANOTHER HUMAN WIN";
            corr = 3;
        } else if (gameMode == HUMAN_VS_AI){
            result = "AI WIN";
        }

        if (stateGameOver == STATE_WIN_AI) {
            g.setColor(Color.BLACK);
        } else if (stateGameOver == STATE_WIN_HUMAN){
            g.setColor(Color.BLUE);
        }

        switch (stateGameOver) {
            case STATE_DRAW -> g.drawString("DRAW", getWidth() / 3, getHeight() / 2 + 10);
            case STATE_WIN_HUMAN -> g.drawString("HUMAN WIN", getWidth() / 7, getHeight() / 2 + 10);
            case STATE_WIN_AI -> g.drawString(result, getWidth() / 3 / corr, getHeight() / 2 + 10);
        }
    }

    private boolean playerTurn(MouseEvent e, int dot) {
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;

        if (!isCellEmpty(cellY, cellX) || !isCellValid(cellY, cellX)) {
            return false;
        }
        field[cellY][cellX] = dot;
        repaint();
        return true;
    }

    private void aiTurn() {
        if (scanField(DOT_AI, winLength)) return;        // проверка выигрыша компа
        if (scanField(DOT_HUMAN, winLength)) return;    // проверка выигрыша игрока на след ходу
        if (scanField(DOT_AI, winLength - 1)) return;
        if (scanField(DOT_HUMAN, winLength - 1)) return;
        if (scanField(DOT_AI, winLength - 2)) return;
        if (scanField(DOT_HUMAN, winLength - 2)) return;
        aiTurnEasy();
    }

    private void aiTurnEasy() {
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (!isCellEmpty(y, x));
        field[y][x] = DOT_AI;
    }

    private boolean gameCheck(int dot, int stateGameOver) {
        if (checkWin(dot, winLength)) {
            this.stateGameOver = stateGameOver;
            isGameOver = true;
            repaint();
            return true;
        }
        if (checkDraw()) {
            this.stateGameOver = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }

        return false;
    }

    private boolean scanField(int dot, int length) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y, x)) {                // поставим фишку в каждую клетку поля по очереди
                    field[y][x] = dot;
                    if (checkWin(dot, length)) {
                        if (dot == DOT_AI) return true;    // если комп выигрывает, то оставляем
                        if (dot == DOT_HUMAN) {
                            field[y][x] = DOT_AI;            // Если выигрывает игрок ставим туда 0
                            return true;
                        }
                    }
                    field[y][x] = DOT_EMPTY;            // если никто ничего, то возвращаем как было
                }
            }
        }
        return false;
    }


    private boolean checkWin(int dot, int length) {
        for (int y = 0; y < fieldSizeY; y++) {            // проверяем всё поле
            for (int x = 0; x < fieldSizeX; x++) {
                if (checkLine(x, y, 1, 0, length, dot)) return true;    // проверка  по +х
                if (checkLine(x, y, 1, 1, length, dot)) return true;    // проверка по диагонали +х +у
                if (checkLine(x, y, 0, 1, length, dot)) return true;    // проверка линию по +у
                if (checkLine(x, y, 1, -1, length, dot)) return true;    // проверка по диагонали +х -у
            }
        }
        return false;
    }

    // проверка линии
    private boolean checkLine(int x, int y, int incrementX, int incrementY, int len, int dot) {
        int endXLine = x + (len - 1) * incrementX;            // конец линии по Х
        int endYLine = y + (len - 1) * incrementY;            // конец по У
        if (!isCellValid(endYLine, endXLine)) return false;    // Выход линии за пределы
        for (int i = 0; i < len; i++) {                    // идем по линии
            if (field[y + i * incrementY][x + i * incrementX] != dot) return false;    // символы одинаковые?
        }
        return true;
    }

    private boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y, x)) return false;
            }
        }
        return true;
    }

    private boolean isCellValid(int y, int x) {
        return x >= 0 && y >= 0 && x < fieldSizeX && y < fieldSizeY;
    }

    private boolean isCellEmpty(int y, int x) {
        return field[y][x] == DOT_EMPTY;
    }

}
