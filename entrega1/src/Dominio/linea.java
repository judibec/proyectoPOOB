package Dominio;

import java.awt.*;

public class linea extends fichas{
    private int estado;
    private Color color = Color.CYAN;

    /**
     * Constructor de la linea
     * @param tab tablero de juego
     * @param x_iz ubicacion de la pieza mas a la izquierda del cuadrado
     * @param x_de ubicacion de la pieza mas a la derecha del cuadrado
     * @param n_y altura del cuadrado
     * @param tetris instancia actual de la clase que controla todo
     * @param tipo clase a la que pertenece la ficha actual
     */
    public linea(int[][] tab, int x_iz, int x_de, int n_y,Tetris tetris,int tipo){
        super(tab,x_iz,x_de,n_y,tetris,tipo);
        estado = 1;
        tablero = tab;
        x_izq = x_iz;
        x_der = x_de;
        y = n_y;
        this.tipo = tipo;
        ficha = new int[4][4];
        for(int i = 0; i < 4; i++){
            ficha[0][i] = 1;
        }
    }

    /**
     * metodo que revisa si es posible rotar o no
     * @return booleano que confirma si es posible o no
     */
    private boolean revisarRotar(){
        if(estado == 1 && y >= 18){
            return false;
        }
        else if(estado == 2 && x_der >= 7){
            return false;
        }
        return true;
    }

    /**
     * rota la ficha
     * @return tablero con la ficha rotada
     */
    public int[][] rotar(){
        boolean rotado = true;
        if(revisarRotar()) {
            if (estado == 1) {
                estado = 2;
                for (int i = 0; i < 17; i += 1) {
                    for (int j = 0; j < 7; j += 1) {
                        if (tablero[i][j] == 1 && rotado) {
                            rotado = false;
                            tablero[i][j + 1] = 0;
                            tablero[i][j + 2] = 0;
                            tablero[i][j + 3] = 0;
                            tablero[i + 1][j] = 1;
                            tablero[i + 2][j] = 1;
                            tablero[i + 3][j] = 1;
                            x_der -= 3;
                            y += 3;
                        }
                    }
                }
            } else {
                estado = 1;
                for (int i = 0; i < 17; i += 1) {
                    for (int j = 0; j < 7; j += 1) {
                        if (tablero[i][j] == 1 && rotado) {
                            rotado = false;
                            tablero[i][j + 1] = 1;
                            tablero[i][j + 2] = 1;
                            tablero[i][j + 3] = 1;
                            tablero[i + 1][j] = 0;
                            tablero[i + 2][j] = 0;
                            tablero[i + 3][j] = 0;
                            x_der += 3;
                            y -= 3;
                        }
                    }
                }
            }
        }
        return tablero;
    }

