/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naves4;

import java.awt.Container;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author miaad
 */
public class Naves4 extends JFrame {
    private Lienzo lienzo;
    
    public Naves4() {
        this.setTitle("Naves4");
        this.setSize(1300, 1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container contenedor = this.getContentPane();
        Lienzo lienzo = new Lienzo();
        this.lienzo = lienzo;
        contenedor.add(lienzo);        
    }    
    
    public Lienzo getLienzo() {
        return this.lienzo;
    }
    
    public static void main(String[] args) {
        Naves4 game = new Naves4();
        Thread tLienzo = new Thread(game.getLienzo());
        tLienzo.start();
        
        game.setVisible(true);
    }
}
