/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package naves4;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import naves3.Alien;
import naves3.Nave;

/**
 *
 * @author miaad
 */
public class Lienzo extends Canvas implements Runnable, KeyListener {

    private ArrayList<Alien> aliens;
    private ArrayList<Moneda> monedas;
    private Nave nave;
    private boolean colision;
    private int random;
    private int puntos;
    private Image buffImg = null;
    private int buffAncho;
    private int buffAlto;
    private Graphics buffGraphics;
    private int[][] tablero;
    private boolean[] keys;

    public Lienzo() {
        this.setBackground(Color.black);
        this.aliens = new ArrayList();
        this.aliens.add(new Alien());
        this.monedas = new ArrayList();
        this.monedas.add(new Moneda());
        this.nave = new Nave();
        this.colision = false;
        this.random = 390;
        this.puntos = 0;
        this.buffAncho = this.getWidth();
        this.buffAlto = this.getHeight();
        this.tablero = new int[1300][1000];
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus(); // JPanel now receives key events
        this.keys = new boolean[4];
    }

    @Override
    public void paint(Graphics g) {
        if (this.buffImg == null) {
            this.buffAncho = this.getWidth();
            this.buffImg = createImage(1300, 1000);
        }

        // get graphics
        this.buffGraphics = this.buffImg.getGraphics();

        // Borrar
        this.buffGraphics.clearRect(0, 0, 1300, 1000);
        this.buffGraphics.setColor(Color.white);

        // PINTAR
        // Pintar monedas
        for (int n = 0; n < this.monedas.size(); n++) {
            Moneda euro = this.monedas.get(n);
            this.buffGraphics.drawImage(euro.getImg(), euro.getX(), euro.getY(), 50, 50, this);
        }

        // Pintar aliens
        for (int n = 0; n < this.aliens.size(); n++) {
            Alien bicho = this.aliens.get(n);
            this.buffGraphics.drawImage(bicho.getImg(), bicho.getX(), bicho.getY(), 103, 160, this);
        }

        // Pintar nave
        this.buffGraphics.drawImage(this.nave.getImg(), this.nave.getX(), this.nave.getY(), 73, 120, this);

        if (colision) {
            Font f = new Font("Dialog", Font.BOLD, 100);
            this.buffGraphics.setFont(f);
            this.buffGraphics.setColor(Color.white);
            this.buffGraphics.drawString("HAS PERDIDO", 300, 300);
            this.buffGraphics.drawString("puntos: " + this.puntos, 420, 420);
        }

        g.drawImage(buffImg, 0, 0, this);
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleepTime;
        beforeTime = System.currentTimeMillis();
        while (!this.colision) {
            this.generarNuevosElementos();
            this.gameUpdate(); // game state is updated 
            this.comprobrarNave();
            // render to a buffer //gameRender(); 
            repaint(); // paint with the buffer
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleepTime = 10 - timeDiff; // time left in this loop
            if (sleepTime <= 0) // update/render took longer than period 
            {
                sleepTime = 5; // sleep a bit anyway
            }
            try {
                Thread.sleep(sleepTime); // in ms 
            } catch (InterruptedException ex) {
            }
            beforeTime = System.currentTimeMillis();
        }
        /*long t1 = System.currentTimeMillis( ); 
        long t2 = System.currentTimeMillis( ); 
        long diff = t2 - t1; // in ms*/

 /*Perf perf = Perf.getPerf( ); 
        long countFreq = perf.highResFrequency( );
        long count1 = perf.highResCounter( ); 
        long count2 = perf.highResCounter( ); 
        long diff = (count2 - count1) * 1000000000L / countFreq;*/
        long count1 = System.nanoTime();
        long count2 = System.nanoTime();
        long diff = (count2 - count1); // in nanoseconds

        System.out.println(diff);

        System.out.println("HAS PERDIDO");
        repaint();
        //System.exit(0);
    }

    private void actualizarTablero() {
        // Reiniciar valores a 0
        // -1 alien
        // 0 vacío
        // > 0 valor de cada moneda segun su posición en el array
        for (int i = 0; i < 1300; i++) {
            for (int j = 0; j < 1000; j++) {
                this.tablero[i][j] = 0;
            }
        }

        this.colocarMonedas();
        this.colocarAlien();
    }

