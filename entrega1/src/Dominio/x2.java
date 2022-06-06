package Dominio;

public class x2 extends Buffo{

    /**
     * constructor del buffo x2
     * @param tablero tablero del juego
     */
    public x2(int [][] tablero){
        super(tablero);
    }

    /**
     * metodo que hace que el buffo se activa
     * @return numero que representa al buffo
     */
    public int activarBuffo(){
        return 4;
    }
}
