package ru.geekbrains.java_core_1.lesson_4;

import java.sql.Array;
import java.util.*;

public class TicTacToe {

    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static final char DOT_EMPTY = '.';
    public static int DOTS_TO_WIN;
    public static final Scanner scanner = new Scanner(System.in);
    public static final Random random = new Random();

    public static char[][] field;
    public static int sizeX;
    public static int sizeY;
    public static int rounds = 1;
    public static int humanWins = 0;
    public static int aiWins = 0;

    public static void main(String[] args) {
        startNewGame();
    }


    public static void startNewGame() {
        while (true) {
            System.out.println("Round #" + rounds++);
            initField();
            printField();
            do {
                int[] lastEnemyTurn = humanTurn();
                printField();
                aiTurnDefenseImproved();
                //aiTurnManual();
                //aiTurnDefense(lastEnemyTurn, DOT_X);
                //aiTurn();
                printField();
            } while (!checkDraw() && !checkWin(DOT_X) && !checkWin(DOT_O));
            if (checkWin(DOT_X)) {
                System.out.println("HUMAN WIN!");
                humanWins++;
            } else if (checkWin(DOT_O)) {
                System.out.println("AI WIN!");
                aiWins++;
            }
            System.out.printf("Game count:\tAI\tHuman\n");
            System.out.printf("           \t%d\t%d\n", aiWins, humanWins);
            System.out.print("Want to continue? (Y or N): ");
            if (!scanner.next().toLowerCase(Locale.ROOT).equals("y")) {
                break;
            }
            System.out.println();
        }
    }

