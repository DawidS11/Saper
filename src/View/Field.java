package View;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.Vector;

/**
 * Klasa reprezentujaca pole gry.
 *
 */

public class Field extends JButton {
    private int x, y;
    private static Vector<Image> image;

    /**
     *
     * @param x
     *          współrzedna x
     * @param y
     *          współrzędna y
     */
    public Field(int x, int y) {
        super();
        if(image == null)
            createVectorOfImages();
        this.setPreferredSize(new Dimension(50, 50));
        this.setVisible(true);
        this.setIcon( new ImageIcon(image.elementAt(12)));
        this.x = x;
        this.y = y;
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
    private void createVectorOfImages() {
        image = new Vector<Image>(13);
        for(int i = 0; i < 13; i++) { // 9 - mina, 10 - flaga, 11 - czerwona mina, 12 - nieodkryte
            switch(i) {
                case 0:
                    image.add(createImage("../images/0.png"));
                    break;
                case 1:
                    image.add(createImage("../images/1.png"));
                    break;
                case 2:
                    image.add(createImage("../images/2.png"));
                    break;
                case 3:
                    image.add(createImage("../images/3.png"));
                    break;
                case 4:
                    image.add(createImage("../images/4.png"));
                    break;
                case 5:
                    image.add(createImage("../images/5.png"));
                    break;
                case 6:
                    image.add(createImage("../images/6.png"));
                    break;
                case 7:
                    image.add(createImage("../images/7.png"));
                    break;
                case 8:
                    image.add(createImage("../images/8.png"));
                    break;
                case 9:
                    image.add(createImage("../images/bomb.png"));
                    break;
                case 10:
                    image.add(createImage("../images/flagged.png"));
                    break;
                case 11:
                    image.add(createImage("../images/boom.png"));
                    break;
                case 12:
                    image.add(createImage("../images/facingDown.png"));
                    break;
            }
        }
    }

    /**
     * Funkcja ustawiajaca flage.
     */
    public void setFlag() { this.setIcon( new ImageIcon(image.elementAt(10))); }

    /**
     * Funkcja ustawiajaca mine.
     */
    public void setMine() { this.setIcon( new ImageIcon(image.elementAt(9))); }

    /**
     * Funkcja ustawiajaca czerwona mine.
     */
    public void setRedMine() { this.setIcon( new ImageIcon(image.elementAt(11))); }

    /**
     * Funkcja ustawiajaca numer.
     * @param number
     *              Numer, ktory ma byc widoczny na polu.
     */
    public void setNumber(int number) {
        switch(number) {
            case 0:
                this.setIcon( new ImageIcon(image.elementAt(0)));
                break;
            case 1:
                this.setIcon( new ImageIcon(image.elementAt(1)));
                break;
            case 2:
                this.setIcon( new ImageIcon(image.elementAt(2)));
                break;
            case 3:
                this.setIcon( new ImageIcon(image.elementAt(3)));
                break;
            case 4:
                this.setIcon( new ImageIcon(image.elementAt(4)));
                break;
            case 5:
                this.setIcon( new ImageIcon(image.elementAt(5)));
                break;
            case 6:
                this.setIcon( new ImageIcon(image.elementAt(6)));
                break;
            case 7:
                this.setIcon( new ImageIcon(image.elementAt(7)));
                break;
            case 8:
                this.setIcon( new ImageIcon(image.elementAt(8)));
                break;
            default:
                this.setIcon( new ImageIcon(image.elementAt(12)));
                break;
        }
    }

    /**
     *
     * @return wspolrzedna x
     */
    public int posX() { return x; }

    /**
     *
     * @return wspolrzedna y
     */
    public int posY() { return y; }
}
