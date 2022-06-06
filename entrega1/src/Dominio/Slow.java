package Dominio;

public class Slow extends Buffo{

    /**
     * constructor del buffo slow
     * @param tablero tablero del juego
     */
    public Slow(int [][] tablero){
        super(tablero);
    }

    /**
     * metodo que hace que el buffo se activa
     * @return numero que representa al buffo
     */
    public int activarBuffo(){
        return 3;
    }
}