    public static void initField() {
        System.out.println("Enter sizes X and Y of game field: ");
        sizeX = scanner.nextInt();
        sizeY = scanner.nextInt();
        field = new char[sizeY][sizeX];
        System.out.println("Enter how much dots in a row must be to win: ");
        DOTS_TO_WIN = scanner.nextInt();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = DOT_EMPTY;
            }
        }
    }

    public static void printField() {
        System.out.print(' ');
        for (int i = 1; i < field[0].length * 2 + 2; i++) {
            if (i % 2 == 0) {
                System.out.print(i / 2);
            } else {
                System.out.print('\t');
            }
        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1);
            for (int j = 1; j < field[i].length * 2 + 2; j++) {
                if (j % 2 == 0) {
                    System.out.print(field[i][j / 2 - 1]);
                } else {
                    System.out.print('\t');
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean validCell(int y, int x) {
        return x >= 0 && y >= 0 && x < sizeX && y < sizeY && field[y][x] == DOT_EMPTY;
    }


    public static int[] humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Enter coordinates X and Y of your DOT: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!validCell(y, x));
        field[y][x] = DOT_X;
        int[] arr = {y, x};
        return arr;
    }

    public static void aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(sizeX);
            y = random.nextInt(sizeY);
        } while (!validCell(y, x));
        field[y][x] = DOT_O;
    }

    public static int[] aiTurnManual() {
        int x;
        int y;
        do {
            System.out.println("Enter coordinates X and Y of ENEMY DOT: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!validCell(y, x));
        field[y][x] = DOT_O;
        int[] arr = {y, x};
        return arr;
    }

    public static boolean aiTurnDefense(int[] lastTurn, char dot) {

        int x1 = lastTurn[0];//indexes for 1 diagonal
        int y1 = lastTurn[1];
        int x2 = lastTurn[0];//indexes for 2 diagonal
        int y2 = lastTurn[1];

        while (x1 > 0 && y1 > 0) {
            x1--; //set to start position
            y1--;
        }

        while (x2 < field.length - 1 && y2 > 0) {
            x2++; //set to start position
            y2--;
        }
//        System.out.println(x1 + ":" + y1);
//        System.out.println(x2 + ":" + y2);

        char[] candidates = {'A', 'B', 'C', 'D'};
        int numCandidates = candidates.length;

        int[] sumOfDots = new int[candidates.length];
        int[] sumOfDotsInRows = new int[candidates.length];
        int[] sumOfEmptyDots = new int[candidates.length];

        //анализируем 4 направления используя предыдущий ход противника как отправную точку
        analyzeDirection('A', x1, y1, dot, sumOfDots, sumOfDotsInRows, sumOfEmptyDots);
        analyzeDirection('B', x2, y2, dot, sumOfDots, sumOfDotsInRows, sumOfEmptyDots);
        analyzeDirection('C', lastTurn[0], 0, dot, sumOfDots, sumOfDotsInRows, sumOfEmptyDots);
        analyzeDirection('D', 0, lastTurn[1], dot, sumOfDots, sumOfDotsInRows, sumOfEmptyDots);

        int maxSumOfDots = 0;
        int maxDotsInRow = 0;

        for (int i = 0; i < candidates.length; i++) {
            maxSumOfDots = Math.max(maxSumOfDots, sumOfDots[i]);
            maxDotsInRow = Math.max(maxDotsInRow, sumOfDotsInRows[i]);
        }

        int tempSumOfDots = 0;
        int tempSumDotsInRow = 0;
        int tempSumOfEmptyDots = 0;

        //анализируем собранную информацию чтобы решить что делать дальше

        for (int i = 0; i < candidates.length; i++) {
            if (sumOfEmptyDots[i] == 0) {
                candidates[i] = '\u0000';
                numCandidates--;
            }
        }
        if (numCandidates == 0) {
            return false;
        }

        if (maxDotsInRow == maxSumOfDots) {
            for (int i = 0; i < candidates.length; i++) {
                if (sumOfDotsInRows[i] != maxDotsInRow && candidates[i] != '\u0000') {
                    candidates[i] = '\u0000';
                    numCandidates--;
                } else {
                    tempSumOfDots = Math.max(tempSumOfDots, sumOfDots[i]);
                }
            }
            for (int i = 0; i < candidates.length; i++) {
                if (candidates[i] != '\u0000' && numCandidates > 1) {
                    if (sumOfDots[i] != tempSumOfDots) {
                        candidates[i] = '\u0000';
                        numCandidates--;
                    } else {
                        tempSumOfEmptyDots = Math.max(tempSumOfEmptyDots, sumOfEmptyDots[i]);
                    }
                }
            }
            for (int i = 0; i < candidates.length; i++) {
                if (candidates[i] != '\u0000' && numCandidates > 1) {
                    if (sumOfEmptyDots[i] != tempSumOfEmptyDots) {
                        candidates[i] = '\u0000';
                        numCandidates--;
                    }
                }
            }
        } else if (maxDotsInRow < maxSumOfDots) {
            for (int i = 0; i < candidates.length; i++) {
                if (sumOfDots[i] != maxSumOfDots && candidates[i] != '\u0000') {
                    candidates[i] = '\u0000';
                    numCandidates--;
                } else {
                    tempSumDotsInRow = Math.max(tempSumDotsInRow, sumOfDotsInRows[i]);
                }
            }
            for (int i = 0; i < candidates.length; i++) {
                if (candidates[i] != '\u0000' && numCandidates > 1) {
                    if (sumOfDotsInRows[i] != tempSumDotsInRow) {
                        candidates[i] = '\u0000';
                        numCandidates--;
                    } else {
                        tempSumOfEmptyDots = Math.max(tempSumOfEmptyDots, sumOfEmptyDots[i]);
                    }
                }
            }
            for (int i = 0; i < candidates.length; i++) {
                if (candidates[i] != '\u0000' && numCandidates > 1) {
                    if (sumOfEmptyDots[i] != tempSumOfEmptyDots) {
                        candidates[i] = '\u0000';
                        numCandidates--;
                    }
                }
            }
        }

        System.out.println(Arrays.toString(candidates));
        // если после всех мытарств кандидатов больше одного выбираем случайно
        char result;
        if (numCandidates > 0 ) {
            do {
                result = candidates[random.nextInt(candidates.length)];
            } while (result == '\u0000');
        } else {
            //System.out.println("numCandidates:" + numCandidates);
            return false;
        }

        System.out.println("Result: " + result);
        System.out.println(Arrays.toString(sumOfDotsInRows));
        System.out.println(Arrays.toString(sumOfDots));
        System.out.println(Arrays.toString(sumOfEmptyDots));

        // в зависимости от выбранного направления производим действия на поле, направленные на блок противника
        boolean success = false;
        if (result == 'A') {
            success = makeMove(result, x1, y1, dot, sumOfDots, sumOfDotsInRows);
        } else if (result == 'B') {
            success = makeMove(result, x2, y2, dot, sumOfDots, sumOfDotsInRows);
        } else if (result == 'C') {
            success = makeMove(result, lastTurn[0], 0, dot, sumOfDots, sumOfDotsInRows);
        } else if (result == 'D') {
            success = makeMove(result, 0, lastTurn[1], dot, sumOfDots, sumOfDotsInRows);
        }
        return success;
    }

    public static void analyzeDirection (char direction, int startX, int startY, char dot, int[] sumOfDots, int[] maxDotsInRows, int[] sumOfEmptyDots){
        int tempDotsInRows = 0;
        int k = 0;
        int indexX = 0;
        int indexY = 0;
        for (int i = 0; i < Math.min(sizeX, sizeY); i++) {
            if (direction == 'A') {
                k = 0;
                indexX = startX + i;
                indexY = startY + i;
            } else if (direction == 'B') {
                k = 1;
                indexX = startX - i;
                indexY = startY + i;
            } else if (direction == 'C') {
                k = 2;
                indexX = startX;
                indexY = startY + i;
            } else if (direction == 'D') {
                k = 3;
                indexX = startX + i;
                indexY = startY;
            }
            //System.out.print(indexX + ":" + indexY);
            if (indexX >= 0 && indexX < field.length && indexY >= 0 && indexY < field[0].length) {
                if (field[indexX][indexY] == dot){
                    sumOfDots[k]++;
                    tempDotsInRows++;
                    maxDotsInRows[k] = Math.max(maxDotsInRows[k], tempDotsInRows);
                } else {
                    tempDotsInRows = 0;
                    if (field[indexX][indexY] == DOT_EMPTY) {
                        sumOfEmptyDots[k]++;
                    } else {
                        sumOfDots[k] = 0; //questionable action
                    }
                }
            }
        }
    }

    public static boolean makeMove (char direction, int startX, int startY, char dot, int[] sumOfDots, int[] sumOfDotsInRows) {
        int count = 0;
        int tempSumOfDots = 0;
        int k = 0;
        int indexX = 0;
        int indexY = 0;
        int upX = 0;
        int upY = 0;
        int downX = 0;
        int downY = 0;
        for (int i = 0; i < Math.min(sizeX, sizeY); i++) {
            if (direction == 'A') {
                k = 0;
                indexX = startX + i;
                indexY = startY + i;
            } else if (direction == 'B') {
                k = 1;
                indexX = startX - i;
                indexY = startY + i;
            } else if (direction == 'C') {
                k = 2;
                indexX = startX;
                indexY = i;
            } else if (direction == 'D') {
                k = 3;
                indexX = i;
                indexY = startY;
            }
            if (indexX >= 0 && indexX < field.length && indexY >= 0 && indexY < field[0].length) {
                if (field[indexX][indexY] == dot){
                    count++;
                    tempSumOfDots++;
                    if (count == sumOfDotsInRows[k]) {
                        for (int j = 1; j < Math.min(sizeX, sizeY); j++) {
                            if (direction == 'A') {
                                upX = indexX + j;
                                upY = indexY + j;
                                downX = indexX - count - j + 1;
                                downY = indexY - count - j + 1;
                            } else if (direction == 'B') {
                                upX = indexX - j;
                                upY = indexY + j;
                                downX = indexX + count + j - 1;
                                downY = indexY - count - j + 1;
                            } else if (direction == 'C') {
                                upX = indexX;
                                upY = indexY + j;
                                downX = indexX;
                                downY = indexY - count - j + 1;
                            } else if (direction == 'D') {
                                upX = indexX + j;
                                upY = indexY;
                                downX = indexX - count - j + 1;
                                downY = indexY;
                            }
                            if (tempSumOfDots < sumOfDots[k]) {
                                if (tryToPutDot(upX, upY)){
                                    return true;
                                }
                                if (tryToPutDot(downX, downY)){
                                    return true;
                                }
                            } else {
                                if (tryToPutDot(downX, downY)){
                                    return true;
                                }
                                if (tryToPutDot(upX, upY)){
                                    return true;
                                }
                            }
                        }
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    public static boolean validIndex(int x, int y){
        return x >= 0 && y >= 0 && x < field.length && y < field[1].length;
    }

    public static void goThroughLine(int direction, int start_X, int start_Y, int limit, Map<int[], int[]> all_data){
        int[] keyArr = {direction, start_X, start_Y};
        int[] valueArr = {0, 0, 0, 0, 0, 0, 0};
        int sumOfDots = 0;
        int sumsOfEffectiveDots = 0;
        int sumsOfDotsInRow = 0;
        int sumsOfEmptyDots = 0;
        int sumsOfEffectiveEmptyDots = 0;
        int index_X = 0;
        int index_Y = 0;
        int tempDotsInRow = 0;
        int[][] lineCoordinates = new int[limit][2];
        for (int j = 0; j < limit; j++) {
            if (direction == 1) {
                index_X = start_X + j;
                index_Y = start_Y + j;
            } else if (direction == 2) {
                index_X = start_X - j;
                index_Y = start_Y + j;
            } else if (direction == 3) {
                index_X = start_X;
                index_Y = start_Y + j;
            } else if (direction == 4) {
                index_X = start_X + j;
                index_Y = start_Y;
            }
            if (validIndex(index_X, index_Y)){
                lineCoordinates[j][0] = index_X;
                lineCoordinates[j][1] = index_Y;
                if (field[index_X][index_Y] == DOT_X){ //enemy
                    //sumOfDots++;
                    sumsOfEffectiveDots++;
                    //sumsOfDotsInRow++;
                    //tempDotsInRow = Math.max(tempDotsInRow, sumsOfDotsInRow);
                } else if (field[index_X][index_Y] == DOT_EMPTY){ //vacant
                    sumsOfEffectiveDots++;
                    //sumsOfDotsInRow = 0;
                    sumsOfEmptyDots++;
                } else { //bot
                    //sumsOfDotsInRow = 0;
                    //sumsOfEmptyDots = 0;
                    sumsOfEffectiveDots = 0;
                    //sumOfDots = 0;
                }
                if (valueArr[1] < sumsOfEffectiveDots) {
                    //valueArr[0] = sumOfDots;
                    valueArr[1] = sumsOfEffectiveDots;
                    //valueArr[2] = tempDotsInRow;
                    //valueArr[3] = sumsOfEffectiveEmptyDots;
                    valueArr[4] = sumsOfEmptyDots;
                }
            }
        }
        int tempSumOfEffectiveDots = 0;
        for (int i = 0; i < limit; i++) {
            if (field[lineCoordinates[i][0]][lineCoordinates[i][1]] != DOT_O){
                tempSumOfEffectiveDots++;
                if (tempSumOfEffectiveDots == valueArr[1]) {
                    for (int j = 0; j < valueArr[1]; j++) {
                        if (field[lineCoordinates[i - j][0]][lineCoordinates[i - j][1]] == DOT_X){
                            tempDotsInRow++;
                            sumOfDots++;
                        } else {
                            if (field[lineCoordinates[i - j][0]][lineCoordinates[i - j][1]] == DOT_EMPTY){
                                sumsOfEffectiveEmptyDots++;
                            }
                            sumsOfDotsInRow = Math.max(sumsOfDotsInRow, tempDotsInRow);
                            tempDotsInRow = 0;
                        }
                    }
                    valueArr[0] = sumOfDots;
                    valueArr[2] = sumsOfDotsInRow;
                    valueArr[3] = sumsOfEffectiveEmptyDots;
                    valueArr[5] = lineCoordinates[i - valueArr[1] + 1][0]; //coordinates of the beginning of effective sequence
                    valueArr[6] = lineCoordinates[i - valueArr[1] + 1][1]; //coordinates of the beginning of effective sequence
                }
            } else {
                tempSumOfEffectiveDots = 0;
            }
        }
        all_data.put(keyArr, valueArr);
    }


    public static boolean aiTurnDefenseImproved(){

        Map<int[], int[]> all_data = new HashMap<>();
        for (int i = field.length - 1 - DOTS_TO_WIN + 1; i >= 0; i--) {
            goThroughLine(1, i, 0, Math.min(sizeX, sizeY), all_data); // '\'
        }
        for (int i = 1; i <= field[0].length - 1 - DOTS_TO_WIN + 1; i++) {
            goThroughLine(1, 0, i, Math.min(sizeX, sizeY), all_data); // '\'
        }
        for (int i = DOTS_TO_WIN - 1; i < field.length; i++) {
            goThroughLine(2, i, 0, Math.min(sizeX, sizeY), all_data); // '/'
        }
        for (int i = 1; i < field[0].length - DOTS_TO_WIN + 1; i++) {
            goThroughLine(2, field.length - 1, i, Math.min(sizeX, sizeY), all_data); // '/'
        }
        for (int i = 0; i < field.length; i++) {
            goThroughLine(3, i, 0, field[0].length, all_data);// '_'
        }
        for (int i = 0; i < field[0].length; i++) {
            goThroughLine(4, 0, i, field.length, all_data); // '|'
        }

        //System.out.println(all_data.size());

        int maxSumOfDots = 0;
        int maxSumOfDotsInRow = 0;
        int maxSumOfEmptyDots = 0;

        HashMap<int[], int[]> all_data_copy = new HashMap<>(all_data);
        for (int[] key : all_data_copy.keySet()){
            if (all_data.get(key)[1] < DOTS_TO_WIN || //effective < dots to win
                all_data.get(key)[3] == 0) { // zero effective empty dots
                all_data.remove(key);
            } else {
                maxSumOfDots = Math.max(all_data.get(key)[0], maxSumOfDots);
            }
        }
        all_data_copy = new HashMap<>(all_data);//TODO: get rid of
        for (int[] key : all_data_copy.keySet()) {
            if (all_data.get(key)[0] < maxSumOfDots){
                all_data.remove(key);
            } else {
                maxSumOfDotsInRow = Math.max(all_data.get(key)[2], maxSumOfDotsInRow);
            }
        }
        if (all_data.size() > 1){
            all_data_copy = new HashMap<>(all_data);//TODO: get rid of
            for (int[] key : all_data_copy.keySet()) {
                if (all_data.get(key)[2] < maxSumOfDotsInRow){
                    all_data.remove(key);
                } else {
                    maxSumOfEmptyDots = Math.max(all_data.get(key)[3], maxSumOfEmptyDots);
                }
            }
            if (all_data.size() > 1){
                all_data_copy = new HashMap<>(all_data);//TODO: get rid of
                for (int[] key : all_data_copy.keySet()) {
                    if (all_data.get(key)[3] < maxSumOfEmptyDots){
                        all_data.remove(key);
                    }
                }
                if (all_data.size() > 1 ){
                    all_data_copy = new HashMap<>(all_data);//TODO: get rid of
                    for (int[] key : all_data_copy.keySet()) {
                        if (all_data.size() > 1){
                            all_data.remove(key);
                        }
                    }
                }
            }
        }

        for (int[] key : all_data.keySet()){
            System.out.println(Arrays.toString(key) + ":" + Arrays.toString(all_data.get(key)));
        }

        if (all_data.size() == 1) {
            //System.out.println("YES");
            for (int[] key : all_data.keySet()) {
                int direction = key[0];
                int index_X = 0;
                int index_Y = 0;
                int sumOfDots = all_data.get(key)[0];
                int sumOfEffectiveDots = all_data.get(key)[1];
                int start_eff_X = all_data.get(key)[5];
                int start_eff_Y = all_data.get(key)[6];
                int tempSumOfDots = 0;
                int tempSumOfEffectiveDots = 0;
                for (int i = 0; i < Math.max(sizeX, sizeY); i++) {
                    if (direction == 1) {
                        index_X = start_eff_X + i;
                        index_Y = start_eff_Y + i;
                    } else if (direction == 2) {
                        index_X = start_eff_X - i;
                        index_Y = start_eff_Y + i;
                    } else if (direction == 3) {
                        index_X = start_eff_X;
                        index_Y = start_eff_Y + i;
                    } else if (direction == 4) {
                        index_X = start_eff_X + i;
                        index_Y = start_eff_Y;
                    }
                    if (validIndex(index_X, index_Y)) {
                        if (field[index_X][index_Y] == DOT_EMPTY) {
                            tempSumOfEffectiveDots++;
                        } else if (field[index_X][index_Y] == DOT_X) {
                            tempSumOfDots++;
                            tempSumOfEffectiveDots++;
                        }
                        if (tempSumOfDots == Math.round(sumOfDots / 2)) {
                            int up_X = 0;
                            int up_Y = 0;
                            int down_X = 0;
                            int down_Y = 0;
                            for (int j = 1; j < Math.max(sizeX, sizeY); j++) {
                                if (direction == 1){
                                    up_X = index_X + j;
                                    up_Y = index_Y + j;
                                    down_X = index_X - j;
                                    down_Y = index_Y - j;
                                } else if (direction == 2) {
                                    up_X = index_X - j;
                                    up_Y = index_Y + j;
                                    down_X = index_X + j;
                                    down_Y = index_Y - j;
                                } else if (direction == 3) {
                                    up_X = index_X;
                                    up_Y = index_Y + j;
                                    down_X = index_X;
                                    down_Y = index_Y - j;
                                } else if (direction == 4) {
                                    up_X = index_X + j;
                                    up_Y = index_Y;
                                    down_X = index_X - j;
                                    down_Y = index_Y;
                                }
                                if (tempSumOfEffectiveDots <= Math.round(sumOfEffectiveDots / 2)){
                                    if (tryToPutDot(up_X, up_Y)){
                                        return true;
                                    }
                                } else {
                                    if (tryToPutDot(down_X, down_Y)) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean tryToPutDot(int x, int y) {
        if (x >= 0 && x < field.length && y >= 0 && y < field[0].length && field[x][y] == DOT_EMPTY) {
            field[x][y] = DOT_O;
            return true;
        }
        return false;
    }

    public static boolean checkWin(char dot) {
        int countA = 0;
        int countB = 0;

        for (int i = 0; i < field.length - DOTS_TO_WIN + 1; i++) {
            countA = 0;
            countB = 0;
            for (int k = 0; k < Math.min(sizeX, sizeY); k++) {
                if (k + i < field.length) {
                    if (field[k + i][k] == dot) { //first diagonal down '\'
                        countA++;
                        if (countA == DOTS_TO_WIN) {
                            return true;
                        }
                    } else {
                        countA = 0;
                    }
                    if (field[field.length - 1 - k - i][k] == dot) { //second diagonal up '/'
                        countB++;
                        if (countB == DOTS_TO_WIN) {
                            return true;
                        }
                    } else {
                        countB = 0;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = 0; i < field[0].length - DOTS_TO_WIN + 1; i++) {
            countA = 0;
            countB = 0;
            for (int k = 0; k < Math.min(sizeX, sizeY); k++) {
                if (k + i < field[0].length) {
                    if (field[k][k + i] == dot) { //first diagonal up '\'
                        countA++;
                        if (countA == DOTS_TO_WIN) {
                            return true;
                        }
                    } else {
                        countA = 0;
                    }
                    if (field[field.length - 1 - k][k + i] == dot) { //second diagonal down '/'
                        countB++;
                        if (countB == DOTS_TO_WIN) {
                            return true;
                        }
                    } else {
                        countB = 0;
                    }
                } else {
                    break;
                }
            }
        }

        for (int i = 0; i < field.length; i++) {
            countA = 0;
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == dot) {
                    countA++;
                    if (countA == DOTS_TO_WIN) {
                        return true;
                    }
                } else {
                    countA = 0;
                }
            }
        }

        for (int i = 0; i < field[0].length; i++) {
            countB = 0;
            for (int j = 0; j < field.length; j++) {
                if (field[j][i] == dot) {
                    countB++;
                    if (countB == DOTS_TO_WIN) {
                        return true;
                    }
                } else {
                    countB = 0;
                }
            }

        }
        return false;

    }

    public static boolean checkDraw() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        System.out.println("DRAW!");
        return true;
    }
}
