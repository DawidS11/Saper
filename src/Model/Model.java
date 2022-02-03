package Model;

import java.util.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Klasa reprezentuje gre Saper. Zawiera metodę generująca nowa
 * rozgrywke, rozwiazanie (po pierwszym wyborze), aktualizacje
 * stanu gry oraz sprawdzanie poprawnosci rozwiazania.
 *
 */

public class Model {
    private int a, b, mines, flags;
    private int[][] solution;           // -1 to mina
    private int[][] game;               // -1 to mina, -2 to flaga
    private boolean[][] checked;
    private boolean isFirstChoice;
    private boolean gameOver;
    private static Model instance;
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Konstruktor, tworzy nowa gre.
     */
    private Model(int a, int b, int mines) {
        this.a = a;
        this.b = b;
        this.mines = mines;
        newGame();
    }

    /**
     * Funkcja generujaca nowa gre.
     */
    public void newGame() {
        solution = new int[a][b];
        game = new int[a][b];
        checked = new boolean[a][b];
        flags = 0;
        isFirstChoice = true;
        gameOver = false;

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                solution[i][j] = -3;
                game[i][j] = -3;
                checked[i][j] = false;
            }
        }
        notify("NEW_GAME");
    }

    /**
     * Singleton
     *
     * @return instancja obiektu
     */
    public static Model getInstance() {
        if (instance == null)
            instance = new Model(10, 10, 15);
        return instance;
    }

    /**
     *
     * @param name
     * @param l
     */
    public void addObserver(String name, PropertyChangeListener l) {
        pcs.addPropertyChangeListener(name, l);
    }

    /**
     * Funkcja powiadamiajaca "sluchaczy".
     * @param val
     */
    public void notify(String val) {
        pcs.firePropertyChange(val, game, solution);
    }

    /**
     * Funkcja aktualizujaca gre.
     * @param posX
     * @param posY
     */
    public void updateGame(int posX, int posY) {
        if (isFirstChoice)
            firstChoice(posX, posY);
        else if (game[posX][posY] == -3) {
            game[posX][posY] = solution[posX][posY];
            checked[posX][posY] = true;
            if (game[posX][posY] == -1) {  // koniec gry
                if(!gameOver) {
                    solution[posX][posY] = -4;  // czerwona mina
                    gameOver = true;
                }
                notify("CHECK");
                return;
            } else {
                if (game[posX][posY] == 0)
                    checkZero(posX, posY);
                notify("CHECK");
                return;
            }
        }
        notify("CHECK");
    }

    /**
     * Funkcja sprawdzajaca czy na sasiednich polach sa "zera"
     * @param posX
     * @param posY
     */
    private void checkZero(int posX, int posY) {
        if (posX - 1 >= 0 && posY - 1 >= 0 && checked[posX - 1][posY - 1] == false) {
            game[posX - 1][posY - 1] = solution[posX - 1][posY - 1];
            checked[posX - 1][posY - 1] = true;
            if (solution[posX - 1][posY - 1] == 0)
                checkZero(posX - 1, posY - 1);
        }
        if (posY - 1 >= 0 && checked[posX][posY - 1] == false) {
            game[posX][posY - 1] = solution[posX][posY - 1];
            checked[posX][posY - 1] = true;
            if (solution[posX][posY - 1] == 0)
                checkZero(posX, posY - 1);
        }
        if (posX + 1 < a && posY - 1 >= 0 && checked[posX + 1][posY - 1] == false) {
            game[posX + 1][posY - 1] = solution[posX + 1][posY - 1];
            checked[posX + 1][posY - 1] = true;
            if (solution[posX + 1][posY - 1] == 0)
                checkZero(posX + 1, posY - 1);
        }
        if (posX + 1 < a && checked[posX + 1][posY] == false) {
            game[posX + 1][posY] = solution[posX + 1][posY];
            checked[posX + 1][posY] = true;
            if (solution[posX + 1][posY] == 0)
                checkZero(posX + 1, posY);
        }
        if (posX + 1 < a && posY + 1 < b && checked[posX + 1][posY + 1] == false) {
            game[posX + 1][posY + 1] = solution[posX + 1][posY + 1];
            checked[posX + 1][posY + 1] = true;
            if (solution[posX + 1][posY + 1] == 0)
                checkZero(posX + 1, posY + 1);
        }
        if (posY + 1 < b && checked[posX][posY + 1] == false) {
            game[posX][posY + 1] = solution[posX][posY + 1];
            checked[posX][posY + 1] = true;
            if (solution[posX][posY + 1] == 0)
                checkZero(posX, posY + 1);
        }
        if (posX - 1 >= 0 && posY + 1 < b && checked[posX - 1][posY + 1] == false) {
            game[posX - 1][posY + 1] = solution[posX - 1][posY + 1];
            checked[posX - 1][posY + 1] = true;
            if (solution[posX - 1][posY + 1] == 0)
                checkZero(posX - 1, posY + 1);
        }
        if (posX - 1 >= 0 && checked[posX - 1][posY] == false) {
            game[posX - 1][posY] = solution[posX - 1][posY];
            checked[posX - 1][posY] = true;
            if (solution[posX - 1][posY] == 0)
                checkZero(posX - 1, posY);
        }
        notify("CHECK");
    }

    /**
     * Funkcja aktualizujaca flagi.
     * @param posX
     * @param posY
     */
    public void updateFlag(int posX, int posY) {
        if (game[posX][posY] == -3) {
            game[posX][posY] = -2;
            checked[posX][posY] = true;
            flags++;
        } else if (game[posX][posY] == -2) {
            game[posX][posY] = -3;
            checked[posX][posY] = false;
            flags--;
        }

        notify("CHECK");
    }

    /**
     * Funkcja sprawdzajaca poprawnosc rozwiazania.
     * @return
     *          -1 gdy porazka, 0 gdy rozgrywka trwa, 1 gdy wygrana
     */
    public int checkSolution() {
        for (int i = 0; i < a; i++)
            for (int j = 0; j < b; j++)
                if (game[i][j] == -1 || game[i][j] == -4) {
                    flags = 0;
                    return -1;
                }

        int count = 0;
        for (int i = 0; i < a; i++)
            for (int j = 0; j < b; j++)
                if (checked[i][j])
                    count++;
                else
                if(solution[i][j] == -1)
                    count++;
        if (count == a * b) {
            flags = mines;
            gameOver = true;
            return 1;
        }
        return 0;
    }

    /**
     * Metoda generujaca nowe rozwiazanie gry.
     *
     * @param posX
     *            Wspolrzedna x pierwszego wybranego pola.
     * @param posY
     *            Wspolrzedna y pierwszego wybranego pola.
     *
     */
    private void firstChoice(int posX, int posY) {
        notify("NEW_GAME");

        int count = 0, x, y;
        Random random = new Random();

        while (count < this.mines) {
            x = random.nextInt(this.a);
            y = random.nextInt(this.b);
            if (x == posX && y == posY)
                continue;
            if (solution[x][y] != -1) {
                solution[x][y] = -1;
                count++;
            }
        }

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                count = 0;
                if (solution[i][j] == -1)
                    continue;
                if (i > 0 && j > 0 && solution[i - 1][j - 1] == -1)      // Sprawdzic sytuacje gdy poza plansza
                    count++;
                if (i > 0 && solution[i - 1][j] == -1)
                    count++;
                if (i > 0 && j < b - 1 && solution[i - 1][j + 1] == -1)
                    count++;
                if (j > 0 && solution[i][j - 1] == -1)
                    count++;
                if (j < b - 1 && solution[i][j + 1] == -1)
                    count++;
                if (i < a - 1 && j > 0 && solution[i + 1][j - 1] == -1)
                    count++;
                if (i < a - 1 && solution[i + 1][j] == -1)
                    count++;
                if (i < a - 1 && j < b - 1 && solution[i + 1][j + 1] == -1)
                    count++;
                solution[i][j] = count;
            }
        }
        game[posX][posY] = solution[posX][posY];
        if (game[posX][posY] == 0)
            checkZero(posX, posY);
        checked[posX][posY] = true;
        this.isFirstChoice = false;
        notify("NEW_GAME");
    }

    /**
     *
     * @return wspolrzedna x
     */
    public int getX() { return a; }

    /**
     *
     * @return wspolrzedna y
     */
    public int getY() { return b; }

    /**
     *
     * @return miny - flagi
     */
    public int getRemainingMines() { return mines - flags; }

    /**
     *
     * @return rozwiazanie
     */
    public int[][] getSolution() { return solution; }

    /**
     *
     * @return stan rozgrywki
     */
    public int[][] getGame() { return game; }
}