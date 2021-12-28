/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naves4;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author miaad
 */
public class Moneda extends naves3.Moneda{
    Image imgMoneda;
    
    public Moneda() {
        super();
        this.loadImg();
    }
    
    private void loadImg() {
        try {
            this.imgMoneda = ImageIO.read(new File("img/star2.png"));
        } catch (IOException e) {
            System.out.println("Imatge no trobada");
        }
    }
    
    public Image getImg() {
        return this.imgMoneda;
    }
}
