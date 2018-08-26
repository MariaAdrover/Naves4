package naves3;

/**
 *
 * @author miaad
 */
public class Moneda {
    private int x;
    private int y;
    
    public Moneda() {
        this.x = this.getRandomX();
        this.y = this.getRandomY();
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

    private int getRandomX() {
        double x = Math.random() * 1250;

        return (int) x;
    }

    private int getRandomY() {
        double y = Math.random() * 950;

        return (int) y;
    }
}
