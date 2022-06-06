package Dominio;

import java.awt.*;

public class ese extends fichas{
    private int estado;
    private Color color = Color.green;

    /**
     * Constructor de la ese
     * @param tab tablero de juego
     * @param x_iz ubicacion de la pieza mas a la izquierda del cuadrado
     * @param x_de ubicacion de la pieza mas a la derecha del cuadrado
     * @param n_y altura del cuadrado
     * @param tetris instancia actual de la clase que controla todo
     * @param tipo clase a la que pertenece la ficha actual
     */
    public ese(int[][] tab, int x_iz, int x_de, int n_y,Tetris tetris, int tipo){
        super(tab,x_iz,x_de,n_y,tetris,tipo);
        tablero = tab;
        x_izq = x_iz;
        x_der = x_de;
        y = n_y;
        this.tipo = tipo;
        estado = 2;
    }

    /**
     * metodo que baja una fila la pieza
     * @return tablero con la ficha bajada
     */
    public int[][] aba(){
        boolean cambiado = true;
        for (int i = 19; i >= 0; i-=1) {
            for (int j = 9; j >= 0; j -= 1){
                if(tablero[i][j] == 1 && estado == 1 && cambiado){
                    if(tablero[i][j+1] >=7 || tablero[i+1][j] >=7 || tablero[i+1][j-1] >=7 || tablero[i][j+1] == -1 || tablero[i+1][j] == -1 || tablero[i+1][j-1] == -1){
                        tetris.setTengoBufo(true);
                    }
                    else if(tablero[i][j+1] !=0 || tablero[i+1][j] !=0 || tablero[i+1][j-1] !=0){
                        saberSiHayFicha(i, j);
                    }

                }
                if(tablero[i][j] == 1 && estado == 2 && cambiado){
                    if(tablero[i +1][j] >= 7 || tablero[i][j - 1] >= 7 || tablero[i +1][j] ==-1 || tablero[i][j - 1] ==-1){
                        tetris.setTengoBufo(true);
                    }
                    else if(tablero[i +1][j] !=0 || tablero[i][j - 1] !=0){
                        saberSiHayFicha(i, j);
                    }
                }
                if (tablero[i][j] == 1 && estado == 1 && cambiado){
                    cambiado = false;
                    tablero[i+1][j] = 1;
                    tablero[i+1][j-1] = 1;
                    tablero[i][j+1]= 1;
                    tablero[i][j-1]=0;
                    tablero[i-1][j]=0;
                    tablero[i-1][j+1]=0;
                    y+=1;
                }else if (tablero[i][j] == 1 && estado == 2 && cambiado) {
                    cambiado = false;
                    tablero[i+1][j]=1;
                    tablero[i][j-1]=1;
                    tablero[i-1][j]=0;
                    tablero[i-2][j-1]=0;
                    y+=1;
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
        int temp = 4;
        if(tipo == 1){
            temp = -4;
        }
        if(estado == 2){
            tablero[i][j] = temp;
            tablero[i - 1][j] = temp;
            tablero[i - 1][j - 1] = temp;
            tablero[i - 2][j  - 1] = temp;
            if(tipo == 3){
                bomb(i,j);
            }
        }else if(estado == 1){
            tablero[i][j] = temp;
            tablero[i][j - 1] = temp;
            tablero[i - 1][j] = temp;
            tablero[i - 1][j + 1] = temp;
            if(tipo == 3){
                bomb(i,j);
            }
        }
    }

    /**
     * metodo que mueve la ese a la izquierda
     * @return tablero con la ficha movida
     */
    public int[][] izq(){
        boolean cambiado = true;
        for (int i = 19; i >= 0; i-=1) {
            for (int j = 9; j >= 0; j -= 1) {
                if(tablero[i][j] == 1 && cambiado){
                    cambiado = saberFichaIz(i,j);
                }
                if(tablero[i][j]==1 && estado == 1 && cambiado){
                    cambiado = false;
                    tablero[i][j-2]=1;
                    tablero[i-1][j-1]=1;
                    tablero[i-1][j+1]=0;
                    tablero[i][j]=0;
                    x_izq -= 1;
                    x_der -= 1;
                }else if(tablero[i][j]==1 && estado==2 && cambiado){
                    cambiado = false;
                    tablero[i][j-1]=1;
                    tablero[i-1][j-2]=1;
                    tablero[i-2][j-2]=1;
                    tablero[i][j]=0;
                    tablero[i-1][j]=0;
                    tablero[i-2][j-1]=0;
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
    private boolean saberFichaIz(int i, int j){
        boolean bandera = true;
        if(estado == 2){
            if(tablero[i][j-1]>=7 || tablero[i][j-1]== -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-1][j-2]>=7 || tablero[i-1][j-2]== -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-2][j-2]>=7 || tablero[i-2][j-2]== -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j]==1 && tablero[i][j-1]!=0 && tablero[i][j-1]!=7 && tablero[i][j-1]!=8 && tablero[i][j-1]!=9 && tablero[i][j-1]!=-1) {
                bandera = false;
            }else if(tablero[i-1][j-1]==1 && tablero[i-1][j-2]!=0 && tablero[i-1][j-2]!=7 && tablero[i-1][j-2]!=8 && tablero[i-1][j-2]!=9 && tablero[i-1][j-2]!=-1) {
                bandera = false;
            }else if(tablero[i-2][j-1]==1 && tablero[i-2][j-2]!=0 && tablero[i-2][j-2]!=7 && tablero[i-2][j-2]!=8 && tablero[i-2][j-2]!=9 && tablero[i-2][j-2]!=-1) {
                bandera = false;
            }
        }else if(estado == 1){
            if(tablero[i][j-2]>= 7 || tablero[i][j-2]== -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-1][j-1]>= 7 || tablero[i-1][j-1]== -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j-1]==1 && tablero[i][j-2]!=0 && tablero[i][j-2]!=7 && tablero[i][j-2]!=8 && tablero[i][j-2]!=9 && tablero[i][j-2]!=-1) {
                bandera = false;
            }else if(tablero[i-1][j]==1 && tablero[i-1][j-1]!=0 && tablero[i-1][j-1]!=7 && tablero[i-1][j-1]!=8 && tablero[i-1][j-1]!=9 && tablero[i-1][j-1]!=-1) {
                bandera = false;
            }
        }
        return bandera;
    }

    /**
     * metodo que mueve la ficha actual a la derecha
     * @return tablero con la ficha movida
     */
    public int[][] der() {
        boolean cambiado = true;
        for (int i = 19; i >= 0; i-=1) {
            for (int j = 9; j >= 0; j -= 1) {
                if(tablero[i][j] == 1 && cambiado){
                    cambiado = saberFichaDe(i,j);
                }
                if(tablero[i][j]==1 && estado==1 && cambiado){
                    cambiado = false;
                    tablero[i][j+1]=1;
                    tablero[i-1][j+2]=1;
                    tablero[i-1][j]=0;
                    tablero[i][j-1]=0;
                    x_izq += 1;
                    x_der += 1;
                }else if(tablero[i][j]==1 && estado==2 && cambiado){
                    cambiado = false;
                    tablero[i][j+1]=1;
                    tablero[i-1][j+1]=1;
                    tablero[i-2][j]=1;
                    tablero[i][j]=0;
                    tablero[i-1][j-1]=0;
                    tablero[i-2][j-1]=0;
                    x_izq += 1;
                    x_der += 1;
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
    private boolean saberFichaDe(int i, int j){
        boolean bandera = true;
        if(estado == 2){
            if(tablero[i][j+1]>=7 || tablero[i][j+1]== -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-1][j+1]>=7 || tablero[i-1][j+1]== -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-2][j]>=7 || tablero[i-2][j]== -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j]==1 && tablero[i][j+1]!=0 && tablero[i][j+1]!=7 && tablero[i][j+1]!=8 && tablero[i][j+1]!=9 && tablero[i][j+1]!=-1) {
                bandera = false;
            }else if(tablero[i-1][j]==1 && tablero[i-1][j+1]!=0 && tablero[i-1][j+1]!=7 && tablero[i-1][j+1]!=8 && tablero[i-1][j+1]!=9 && tablero[i-1][j+1]!=-1) {
                bandera = false;
            }else if(tablero[i-2][j-1]==1 && tablero[i-2][j]!=0 && tablero[i-2][j]!=7 && tablero[i-2][j]!=8 && tablero[i-2][j]!=9 && tablero[i-2][j]!=-1) {
                bandera = false;
            }
        }else if(estado == 1){
            if(tablero[i][j+1]>= 7 || tablero[i][j+1]== -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-1][j+2]>= 7 || tablero[i-1][j+2]== -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j]==1 && tablero[i][j+1]!=0 && tablero[i][j+1]!=7 && tablero[i][j+1]!=8 && tablero[i][j+1]!=9 && tablero[i][j+1]!=-1) {
                bandera = false;
            }else if(tablero[i-1][j+1]==1 && tablero[i-1][j+2]!=0 && tablero[i-1][j+2]!=7 && tablero[i-1][j+2]!=8 && tablero[i-1][j+2]!=9 && tablero[i-1][j+2]!=-1) {
                bandera = false;
            }
        }
        return bandera;
    }

    /**
     * metodo que revisa si es posible rotar
     * @return booleano que confirma si es posible o no
     */
    private boolean revisarRotar(){
        if(estado == 1 && y == 20){
            return false;
        }
        else if(estado == 2 && x_izq == 0){
            return false;
        }
        return true;
    }

    /**
     * rota la ficha
     * @return tablero con la ficha rotada
     */
    public int[][] rotar(){
        boolean cambiado = true;
        if(revisarRotar()) {
            for (int i = 19; i >= 0; i -= 1) {
                for (int j = 9; j >= 0; j -= 1) {
                    if (tablero[i][j] == 1 && estado == 1 && cambiado) {
                        cambiado = false;
                        estado = 2;
                        tablero[i + 1][j + 1] = 1;
                        tablero[i][j + 1] = 1;
                        tablero[i][j - 1] = 0;
                        tablero[i - 1][j + 1] = 0;
                        y += 1;
                        x_izq += 1;
                    } else if (tablero[i][j] == 1 && estado == 2 && cambiado) {
                        cambiado = false;
                        estado = 1;
                        tablero[i - 1][j - 2] = 1;
                        tablero[i - 2][j] = 1;
                        tablero[i][j] = 0;
                        tablero[i - 1][j] = 0;
                        y -= 1;
                        x_izq -= 1;

                    }
                }
            }
        }
        return tablero;
    }

    /**
     * metodo que revisa si la ficha actual llego al final del tablero
     * @return tablero con la ficha cambiada su representacion
     */
    public int[][] llegoAlFinal(){
        int temp = 4;
        if(tipo == 1){
            temp = -4;
        }
        for (int i = 0; i < 10; i++) {
            if (tablero[19][i] == 1 && estado == 1) {
                tablero[19][i] = temp;
                tablero[19][i+1] = temp;
                tablero[18][i+1] = temp;
                tablero[18][i+2] = temp;
                if(tipo == 3){
                    bomb(19,i+1);
                }
            }
            else if(tablero[19][i] == 1 && estado == 2){
                tablero[19][i] = temp;
                tablero[18][i] = temp;
                tablero[18][i-1] = temp;
                tablero[17][i-1] = temp;
                if(tipo == 3){
                    bomb(19,i);
                }
            }
        }
        return tablero;
    }

    /**
     * metodo para el tipo de ficha bomb
     * @param i posicion i de la ficha dentro de la matriz
     * @param j posicion j de la ficha dentro de la matriz
     */
    private void bomb(int i, int j){
        if (estado == 2){
            if(x_izq != 0){
                if(tablero[i-3][j-2] != 0 && tablero[i-3][j-2] != 7 && tablero[i-3][j-2] != 8 && tablero[i-3][j-2] != 9 && tablero[i-3][j-2] != -1){
                    tablero[i-3][j-2] = 0;
                    setPuntaje();
                }
                if(tablero[i-2][j-2] != 0 && tablero[i-2][j-2] != 7 && tablero[i-2][j-2] != 8 && tablero[i-2][j-2] != 9 && tablero[i-2][j-2] != -1){
                    tablero[i-2][j-2] = 0;
                    setPuntaje();
                }
                if(tablero[i-1][j-2] != 0 && tablero[i-1][j-2] != 7 && tablero[i-1][j-2] != 8 && tablero[i-1][j-2] != 9 && tablero[i-1][j-2] != -1){
                    tablero[i-1][j-2] = 0;
                    setPuntaje();
                }
                if(tablero[i][j-2] != 0 && tablero[i][j-2] != 7 && tablero[i][j-2] != 8 && tablero[i][j-2] != 9 && tablero[i][j-2] != -1){
                    tablero[i][j-2] = 0;
                    setPuntaje();
                }
            }
            if(tablero[i-3][j-1] != 0 && tablero[i-3][j-1] != 7 && tablero[i-3][j-1] != 8 && tablero[i-3][j-1] != 9 && tablero[i-3][j-1] != -1){
                tablero[i-3][j-1] = 0;
                setPuntaje();
            }
            if(tablero[i-2][j-1] != 0 && tablero[i-2][j-1] != 7 && tablero[i-2][j-1] != 8 && tablero[i-2][j-1] != 9 && tablero[i-2][j-1] != -1){
                tablero[i-2][j-1] = 0;
                setPuntaje();
            }
            if(tablero[i-1][j-1] != 0 && tablero[i-1][j-1] != 7 && tablero[i-1][j-1] != 8 && tablero[i-1][j-1] != 9 && tablero[i-1][j-1] != -1){
                tablero[i-1][j-1] = 0;
                setPuntaje();
            }
            if(tablero[i][j-1] != 0 && tablero[i][j-1] != 7 && tablero[i][j-1] != 8 && tablero[i][j-1] != 9 && tablero[i][j-1] != -1){
                tablero[i][j-1] = 0;
                setPuntaje();
            }
            if(y != 20){
                if(tablero[i+1][j-1] != 0 && tablero[i+1][j-1] != 7 && tablero[i+1][j-1] != 8 && tablero[i+1][j-1] != 9 && tablero[i+1][j-1] != -1){
                    tablero[i+1][j-1] = 0;
                    setPuntaje();
                }
                if(tablero[i+1][j] != 0 && tablero[i+1][j] != 7 && tablero[i+1][j] != 8 && tablero[i+1][j] != 9 && tablero[i+1][j] != -1){
                    tablero[i+1][j] = 0;
                    setPuntaje();
                }
            }
            if(tablero[i][j] != 0 && tablero[i][j] != 7 && tablero[i][j] != 8 && tablero[i][j] != 9 && tablero[i][j] != -1){
                tablero[i][j] = 0;
                setPuntaje();
            }
            if(tablero[i-1][j] != 0 && tablero[i-1][j] != 7 && tablero[i-1][j] != 8 && tablero[i-1][j] != 9 && tablero[i-1][j] != -1){
                tablero[i-1][j] = 0;
                setPuntaje();
            }
            if(tablero[i-2][j] != 0 && tablero[i-2][j] != 7 && tablero[i-2][j] != 8 && tablero[i-2][j] != 9 && tablero[i-2][j] != -1){
                tablero[i-2][j] = 0;
                setPuntaje();
            }
            if(tablero[i-3][j] != 0 && tablero[i-3][j] != 7 && tablero[i-3][j] != 8 && tablero[i-3][j] != 9 && tablero[i-3][j] != -1){
                tablero[i-3][j] = 0;
                setPuntaje();
            }
            if(x_der < 9){
                if(tablero[i-2][j+1] != 0 && tablero[i-2][j+1] != 7 && tablero[i-2][j+1] != 8 && tablero[i-2][j+1] != 9 && tablero[i-2][j+1] != -1){
                    tablero[i-2][j+1] = 0;
                    setPuntaje();
                }
                if(tablero[i-1][j+1] != 0 && tablero[i-1][j+1] != 7 && tablero[i-1][j+1] != 8 && tablero[i-1][j+1] != 9 && tablero[i-1][j+1] != -1){
                    tablero[i-1][j+1] = 0;
                    setPuntaje();
                }
                if(tablero[i][j+1] != 0 && tablero[i][j+1] != 7 && tablero[i][j+1] != 8 && tablero[i][j+1] != 9 && tablero[i][j+1] != -1){
                    tablero[i][j+1] = 0;
                    setPuntaje();
                }
                if(y != 20){
                    if(tablero[i+1][j+1] != 0 && tablero[i+1][j+1] != 7 && tablero[i+1][j+1] != 8 && tablero[i+1][j+1] != 9 && tablero[i+1][j+1] != -1){
                        tablero[i+1][j+1] = 0;
                        setPuntaje();
                    }
                }
            }
        }
        if(estado == 1){
            if(x_izq != 0){
                if(tablero[i-1][j-2] != 0 && tablero[i-1][j-2] != 7 && tablero[i-1][j-2] != 8 && tablero[i-1][j-2] != 9 && tablero[i-1][j-2] != -1){
                    tablero[i-1][j-2] = 0;
                    setPuntaje();
                }
                if(tablero[i][j-2] != 0 && tablero[i][j-2] != 7 && tablero[i][j-2] != 8 && tablero[i][j-2] != 9 && tablero[i][j-2] != -1){
                    tablero[i][j-2] = 0;
                    setPuntaje();
                }
                if(y != 20){
                    if(tablero[i+1][j-2] != 0 && tablero[i+1][j-2] != 7 && tablero[i+1][j-2] != 8 && tablero[i+1][j-2] != 9 && tablero[i+1][j-2] != -1){
                        tablero[i+1][j-2] = 0;
                        setPuntaje();
                    }
                }
            }
            if(tablero[i-2][j-1] != 0 && tablero[i-2][j-1] != 7 && tablero[i-2][j-1] != 8 && tablero[i-2][j-1] != 9 && tablero[i-2][j-1] != -1){
                tablero[i-2][j-1] = 0;
                setPuntaje();
            }
            if(tablero[i-1][j-1] != 0 && tablero[i-1][j-1] != 7 && tablero[i-1][j-1] != 8 && tablero[i-1][j-1] != 9 && tablero[i-1][j-1] != -1){
                tablero[i-1][j-1] = 0;
                setPuntaje();
            }
            if(tablero[i][j-1] != 0 && tablero[i][j-1] != 7 && tablero[i][j-1] != 8 && tablero[i][j-1] != 9 && tablero[i][j-1] != -1){
                tablero[i][j-1] = 0;
                setPuntaje();
            }
            if(y != 20){
                if(tablero[i+1][j-1] != 0 && tablero[i+1][j-1] != 7 && tablero[i+1][j-1] != 8 && tablero[i+1][j-1] != 9 && tablero[i+1][j-1] != -1){
                    tablero[i+1][j-1] = 0;
                    setPuntaje();
                }
                if(tablero[i+1][j] != 0 && tablero[i+1][j] != 7 && tablero[i+1][j] != 8 && tablero[i+1][j] != 9 && tablero[i+1][j] != -1){
                    tablero[i+1][j] = 0;
                    setPuntaje();
                }
                if(tablero[i+1][j+1] != 0 && tablero[i+1][j+1] != 7 && tablero[i+1][j+1] != 8 && tablero[i+1][j+1] != 9 && tablero[i+1][j+1] != -1){
                    tablero[i+1][j+1] = 0;
                    setPuntaje();
                }
            }
            if(tablero[i-2][j] != 0 && tablero[i-2][j] != 7 && tablero[i-2][j] != 8 && tablero[i-2][j] != 9 && tablero[i-2][j] != -1){
                tablero[i-2][j] = 0;
                setPuntaje();
            }
            if(tablero[i-1][j] != 0 && tablero[i-1][j] != 7 && tablero[i-1][j] != 8 && tablero[i-1][j] != 9 && tablero[i-1][j] != -1){
                tablero[i-1][j] = 0;
                setPuntaje();
            }
            if(tablero[i][j] != 0 && tablero[i][j] != 7 && tablero[i][j] != 8 && tablero[i][j] != 9 && tablero[i][j] != -1){
                tablero[i][j] = 0;
                setPuntaje();
            }
            if(tablero[i-2][j+1] != 0 && tablero[i-2][j+1] != 7 && tablero[i-2][j+1] != 8 && tablero[i-2][j+1] != 9 && tablero[i-2][j+1] != -1){
                tablero[i-2][j+1] = 0;
                setPuntaje();
            }
            if(tablero[i-1][j+1] != 0 && tablero[i-1][j+1] != 7 && tablero[i-1][j+1] != 8 && tablero[i-1][j+1] != 9 && tablero[i-1][j+1] != -1){
                tablero[i-1][j+1] = 0;
                setPuntaje();
            }
            if(tablero[i][j+1] != 0 && tablero[i][j+1] != 7 && tablero[i][j+1] != 8 && tablero[i][j+1] != 9 && tablero[i][j+1] != -1){
                tablero[i][j+1] = 0;
                setPuntaje();
            }
            if(x_der < 9){
                if(tablero[i-2][j+2] != 0 && tablero[i-2][j+2] != 7 && tablero[i-2][j+2] != 8 && tablero[i-2][j+2] != 9 && tablero[i-2][j+2] != -1){
                    tablero[i-2][j+2] = 0;
                    setPuntaje();
                }
                if(tablero[i-1][j+2] != 0 && tablero[i-1][j+2] != 7 && tablero[i-1][j+2] != 8 && tablero[i-1][j+2] != 9 && tablero[i-1][j+2] != -1){
                    tablero[i-1][j+2] = 0;
                    setPuntaje();
                }
                if(tablero[i][j+2] != 0 && tablero[i][j+2] != 7 && tablero[i][j+2] != 8 && tablero[i][j+2] != 9 && tablero[i][j+2] != -1){
                    tablero[i][j+2] = 0;
                    setPuntaje();
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
