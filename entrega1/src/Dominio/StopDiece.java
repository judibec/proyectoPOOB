package Dominio;

public class StopDiece extends Buffo{

    /**
     * constructor del buffo stop diece
     * @param tablero tablero del juego
     */
    public StopDiece(int [][] tablero){
        super(tablero);
    }

    /**
     * metodo que hace que el buffo se activa
     * @return numero que representa al buffo
     */
    public int activarBuffo(){
        return 2;
    }
}