    private void colocarMonedas() {
        if (this.monedas.size() > 0) {
            for (int n = 0; n < this.monedas.size(); n++) {
                Moneda euro = this.monedas.get(n);
                int monedaX = euro.getX();
                int monedaY = euro.getY();

                for (int x = monedaX; x < (monedaX + 50); x++) { // revisar, valores fijos?
                    for (int y = monedaY; y < (monedaY + 50); y++) {
                        this.tablero[x][y] = n + 1;
                    }
                }
            }
        }
    }

    private void colocarAlien() {
        for (int n = 0; n < this.aliens.size(); n++) {
            Alien bicho = this.aliens.get(n);
            int alienX = bicho.getX();
            int alienY = bicho.getY();

            for (int x = alienX + 3; x <= (alienX + bicho.getAncho()) - 3; x++) { // revisar, valores fijos?
                for (int y = alienY + 3; y <= (alienY + bicho.getAlto()) - 3; y++) {
                    this.tablero[x][y] = -1;
                }
            }
        }
    }

    private int comprobarColision() {
        int naveX = this.nave.getX();
        int naveY = this.nave.getY();

        for (int x = naveX; x < naveX + this.nave.getAncho(); x++) {
            for (int y = naveY; y < naveY + this.nave.getAlto(); y++) {
                int valorCasilla = this.tablero[x][y];
                if (valorCasilla == -1) {
                    return -1;
                } else if (valorCasilla != 0) {
                    return valorCasilla;
                }
            }
        }

        return 0;
    }

    private void comprobrarNave() {
        int valorContacto = this.comprobarColision();
        if (valorContacto == -1) {
            colision = true;
        } else if (valorContacto > 0) {
            recogerMoneda(valorContacto);
        }

    }

    private void gameUpdate() {
        this.moverAliens();
        this.moverNave();
        this.actualizarTablero();
    }

    private void gameRender() {
    }

    @Override
    public void update(Graphics g) {
        // Si no sobreescribo este metodo --->>>> FLICKERING!!!
        // Cambiar Canvas por JPanel
        paint(g);
    }

    private void generarMoneda() {
        this.monedas.add(new Moneda());
    }

    private void generarNuevosElementos() {
        if ((this.aliens.get(0).getY() == 780) || (this.aliens.get(0).getY() == this.random)) {
            this.aliens.add(new Alien());
            this.generarMoneda();
            this.random = getRandom();
        }
    }

    private int getRandom() {
        double x = Math.random() * 780;

        return (int) x;
    }

    private void moverAliens() {
        for (int n = 0; n < this.aliens.size(); n++) {
            Alien bicho = this.aliens.get(n);
            bicho.mover();
            bicho.cambiarImg();
        }
    }

    private void moverNave() {
        if ((this.keys[0] == true && this.keys[1] == false) && (this.nave.getX() - 10 >= 0)) {
            this.nave.setX(-5);
        } else if ((this.keys[0] == false && this.keys[1] == true) && (this.nave.getX() + 10 <= 1200)) {
            this.nave.setX(5);
        } else {
            this.nave.setX(0);
        }

        if ((this.keys[2] == true && this.keys[3] == false) && (this.nave.getY() - 10 >= 0)) {
            this.nave.setY(-5);
        } else if ((this.keys[2] == false && this.keys[3] == true) && (this.nave.getY() + 10 <= 810)) {
            this.nave.setY(5);
        } else {
            this.nave.setY(0);
        }

    }

    private void recogerMoneda(int moneda) {
        for (int x = 0; x < 1300; x++) {
            for (int y = 0; y < 1000; y++) {
                if (this.tablero[x][y] == moneda) {
                    this.tablero[x][y] = 0;
                }
            }
        }

        this.monedas.remove(moneda - 1);
        this.puntos++;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!colision) {
            if (e.getKeyCode() == 37 && (this.nave.getX() - 10 >= 0)) {
                this.keys[0] = true;
            }
            if (e.getKeyCode() == 39 && (this.nave.getX() + 10 <= 1200)) {
                this.keys[1] = true;
            }
            if (e.getKeyCode() == 38 && (this.nave.getY() - 10 >= 0)) {
                this.keys[2] = true;
            }
            if (e.getKeyCode() == 40 && (this.nave.getY() + 10 <= 810)) {
                this.keys[3] = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!colision) {
            if (e.getKeyCode() == 37) {
                this.keys[0] = false;
            }
            if (e.getKeyCode() == 39) {
                this.keys[1] = false;
            }
            if (e.getKeyCode() == 38) {
                this.keys[2] = false;
            }
            if (e.getKeyCode() == 40) {
                this.keys[3] = false;
            }
        }
    }

}
