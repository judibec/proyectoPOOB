package Dominio;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pc {

    private Robot robot;
    private int delay;
    private Tetris tetris;


    /**
     * Contrtuctor de la clase Pc
     */
    public Pc(Tetris tetris) {
        try {
            robot = new Robot();
            this.tetris = tetris;
        } catch (AWTException ex) {
            Logger.getLogger(Pc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * metodo que hace que la maquina baje la pieza
     */
    public void abajo() {
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.delay(delay);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.delay(delay);
    }

    /**
     * metodo que hace que la maquina mueva a la izquierda la pieza
     */
    public void izquierda() {
        robot.keyPress(KeyEvent.VK_LEFT);
        robot.delay(delay);
        robot.keyRelease(KeyEvent.VK_LEFT);
        robot.delay(delay);
    }

    /**
     * metodo que hace que la maquina mueva a la derecha la pieza
     */
    public void derecha() {
        robot.keyPress(KeyEvent.VK_RIGHT);
        robot.delay(delay);
        robot.keyRelease(KeyEvent.VK_RIGHT);
        robot.delay(delay);
    }

    /**
     * metodo que hace que la maquina rota la pieza
     */
    public void rotar() {
        robot.keyPress(KeyEvent.VK_UP);
        robot.delay(delay);
        robot.keyRelease(KeyEvent.VK_UP);
        robot.delay(delay);
    }

    /**
     * metodo que hace que la maquina use el poder
     */
    public void poder() {
        robot.keyPress(KeyEvent.VK_0);
        robot.delay(delay);
        robot.keyRelease(KeyEvent.VK_0);
        robot.delay(delay);
    }


    /**
     * metodo que hace que la maquina realice una accion aleatoria
     */
    public void AccionRandom() {
        int action = (int) (Math.random() * 6);
        if (action == 1) {
            izquierda();
        }
        else if (action == 2) {
            derecha();
        } else if (action == 3) {
            abajo();
        } else if (action == 4) {
            rotar();
        }if (action == 5) {
            poder();
        }
    }
}