package Dominio;

public class StopTime extends Buffo{

    /**
     * constructor del buffo stop time
     * @param tablero tablero del juego
     */
    public StopTime(int[][] tablero){
        super(tablero);
    }

    /**
     * metodo que hace que el buffo se activa
     * @return numero que representa al buffo
     */
    public int activarBuffo(){
        return 1;
    }


}
