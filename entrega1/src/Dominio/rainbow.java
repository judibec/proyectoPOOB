package Dominio;

import java.awt.*;

public class rainbow extends fichas{
    private int colorPos = 0;
    private Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};

    public rainbow(int[][] tab, float x_iz, float x_de, float n_y, Tetris tetris, int tipo) {
        super(tab, x_iz, x_de, n_y, tetris, tipo);
    }

    protected int[][] aba() {
        boolean cambiado = true;
        for(int i = 19; i >= 0; i-=1){
            for(int j = 9; j >= 0; j-=1){
                if(tablero[i][j] == 1 && cambiado){
                    saberSiHayFicha(i, j);
                }
                if(tablero[i][j] == 1 && cambiado){
                    cambiado = false;
                    tablero[i][j] = 0;
                    tablero[i + 1][j] = 1;
                    tablero[i - 1][j - 1] = 0;
                    tablero[i][j - 1] = 1;
                    tablero[i - 1][j - 2] = 0;
                    tablero[i][j - 2] = 1;
                    tablero[i][j - 3] = 0;
                    tablero[i + 1][j - 3] = 1;
                    y += 1;
                }
            }
        }
        return tablero;
    }

    private void saberSiHayFicha(int i, int j) {
        int temp  = -7;
        if(tablero[i+1][j]>= 7 || tablero[i+1][j]==-1){
            tetris.setTengoBufo(true);
        }else if(tablero[i][j-1]>= 7 || tablero[i][j-1]==-1){
            tetris.setTengoBufo(true);
        }else if(tablero[i][j-2]>= 7 || tablero[i][j-2]==-1){
            tetris.setTengoBufo(true);
        }else if(tablero[i+1][j-3]>= 7 || tablero[i+1][j-3]==-1){
            tetris.setTengoBufo(true);
        }
        if(tablero[i][j] ==1 && tablero[i+1][j]!=0 && tablero[i+1][j]!=7 && tablero[i+1][j]!=8 && tablero[i+1][j]!=9 && tablero[i+1][j]!=-1){
//            tablero[i][j] = temp;
//            tablero[i - 1][j - 1] = temp;
//            tablero[i - 1][j - 2] = temp;
//            tablero[i][j - 3] = temp;
            bajar(i);
        }
        else if(tablero[i][j-1]!=0 && tablero[i][j-1]!=7 && tablero[i][j-1]!=8 && tablero[i][j-1]!=9 && tablero[i][j-1]!=-1){
//            tablero[i][j] = temp;
//            tablero[i - 1][j - 1] = temp;
//            tablero[i - 1][j - 2] = temp;
//            tablero[i][j - 3] = temp;
            bajar(i);
        }
        else if(tablero[i][j-2]!=0 && tablero[i][j-2]!=7 && tablero[i][j-2]!=8 && tablero[i][j-2]!=9 && tablero[i][j-2]!=-1){
//            tablero[i][j] = temp;
//            tablero[i - 1][j - 1] = temp;
//            tablero[i - 1][j - 2] = temp;
//            tablero[i][j - 3] = temp;
            bajar(i);
        }
        else if(tablero[i+1][j-3]!=0 && tablero[i+1][j-3]!=7 && tablero[i+1][j-3]!=8 && tablero[i+1][j-3]!=9 && tablero[i+1][j-3]!=-1){
//            tablero[i][j] = temp;
//            tablero[i - 1][j - 1] = temp;
//            tablero[i - 1][j - 2] = temp;
//            tablero[i][j - 3] = temp;
            bajar(i);
        }

    }


    protected int[][] izq() {
        if(colorPos == 6){
            colorPos = 0;
            for(int i = 0; i < 10; i++){
                setPuntaje();
            }
        }else{
            colorPos ++;
        }

        boolean cambiado = true;
        for (int i = 19; i >= 0; i-=1) {
            for (int j = 9; j >= 0; j -= 1) {
                if(tablero[i][j]== 1 && cambiado){
                    cambiado = saberFichaIz(i,j);
                }
                if(tablero[i][j] == 1 && cambiado){
                    cambiado = false;
                    tablero[i][j] = 0;
                    tablero[i][j - 1] = 1;
                    tablero[i - 1][j - 1] = 0;
                    tablero[i - 1][j - 3] = 1;
                    tablero[i][j - 3] = 0;
                    tablero[i][j - 4] = 1;
                    x_izq -= 1;
                    x_der -= 1;
                }
            }
        }
        return tablero;
    }

    private boolean saberFichaIz(int i, int j) {
        boolean bandera = true;
        if(tablero[i][j-4]>=7 || tablero[i][j-4]==-1){
            tetris.setTengoBufo(true);
        }else if(tablero[i-1][j-3]>=7 || tablero[i-1][j-3]==-1){
            tetris.setTengoBufo(true);
        }
        if(tablero[i][j-4]!=0 && tablero[i][j-4]!=7 && tablero[i][j-4]!=8 && tablero[i][j-4]!=9 && tablero[i][j-4]!=-1){
            bandera = false;
        }
        else if(tablero[i-1][j-3]!=0 && tablero[i-1][j-3]!=7 && tablero[i-1][j-3]!=8 && tablero[i-1][j-3]!=9 && tablero[i-1][j-3]!=-1){
            bandera = false;
        }
        return bandera;
    }


    protected int[][] der() {
        if(colorPos == 6){
            colorPos = 0;
            for(int i = 0; i < 10; i++){
                setPuntaje();
            }
        }else{
            colorPos ++;
        }
        boolean cambiado = true;
        for (int i = 19; i >= 0; i-=1) {
            for (int j = 9; j >= 0; j -= 1) {
                if(tablero[i][j]== 1 && cambiado){
                    cambiado = saberFichaDe(i,j);
                }
                if(tablero[i][j] == 1 && cambiado){
                    cambiado = false;
                    tablero[i][j] = 0;
                    tablero[i][j + 1] = 1;
                    tablero[i - 1][j] = 1;
                    tablero[i - 1][j - 2] = 0;
                    tablero[i][j - 2] = 1;
                    tablero[i][j - 3] = 0;
                    x_izq += 1;
                    x_der += 1;
                }
            }
        }
        return tablero;
    }

    private boolean saberFichaDe(int i, int j) {
        boolean bandera = true;
        if(tablero[i][j+1]>=7 || tablero[i][j+1]==-1){
            tetris.setTengoBufo(true);
        }else if(tablero[i-1][j+1]>=7 || tablero[i-1][j+1]==-1){
            tetris.setTengoBufo(true);
        }
        if(tablero[i][j+1]!=0 && tablero[i][j+1]!=7 && tablero[i][j+1]!=8 && tablero[i][j+1]!=9 && tablero[i][j+1]!=-1){
            bandera = false;
        }
        else if(tablero[i-1][j]!=0 && tablero[i-1][j]!=7 && tablero[i-1][j]!=8 && tablero[i-1][j]!=9 && tablero[i-1][j]!=-1){
            bandera = false;
        }
        return bandera;
    }


    protected int[][] rotar() {
        return tablero;
    }


    protected int[][] llegoAlFinal() {
        int temp = -7;
//        for (int j = 9; j >= 0; j -= 1) {
//            if(tablero[19][j] == 1){
//                tablero[19][j] = temp;
//                tablero[18][j - 1] = temp;
//                tablero[18][j - 2] = temp;
//                tablero[19][j - 3] = temp;
//            }
//        }
        bajar(19);
        return tablero;
    }

    protected Color getColor() {
        return colors[colorPos];
    }

    private void bajar(int i){
        tetris.borrarLinea(i - 1);
        tetris.bajarLineas(i- 1);
        tetris.borrarLinea(i);
        tetris.bajarLineas(i);
    }
}
