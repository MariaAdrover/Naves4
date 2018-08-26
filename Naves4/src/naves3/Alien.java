package naves3;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author miaad
 */
public class Alien {

    private boolean change;
    private boolean cambiarImg;
    private int contador;
    private final int x;
    private int y;
    private final int ancho;
    private final int alto;
    private Image img;
    private Image img1;
    private Image img2;

    public Alien() {
        this.change = false;
        this.cambiarImg = true;
        this.contador = 0;
        this.x = this.setX();
        this.y = 0;
        this.alto = 160;
        this.ancho = 103;
        this.loadImg();
        this.setImg();
    }
    
    public boolean getChange() {
        return this.change;
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

    public void cambiarImg() {
        this.setImg();
    }

    public void mover() {
        if (!change) {
            if ((this.y + 1) <= 780) {
                this.y = this.y + 1;
            } else {
                this.y = this.y - 1;
                this.change = true;
            }
        } else {
            if ((this.y - 1) >= 0) {
                this.y = this.y - 1;
            } else {
                this.y = this.y + 1;
                this.change = false;
            }
        }
    }

    private void loadImg() {
        try {
            this.img1 = ImageIO.read(new File("img/nave3.png"));
            this.img2 = ImageIO.read(new File("img/nave4.png"));
        } catch (IOException e) {
            System.out.println("Imatge no trobada");
        }
    }

    private void setImg() {
        if (cambiarImg) {
            this.img = this.img1;
            this.contador++;

            if (this.contador > 50) {
                this.cambiarImg = false;
            }
            
        } else {
            this.img = this.img2;
            this.contador--;

            if (this.contador < 0) {
                this.cambiarImg = true;
            }
            
        }
        //System.out.println(contador);
    }

    private int setX() {
        double x = Math.random() * 1197;

        return (int) x;
    }
}
