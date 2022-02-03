package View;
import Controller.MenuPanelController;
import Model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Klasa wchodzaca w sklad widoku gry. Reprezentuje menu gry.
 *
 */

public class MenuPanel implements PropertyChangeListener {
    private JPanel panel;
    private JLabel label;
    private JButton button;
    private int mines;
    private static Vector<Image> face;

    /**
     * Konstruktor, tworzy nowe menu.
     * @param mines
     *              Liczba min na poczatku rozgrywki.
     */
    public MenuPanel(int mines) {
        this.mines = mines;
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label = new JLabel(String.valueOf(Model.getInstance().getRemainingMines()), JLabel.CENTER);
        label.setMinimumSize(new Dimension(50, 50));
        label.setPreferredSize(new Dimension(50, 50));
        label.setMaximumSize(new Dimension(50, 50));
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 30));

        if(face == null)
            createVectorOfFaces();
        button = new JButton();
        button.setPreferredSize(new Dimension(50, 50));
        button.setVisible(true);
        button.setIcon( new ImageIcon(face.elementAt(0)));

        panel.add(Box.createHorizontalStrut(20));
        panel.add(label);
        panel.add(Box.createHorizontalStrut(125));
        panel.add(button);
    }

    /**
     * Metoda tworzaca obiekt Image zawierajacy obraz o podanej nazwie.
     *
     */
    private Image createImage(String fileName) {
        try {
            Image img = ImageIO.read(getClass().getResource(fileName));
            Image newimg = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH );
            return newimg;
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    /**
     * Metoda tworzaca wektor obiektow Image - przyspiesza aktualizacje planszy.
     *
     */
    private void createVectorOfFaces() {
        face = new Vector<>(3);

        face.add(createImage("../images/sun-smile.png"));               // 0 - rozgrywka w toku
        face.add(createImage("../images/sun-sad.png"));                 // 1 - porazka
        face.add(createImage("../images/sun-glasses.png"));             // 2 - wygrana
    }

    @Override
    public void propertyChange( PropertyChangeEvent evt) {
        switch( evt.getPropertyName())
        {
            case "NEW_GAME":
                setNewGame();
                break;

            case "CHECK":
                setGameCheck( Model.getInstance() );
                break;
        }
    }

    /**
     * Ustawia twarz na "rozgrywka w toku"
     *
     */
    public void setNewGame() {
        label.setText(String.valueOf(Model.getInstance().getRemainingMines()));
        button.setIcon( new ImageIcon(face.elementAt(0)));
    }

    /**
     * Ustawia twarz na "porazka" lub "wygrana" - w zależności od wyniku rozgrywki.
     * @param model
     *              Model.
     */
    private void setGameCheck(Model model) {
        int result = model.checkSolution();
        if (result == 1)
            button.setIcon( new ImageIcon(face.elementAt(2)));
        else if (result == -1)
            button.setIcon( new ImageIcon(face.elementAt(1)));

        label.setText(String.valueOf(Model.getInstance().getRemainingMines()));
    }

    /**
     * Funkcja ustawiajaca kontroler.
     * @param menuPanelController
     */
    public void setController(MenuPanelController menuPanelController) {
        button.addMouseListener(menuPanelController);
    }

    /**
     *
     * @return panel menu
     */
    public JPanel getPanel() { return panel; }
}
