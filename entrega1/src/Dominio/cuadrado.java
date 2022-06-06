package Dominio;

import java.awt.*;
import java.util.Objects;

public class cuadrado extends fichas{
    private Color color = Color.YELLOW;

    /**
     * Constructor del cuadrado
     * @param tab tablero de juego
     * @param x_iz ubicacion de la pieza mas a la izquierda del cuadrado
     * @param x_de ubicacion de la pieza mas a la derecha del cuadrado
     * @param n_y altura del cuadrado
     * @param tetris instancia actual de la clase que controla todo
     * @param tipo clase a la que pertenece la ficha actual
     */
    public cuadrado(int[][] tab, float x_iz, float x_de, float n_y, Tetris tetris, int tipo){
        super(tab,x_iz,x_de,n_y,tetris,tipo);
        tablero = tab;
        x_izq = x_iz;
        x_der = x_de;
        y = n_y;
        this.tipo = tipo;
        ficha = new int[2][2];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j<2; j++){
                ficha[i][j] = 1;
            }
        }
    }

    /**
     * metodo que baja una fila la pieza
     * @return tablero con la ficha bajada
     */
    public int[][] aba(){
        boolean bandera = true;
        boolean revisado = true;
        for (int i = 19; i >= 0; i-=1){
            for (int j = 9; j >= 0; j-=1){
                if(tablero[i][j] == 1 && revisado){
                    revisado = false;
                    saberSiHayFicha(i, j);
                }
                if(tablero[i][j] == 1 && bandera){
                    tablero[i-1][j] = 0;
                    tablero[i-1][j-1] = 0;
                    tablero[i+1][j] = 1;
                    tablero[i+1][j-1] = 1;
                    bandera = false;
                    y += 1;
                }
            }
        }
        return tablero;
    }

    /**
     * metodo que revisa si debajo de la ficha actual hay otra con la cual chocar
     * @param i posicion i de la ficha dentro de la matriz
     * @param j posicion j de la ficha dentro de la matriz
     */
    private void saberSiHayFicha(int i, int j){
        int inicio = 0;
        int temp = 2;
        if(tipo == 1){
            temp = -2;
        }
        for(inicio = 0; inicio < 2; inicio++){
            if(tablero[i+1][j-inicio]>=7 || tablero[i+1][j-inicio]==-1){
                tetris.setTengoBufo(true);
            }
            else if(tablero[i+1][j - inicio] != 0 && tablero[i+1][j-inicio]!=7  && tablero[i+1][j-inicio]!=8 && tablero[i+1][j-inicio]!=9 && tablero[i+1][j-inicio]!=-1){
                tablero[i][j] = temp;
                tablero[i][j-1] = temp;
                tablero[i-1][j] = temp;
                tablero[i-1][j-1] = temp;
                if(tipo == 3){
                    bomb(i,j,'n');
                }
            }
        }
    }

    /**
     * metodo que mueve la ficha a la izquierda
     * @return tablero con la ficha movida
     */
    public int[][] izq(){
        boolean cambiado = true;
        for (int i = 19; i >= 0; i-=1) {
            for (int j = 9; j >= 0; j-=1) {
                if(tablero[i][j]==1 && cambiado){
                    cambiado = saberFichaIz(i,j);
                }
                if(tablero[i][j] == 1 && j >= 1 && cambiado){
                    tablero[i][j] = 0;
                    tablero[i-1][j] = 0;
                    tablero[i][j-2] = 1;
                    tablero[i-1][j-2] = 1;
                    cambiado = false;
                    x_izq -= 1;
                    x_der -= 1;
                }
            }
        }
        return tablero;
    }

    /**
     * revisa si al lado de la ficha actual existe otra con la cual chocar
     * @param i posicion i de la ficha dentro de la matriz
     * @param j posicion j de la ficha dentro de la matriz
     * @return booleano que dice si es posible moverse o no
     */
    public boolean saberFichaIz(int i,int j){
        boolean bandera = true;
        if(tablero[i][j-2]>=7 || tablero[i][j-2]==-1){
            tetris.setTengoBufo(true);
        }if(tablero[i-1][j-2]>=7 || tablero[i-1][j-2]==-1){
            tetris.setTengoBufo(true);
        }
        if(tablero[i][j]== 1 && tablero[i][j-2]!=0 && tablero[i][j-2]!=7 && tablero[i][j-2]!=8 && tablero[i][j-2]!=9 && tablero[i][j-2]!=-1){
            bandera = false;
        }else if(tablero[i-1][j]== 1 && tablero[i-1][j-2]!=0 && tablero[i-1][j-2]!=7 && tablero[i-1][j-2]!=8 && tablero[i-1][j-2]!=9 && tablero[i-1][j-2]!=-1){
            bandera = false;
        }
        return bandera;
    }

    /**
     * metodo que mueve la ficha actual a la derecha
     * @return tablero con la ficha movida
     */
    public int[][] der(){
        boolean cambiado = true;
        for (int i = 19; i >= 0; i-=1) {
            for (int j = 0; j < 10; j+=1) {
                if(tablero[i][j]==1 && cambiado){
                    cambiado = saberFichaDe(i,j);
                }
                if(tablero[i][j] == 1 && j <= 8 && cambiado){
                    tablero[i][j] = 0;
                    tablero[i-1][j] = 0;
                    tablero[i][j+2] = 1;
                    tablero[i-1][j+2] = 1;
                    cambiado = false;
                    x_der += 1;
                    x_izq += 1;
                }
            }
        }
        return tablero;
    }

    /**
     * revisa si al lado de la ficha actual existe otra con la cual chocar
     * @param i posicion i de la ficha dentro de la matriz
     * @param j posicion j de la ficha dentro de la matriz
     * @return booleano que dice si es posible moverse o no
     */
    public boolean saberFichaDe(int i,int j){
        boolean bandera = true;
        if(tablero[i][j+2]>=7 || tablero[i][j+2]==-1){
            tetris.setTengoBufo(true);
        }if(tablero[i-1][j+2]>=7 || tablero[i-1][j+2]==-1){
            tetris.setTengoBufo(true);
        }
        if(tablero[i][j]== 1 && tablero[i][j+2]!=0 && tablero[i][j+2]!=7 && tablero[i][j+2]!=8 && tablero[i][j+2]!=9 && tablero[i][j+2]!=-1){
            bandera = false;
        }else if(tablero[i-1][j]== 1 && tablero[i-1][j+2]!=0 && tablero[i-1][j+2]!=7 && tablero[i-1][j+2]!=8 && tablero[i-1][j+2]!=9 && tablero[i-1][j+2]!=-1){
            bandera = false;
        }
        return bandera;
    }

    /**
     * rota la ficha (en este caso no es necesario su implementacion)
     * @return tablero con la ficha rotada
     */
    public int[][] rotar(){
        return tablero;
    }

    /**
     * metodo que revisa si la ficha actual llego al final del tablero
     * @return tablero con la ficha cambiada su representacion
     */
    public int[][] llegoAlFinal(){
        int temp = 2;
        if(tipo == 1){
            temp = -2;
        }
        for (int i = 0; i < 10; i++) {
            if (tablero[19][i] == 1) {
                if(tipo == 3){
                    bomb(19,i,'f');
                }else if(tipo == 2 || tipo == 1) {
                    tablero[19][i] = temp;
                    tablero[18][i] = temp;
                }
            }
        }
        return tablero;
    }

    /**
     * metodo para el tipo de ficha bomb
     * @param i posicion i de la ficha dentro de la matriz
     * @param j posicion j de la ficha dentro de la matriz
     * @param f caracter que define si es un caso donde la ficha llego al final o choco con otra ficha
     */
    private void bomb(int i, int j, char f){
        if (f == 'f') {
            int c = j - 1;
            int d = j + 2;
            if(x_izq == 0){
                c += 1;
            }
            if(x_der == 9){
                d -= 1;
            }
            for (int a = i - 2; a < i + 1; a++) {
                for (int e = c; e < d + 1; e++) {
                    if (tablero[a][e] != 0 && tablero[a][e] != 7 && tablero[a][e] != 8 && tablero[a][e] != 9 && tablero[a][e] != -1) {
                        tablero[a][e] = 0;
                        setPuntaje();
                    }
                }
            }
        }
        else {
            int c = j - 2;
            int d = j + 1;
            if(x_izq == 0){
                c += 1;
            }
            if(x_der == 9){
                d -= 1;
            }
            for(int a = i - 2; a < i+2; a++){
                for(int e = c; e < d + 1; e++){
                    if(tablero[a][e] != 0 && tablero[a][e] != 7 && tablero[a][e] != 8 && tablero[a][e] != 9 && tablero[a][e] != -1){
                        tablero[a][e] = 0;
                        setPuntaje();
                    }
                }
            }
        }
    }

    /**
     * metodo que retorna el color de la ficha
     * @return color
     */
    public Color getColor(){
        return color;
    }
}
