package naves3;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author miaad
 */
public class Nave {

    private Image img;
    private int x;
    private int y;
    private int ancho;
    private int alto;

    public Nave() {
        this.x = 470;
        this.y = 700;
        this.ancho = 73;
        this.alto = 120;
        try {
            this.img = ImageIO.read(new File("img/nave2.png"));
        } catch (IOException e) {
            System.out.println("Imatge no trobada");
        }
    }

    public int getAncho() {
        return this.ancho;
    }

    public int getAlto() {
        return this.alto;
    }

    public Image getImg() {
        return this.img;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int n) {
        this.x = this.x + n;
    }

    public void setY(int n) {
        this.y = this.y + n;
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("3 - keyReleased");
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37 && (this.x - 10 >= 0)) {
            this.setX(-10);
        } else if (e.getKeyCode() == 39 && (this.x + 10 <= 1200)) {
            this.setX(10);
        } else if (e.getKeyCode() == 38 && (this.y - 10 >= 0)) {
            this.setY(-10);
        } else if (e.getKeyCode() == 40 && (this.y + 10 <= 810)) {
            this.setY(10);
        }
    }

}
