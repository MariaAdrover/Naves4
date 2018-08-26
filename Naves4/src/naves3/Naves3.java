package naves3;

import java.awt.Container;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author miaad
 */
public class Naves3 extends JFrame {
    private Lienzo lienzo;
    
    public Naves3() {
        this.setTitle("Naves3");
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
        Naves3 game = new Naves3();
        Thread tLienzo = new Thread(game.getLienzo());
        tLienzo.start();
        
        game.setVisible(true);
    }
    
}
