package Dominio;

import java.awt.*;

public abstract class fichas{
    public int[][] ficha;
    public int[][] tablero;
    public float x_izq;
    public float x_der;
    public float y;
    public Tetris tetris;
    public int tipo;


    /**
     * Constructor de las fichas
     * @param tab tablero de juego
     * @param x_iz ubicacion de la pieza mas a la izquierda del cuadrado
     * @param x_de ubicacion de la pieza mas a la derecha del cuadrado
     * @param n_y altura del cuadrado
     * @param tetris instancia actual de la clase que controla todo
     * @param tipo clase a la que pertenece la ficha actual
     */
    public fichas(int[][] tab, float x_iz, float x_de, float n_y,Tetris tetris, int tipo){
        tablero = tab;
        x_izq = x_iz;
        x_der = x_de;
        y = n_y;
        this.tipo = tipo;
        this.tetris = tetris;
    }

    /**
     * metodo que baja una fila la pieza
     * @return tablero con la ficha bajada
     */
    protected abstract int[][] aba();

    /**
     * metodo que mueve la ficha a la izquierda
     * @return tablero con la ficha movida
     */
    protected abstract int[][] izq();

    /**
     * metodo que mueve la ficha actual a la derecha
     * @return tablero con la ficha movida
     */
    protected abstract int[][] der();

    /**
     * rota la ficha
     * @return tablero con la ficha rotada
     */
    protected abstract int[][] rotar();

    /**
     * metodo que revisa si la ficha actual llego al final del tablero
     * @return tablero con la ficha cambiada su representacion
     */
    protected abstract int[][] llegoAlFinal();

    /**
     * metodo que retorna el color de la ficha
     * @return color
     */
    protected abstract Color getColor();

    /**
     * metodo que retorna de que tipo es la ficha
     * @return entero que determina el tipo de la ficha
     */
    public int getTipo(){
        return tipo;
    }

    /**
     * metodo que cambia el puntaje
     */
    public void setPuntaje(){
        int puntajes = Integer.parseInt(tetris.puntaje);
        puntajes += 1;
        tetris.puntaje = String.valueOf(puntajes);
    }

}