    /**
     * metodo que baja una fila la pieza
     * @return tablero con la ficha bajada
     */
    public int[][] aba(){
        boolean bajo = true;
        boolean revisado = true;
        for (int i = 19; i >= 0; i-=1){
            for (int j = 9; j >= 0; j -= 1){
                if(tablero[i][j] == 1 && estado == 1){
                    saberSiHayFicha(i, j);
                }
                if(tablero[i][j] == 1 && estado == 2 && revisado){
                    saberSiHayFicha(i, j);
                    revisado = false;
                }
                if(tablero[i][j] == 1 && estado == 1 && bajo){
                    bajo = false;
                    tablero[i][j] = 0;
                    tablero[i][j-1] = 0;
                    tablero[i][j-2] = 0;
                    tablero[i][j-3] = 0;
                    tablero[i+1][j] = 1;
                    tablero[i+1][j-1] = 1;
                    tablero[i+1][j-2] = 1;
                    tablero[i+1][j-3] = 1;
                    y += 1;
                }
                else if(tablero[i][j] == 1 && estado == 2 && bajo){
                    bajo = false;
                    tablero[i+1][j] = 1;
                    tablero[i-3][j] = 0;
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
        int temp = 5;
        if(tipo == 1){
            temp = -5;
        }
        int inicio = 0;
        if(estado == 1){
            for(inicio = 0; inicio < 4; inicio++){
                if(tablero[i+1][j - inicio] >= 7 || tablero[i+1][j - inicio] == -1){
                    tetris.setTengoBufo(true);
                }
                else if(tablero[i][j - inicio] == 1 && tablero[i+1][j- inicio] != 0 && tablero[i+1][j- inicio] != 7 && tablero[i+1][j- inicio] != 8 && tablero[i+1][j- inicio] != 9&& tablero[i+1][j- inicio] != -1){
                    if(inicio == 0){
                        tablero[i][j- inicio] = temp;
                        tablero[i][j - inicio - 1] = temp;
                        tablero[i][j - inicio - 2] = temp;
                        tablero[i][j - inicio - 3] = temp;
                        if(tipo == 3){
                            bomb(i,j,'n');
                        }
                    }else if(inicio == 1){
                        tablero[i][j - inicio + 1] = temp;
                        tablero[i][j - inicio] = temp;
                        tablero[i][j - inicio - 1] = temp;
                        tablero[i][j - inicio - 2] = temp;
                        if(tipo == 3){
                            bomb(i,j,'n');
                        }
                    }else if(inicio == 2){
                        tablero[i][j - inicio + 2] = temp;
                        tablero[i][j - inicio + 1] = temp;
                        tablero[i][j - inicio] = temp;
                        tablero[i][j - inicio - 1] = temp;
                        if(tipo == 3){
                            bomb(i,j,'n');
                        }
                    }else if(inicio == 3){
                        tablero[i][j - inicio + 3] = temp;
                        tablero[i][j - inicio + 2] = temp;
                        tablero[i][j - inicio + 1] = temp;
                        tablero[i][j - inicio] = temp;
                        if(tipo == 3){
                            bomb(i,j,'n');
                        }

                    }
                }
            }
        }

        else if(estado == 2){
            if(tablero[i+1][j] >=7 || tablero[i+1][j] ==-1){
                tetris.setTengoBufo(true);
            }else if(tablero[i+1][j] != 0 && tablero[i+1][j] != 7 && tablero[i+1][j] != 8 && tablero[i+1][j] != 9 && tablero[i+1][j] != -1){
                tablero[i][j] = 5;
                tablero[i-1][j] = 5;
                tablero[i-2][j] = 5;
                tablero[i-3][j] = 5;
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
            for (int j = 9; j >= 1; j -= 1) {
                if(tablero[i][j] == 1 && cambiado){
                    cambiado = saberFichaIz(i,j);
                }
                if(tablero[i][j] == 1 && estado == 1 && cambiado){
                    cambiado = false;
                    tablero[i][j] = 0;
                    tablero[i][j-4] = 1;
                    x_izq -= 1;
                    x_der -= 1;
                }
                if(tablero[i][j] == 1 && estado == 2 && cambiado){
                    cambiado = false;
                    tablero[i][j] = 0;
                    tablero[i-1][j] = 0;
                    tablero[i-2][j] = 0;
                    tablero[i-3][j] = 0;
                    tablero[i][j-1] = 1;
                    tablero[i-1][j-1] = 1;
                    tablero[i-2][j-1] = 1;
                    tablero[i-3][j-1] = 1;
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
    public boolean saberFichaIz(int i, int j){
        boolean bandera = true;
        if(estado == 1){
            if(tablero[i][j-4]>=7 || tablero[i][j-4] == -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j]==1 && tablero[i][j-4]!=0 && tablero[i][j-4]!=7 && tablero[i][j-4]!=8 && tablero[i][j-4]!=9 && tablero[i][j-4]!=-1){
                bandera = false;
            }
        }else if (estado == 2){
            if(tablero[i][j-1]>=7 || tablero[i][j-1] == -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-1][j-1]>=7 || tablero[i-1][j-1] == -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-2][j-1]>=7 || tablero[i-2][j-1] == -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-3][j-1]>=7 || tablero[i-3][j-1] == -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j]==1 && tablero[i][j-1]!=0 && tablero[i][j-1]!=7 && tablero[i][j-1]!=8 && tablero[i][j-1]!=9 && tablero[i][j-1]!=-1){
                bandera = false;
            }else if(tablero[i][j]==1 && tablero[i-1][j-1]!=0 && tablero[i-1][j-1]!=7 && tablero[i-1][j-1]!=8 && tablero[i-1][j-1]!=9 && tablero[i-1][j-1]!=-1){
                bandera = false;
            }else if(tablero[i][j]==1 && tablero[i-2][j-1]!=0 && tablero[i-2][j-1]!=7 && tablero[i-2][j-1]!=8 && tablero[i-2][j-1]!=9 && tablero[i-2][j-1]!=-1){
                bandera = false;
            }else if(tablero[i][j]==1 && tablero[i-3][j-1]!=0 && tablero[i-3][j-1]!=7 && tablero[i-3][j-1]!=8 && tablero[i-3][j-1]!=9 && tablero[i-3][j-1]!=-1){
                bandera = false;
            }
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
            for (int j = 8; j >= 0; j -= 1) {
                if(tablero[i][j] == 1 && cambiado){
                    cambiado = saberFichaDe(i,j);
                }
                if(tablero[i][j] == 1 && estado == 1 && cambiado){
                    cambiado = false;
                    tablero[i][j-3] = 0;
                    tablero[i][j+1] = 1;
                    x_izq += 1;
                    x_der += 1;
                }
                if(tablero[i][j] == 1 && estado == 2 && cambiado){
                    cambiado = false;
                    tablero[i][j] = 0;
                    tablero[i-1][j] = 0;
                    tablero[i-2][j] = 0;
                    tablero[i-3][j] = 0;
                    tablero[i][j+1] = 1;
                    tablero[i-1][j+1] = 1;
                    tablero[i-2][j+1] = 1;
                    tablero[i-3][j+1] = 1;
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
    public boolean saberFichaDe(int i, int j){
        boolean bandera = true;
        if(estado == 1){
            if(tablero[i][j+1]>=7 || tablero[i][j+1] == -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j]==1 && tablero[i][j+1]!=0 && tablero[i][j+1]!=7 && tablero[i][j+1]!=8 && tablero[i][j+1]!=9 && tablero[i][j+1]!=-1){
                bandera = false;
            }
        }else if (estado == 2){
            if(tablero[i][j+1]>=7 || tablero[i][j+1] == -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-1][j+1]>=7 || tablero[i-1][j+1] == -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-2][j+1]>=7 || tablero[i-2][j+1] == -1){
                tetris.setTengoBufo(true);
            }else if(tablero[i-3][j+1]>=7 || tablero[i-3][j+1] == -1){
                tetris.setTengoBufo(true);
            }
            if(tablero[i][j]==1 && tablero[i][j+1]!=0 && tablero[i][j+1]!=7 && tablero[i][j+1]!=8 && tablero[i][j+1]!=9 && tablero[i][j+1]!=-1){
                bandera = false;
            }else if(tablero[i][j]==1 && tablero[i-1][j+1]!=0 && tablero[i-1][j+1]!=7 && tablero[i-1][j+1]!=8 && tablero[i-1][j+1]!=9 && tablero[i-1][j+1]!=-1){
                bandera = false;
            }else if(tablero[i][j]==1 && tablero[i-2][j+1]!=0 && tablero[i-2][j+1]!=7 && tablero[i-2][j+1]!=8 && tablero[i-2][j+1]!=9 && tablero[i-2][j+1]!=-1){
                bandera = false;
            }else if(tablero[i][j]==1 && tablero[i-3][j+1]!=0 && tablero[i-3][j+1]!=7 && tablero[i-3][j+1]!=8 && tablero[i-3][j+1]!=9 && tablero[i-3][j+1]!=-1){
                bandera = false;
            }
        }
        return bandera;
    }

    /**
     * metodo que revisa si la ficha actual llego al final del tablero
     * @return tablero con la ficha cambiada su representacion
     */
    public int[][] llegoAlFinal(){
        int temp = 5;
        if(tipo == 1){
            temp = -5;
        }
        for (int i = 0; i < 10; i++) {
            if (tablero[19][i] == 1 && estado == 1) {
                tablero[19][i] = temp;
                if(tipo == 3){
                    bomb(19,i,'f');
                }
            }
            else if(tablero[19][i] == 1 && estado == 2){
                tablero[19][i] = temp;
                tablero[18][i] = temp;
                tablero[17][i] = temp;
                tablero[16][i] = temp;
                if(tipo == 3){
                    bomb(19,i,'f');
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
    private void bomb(int i , int j, char f){
        if(estado == 1){
            if(f == 'f'){
                int a = j - 1;
                int b = j + 4;
                if(x_izq == 0){
                    a += 1;
                }
                if(x_der == 9){
                    b -= 1;
                }
                for(int x = i - 1; x< i + 1; x++){
                    for(int c = a; c < b + 1; c++){
                        if (tablero[x][c] != 0) {
                            tablero[x][c] = 0;
                            setPuntaje();
                        }
                    }
                }
            }
            else {
                int a = j - 4;
                int b = j + 1;
                if(x_izq == 0){
                    a += 1;
                }
                if(x_der == 9){
                    b -= 1;
                }
                for(int x = i - 1; x< i + 2; x++){
                    for(int y = a; y < b + 1; y++){
                        if (tablero[x][y] != 0) {
                            tablero[x][y] = 0;
                            setPuntaje();
                        }
                    }
                }
            }
        }
        if(estado == 2){
            int a = j - 1;
            int b = j + 1;
            int c = i + 1;
            if(x_izq == 0){
                a += 1;
            }
            if(x_der == 9){
                b -= 1;
            }if(y==20){
                c-=1;
            }
            for(int x = c - 4; x < c + 1; x++){
                for(int y = a; y < b + 1; y++){
                    if (tablero[x][y] != 0) {
                        tablero[x][y] = 0;
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
