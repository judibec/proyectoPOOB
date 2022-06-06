package Dominio;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Buffo {
    public int[][] tablero;

    /**
     * constructor de los buffos
     * @param tablero tablero de juego
     */
    public Buffo(int [][] tablero){
        this.tablero = tablero;
    }

    protected abstract int activarBuffo();

    /**
     * primero se revisa a partir de que linea se puede agregar un buffo, luego se selecciona un i y j de la matriz
     * donde se va a ubicar el buffo
     * @param bufo buffo que va a ser agregado en el tablero
     */
    public void revisar(Buffo bufo){
        int maxi = 20;
        boolean bandera = true;
        for(int i = 0;i<20;i++){
            for (int j = 0; j<10;j++){
                if (tablero[i][j]!=0 && tablero[i][j]!=1 && tablero[i][j] < 7 && tablero[i][j]!= -1 && bandera){
                    maxi = i-1;
                    bandera = false;
                }
            }
        }
        int i = ThreadLocalRandom.current().nextInt(2, maxi);
        int j = ThreadLocalRandom.current().nextInt(0, 10);
        while (tablero[i][j]!=0 && tablero[i][j]!=1){
            i = ThreadLocalRandom.current().nextInt(2, maxi);
            j = ThreadLocalRandom.current().nextInt(0, 10);
        }
        if(bufo instanceof StopTime){
            tablero[i][j] = 7;
        }else if(bufo instanceof StopDiece){
            tablero[i][j] = 8;
        }else if(bufo instanceof Slow){
            tablero[i][j] = 9;
        }else if(bufo instanceof x2){
            tablero[i][j] = -1;
        }
    }
}
