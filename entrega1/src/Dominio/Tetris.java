package Dominio;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Tetris {

    private static int TipoFicha;
    private boolean finalT = false;
    private boolean primerMov = true;
    private int[][] tablero;
    private fichas ficha;
    private String[] dispoibles = {"linea", "cuadrado", "ele", "ese", "te", "rainbow"};
    private String[] bufos = {"ST", "SD", "Slow", "2x"};
    public String puntaje = "0";
    private Buffo buffo;
    private Boolean usarBufo = false;
    private Boolean tengoBufo = false;
    private String nickname = "";
    private int cantBufo = 4;

    /**
     * Constructor del tetris, crea una matriz que corresponde al juego
     */
    public Tetris(){
        tablero = new int[20][10];
        for(int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = 0;
            }
        }
    }

    /**
     * saca un numero random entre 0 y 5
     * @return numero aleatorio
     */
    public static int numeroAleatorioEnRango() {
        TipoFicha = ThreadLocalRandom.current().nextInt(0, 5);
        return TipoFicha;
    }

    /**
     * saca un numero random entre 1 y 4
     * @return numero aleatorio
     */
    public static int numeroAleatorioTipo() {
        TipoFicha = ThreadLocalRandom.current().nextInt(1,4);
        return TipoFicha;
    }

    /**
     * genera una ficha aleatoria y la agrega al tablero
     */
    public void sacarFicha(){
        int tipo = numeroAleatorioTipo();
        String fichaaleatoria = dispoibles[numeroAleatorioEnRango()];
        if(Objects.equals(fichaaleatoria, "cuadrado")){
            tablero[0][4] = 1;
            tablero[0][5] = 1;
            tablero[1][4] = 1;
            tablero[1][5] = 1;
            ficha = new cuadrado(tablero, 4, 5, 2,this,tipo);
        }
        else if(Objects.equals(fichaaleatoria, "linea")) {
            tablero[0][3] = 1;
            tablero[0][4] = 1;
            tablero[0][5] = 1;
            tablero[0][6] = 1;
            ficha = new linea(tablero, 3, 6, 1,this,tipo);
        }
        else if(Objects.equals(fichaaleatoria, "ele")) {
            tablero[0][4] = 1;
            tablero[1][4] = 1;
            tablero[2][4] = 1;
            tablero[2][5] = 1;
            ficha = new ele(tablero, 4, 5, 3,this,tipo);
        }
        else if(Objects.equals(fichaaleatoria, "ese")) {
            tablero[0][4] = 1;
            tablero[1][4] = 1;
            tablero[1][5] = 1;
            tablero[2][5] = 1;
            ficha = new ese(tablero, 4, 5, 3,this,tipo);
        }
        else if(Objects.equals(fichaaleatoria, "te")) {
            tablero[0][4] = 1;
            tablero[0][5] = 1;
            tablero[0][6] = 1;
            tablero[1][5] = 1;
            ficha = new te(tablero, 4, 6, 2,this,tipo);
        }
        else if(Objects.equals(fichaaleatoria, "rainbow")){
            tablero[0][4] = 1;
            tablero[0][5] = 1;
            tablero[1][3] = 1;
            tablero[1][6] = 1;
            ficha = new rainbow(tablero, 3, 6, 2,this,2);
        }
//        imprimir();
    }

    /**
     * saca un numero random entre 0 y la cantidad de buffos seleccionados por el usuario
     * @return numero aleatorio
     */
    public int numeroAleatorioBuffo() {

        return ThreadLocalRandom.current().nextInt(0, cantBufo);
    }

    /**
     * metodo que agrega los buffos seleccionados por el usuario
     * @param bufosDisp buffos seleccionados
     */
    public void setBufos(String[] bufosDisp){
        bufos = bufosDisp;
    }

    /**
     * metodo que determina la cantidad de buffos de la partida
     * @param cantBufos numero de buffos
     */
    public void setCantBufo(int cantBufos){
        cantBufo = cantBufos;
    }

    /**
     * genera un buffo aleatorio
     */
    public void crearBuffo(){
        if(!usarBufo && revisarBufo() && !tengoBufo){
            Random random = new Random();
            if(random.nextBoolean()) {
                String tipo = bufos[numeroAleatorioBuffo()];
                if (Objects.equals(tipo, "ST")) {
                    buffo = new StopTime(tablero);
                } else if (Objects.equals(tipo, "SD")) {
                    buffo = new StopDiece(tablero);
                } else if (Objects.equals(tipo, "Slow")) {
                    buffo = new Slow(tablero);
                } else if (Objects.equals(tipo, "2x")) {
                    buffo = new x2(tablero);
                }
                buffo.revisar(buffo);
            }
        }
    }

    /**
     * revisa que no haya un buffo en juego
     * @return booleano que determina dicha condicion
     */
    public boolean revisarBufo(){
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if(tablero[i][j]>=7 || tablero[i][j] == -1){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * metodo que activa un buffo
     * @return entero que representa el buffo activado
     */
    public int activarBuffo(){
        return buffo.activarBuffo();
    }

    /**
     * metodo que revisa la condicion de derrota del jeugo
     * @return booleano que indica si la partida esta perdida
     */
    public boolean perder(){
        boolean perdida = true;
        for(int i = 0; i < 10; i++){
            if(tablero[0][i] == 2||tablero[0][i] == 3||tablero[0][i] == 4||tablero[0][i] == 5||tablero[0][i] == 6 || tablero[0][i] == -2) {
                perdida = false;
            }
        }
        return perdida;
    }

    /**
     * metodo que en base a un string hace que las piezas se muevan
     * @param tecla string que indica el movimiento deseado por el usuario
     */
    public void movimiento(String tecla){
        revisarTablero();
        if(Objects.equals(tecla, "s")){
            if(ficha.y == 20){
                tablero = ficha.llegoAlFinal();
                finalT = true;
                //borrarLineas();
            }else if(ficha.y < 20){
                tablero = ficha.aba();
            }
        }else if(Objects.equals(tecla, "a")){
            if(ficha.x_izq > 0){
                tablero = ficha.izq();
                crearBuffo();
            }
        }else if(Objects.equals(tecla, "d")){
            if(ficha.x_der < 9){
                tablero = ficha.der();
                crearBuffo();
            }
        }else if(Objects.equals(tecla, "w")){
            tablero = ficha.rotar();
            crearBuffo();
        }
    }

//    public void imprimir(){
//        System.out.println();
//        for(int i = 0; i < 20; i++){
//            for(int j = 0; j<10; j++){
//                System.out.print(tablero[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }

    /**
     * metodo que se conecta con la capa de presentacion, esta permite que se generen nuevas fichas y que se baje
     * automaticamente la misma
     * @param tecla string que determina si es necesario sacar una nueva ficha o si se debe bajar automaticamente
     */
    public void jugar(String tecla){
        int lineasBorrardas = 0;
        if (Objects.equals(tecla, "s") && primerMov){
            primerMov = false;
            sacarFicha();
        }else if(Objects.equals(tecla, "s")){
            movimiento(tecla);
        }else if(Objects.equals(tecla, "f")){
            sacarFicha();
        }
        revisarHaciaAbajo();
    }

    /**
     * metodo que borra una linea
     * @param fila fila de la matriz que va a ser borrada
     */
    public void borrarLinea(int fila){
        for(int i = 0; i< 10; i++){
            tablero[fila][i] = 0;
        }
    }

    /**
     * metodo que revisa si alguna fila a sido completada
     */
    public void revisarHaciaAbajo(){
        int cont;
        for (int i = 0; i < 20; i++){
            cont = 0;
            for(int j = 0; j< 10; j++){
                if(tablero[i][j] !=0 && tablero[i][j] != 1 && tablero[i][j] !=-2 && tablero[i][j] !=-3 && tablero[i][j] !=-4 && tablero[i][j] !=-5 && tablero[i][j] !=-6){
                    cont += 1;
                }
            }
            if(cont == 10){
                borrarLinea(i);
                bajarLineas(i);
            }
        }
    }

    /**
     * metodo que baja las filas superiores a una fila borrada
     * @param filaBorrada fila de la matriz que a sido borrada
     */
    public void bajarLineas(int filaBorrada){
        for (int i = filaBorrada - 1; i > 0; i-=1) {
            for (int j = 0; j < 10; j += 1) {
                if(tablero[i][j] !=0 && tablero[i][j] != 1 && tablero[i][j] != 7 && tablero[i][j] != 8 && tablero[i][j] != 9 && tablero[i][j] != -1){
                    int a = tablero[i][j];
                    tablero[i][j] = 0;
                    tablero[i + 1][j] = a;
                }
            }
        }
        int puntajes = Integer.parseInt(puntaje);
        puntajes += 10;
        puntaje = String.valueOf(puntajes);
    }

    /**
     * metodo que revisa si aun hay una ficha en juego
     */
    private void revisarTablero(){
        int cont = 0;
        for (int i = 0;i<20;i++){
            for (int j = 0;j<10;j++){
                if (tablero[i][j]==1){
                    cont ++;
                }
            }
        }if(cont == 0){
            finalT = true;
        }
    }

    /**
     * metodo que en base a un archivo escribe la matriz del juego
     * @param file archivo a leer
     * @return matriz construida
     * @throws TetrisException se lanza al cargar mal el archivo
     */
    public int[][] crearTablero(File file) throws TetrisException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            ArrayList<String> lines = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
            return construirTablero(lines);
        }catch (Exception e){
            throw new TetrisException(TetrisException.IMPORTAR);
        }
    }

    /**
     * metodo que recibe las lineas del metodo de arriba y construye la matriz
     * @param lines linea de la matriz
     * @return matriz construida
     */
    private int[][] construirTablero(ArrayList<String> lines){
        tablero = new int[20][10];
        int cont = 0;
        for(String line:lines){
            String[] splittedLine;
            splittedLine = line.trim().split(" ");
            if(cont < 20) {
                for (int i = 0; i < 10; i++) {
                    tablero[cont][i] = Integer.parseInt(splittedLine[i]);
                }
                cont++;
            }else if (cont == 20){
                int longi = splittedLine.length;
                setPuntaje(Integer.parseInt(splittedLine[1]));
                for(int i = 2;i<longi;i++) {
                    nickname += splittedLine[i] + " ";
                }
                cont++;
            }
        }

        return tablero;
    }

    /**
     * metodo que en base a un archivo escribe la matriz del juego para el segundo jugador
     * @param file archivo a leer
     * @return matriz construida
     * @throws TetrisException se lanza al cargar mal el archivo
     */
    public int[][] crearTablero2(File file) throws TetrisException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            ArrayList<String> lines = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
            return construirTablero2(lines);
        }catch (Exception e){
            throw new TetrisException(TetrisException.IMPORTAR);
        }
    }

    /**
     * metodo que recibe las lineas del metodo de arriba y construye la matriz para el segundo jugador
     * @param lines linea de la matriz
     * @return matriz construida
     * @throws Exception se lanza al cargar mal el archivo
     */
    private int[][] construirTablero2(ArrayList<String> lines) throws Exception {
        tablero = new int[20][10];
        int cont = 0;
        for(String line:lines){
            String[] splittedLine;
            splittedLine = line.trim().split(" ");
            if(cont > 20 && cont < 41) {
                for (int i = 0; i < 10; i++) {
                    tablero[cont-21][i] = Integer.parseInt(splittedLine[i]);
                }
                cont++;
            }else if (cont == 41){
                int longi = splittedLine.length;
                setPuntaje(Integer.parseInt(splittedLine[1]));
                for(int i = 2;i<longi;i++) {
                    nickname += splittedLine[i] + " ";
                }
                cont++;
            }else{
                cont++;
            }
        }
        return tablero;
    }

    /**
     * metodo del que se obtiene el tablero del juego
     * @return tablero
     */
    public int[][] tableroLog(){
        return tablero;
    }

    /**
     * metodo que indica si una ficha ya llego al final
     * @return ooleano que representa si una ficha ya llego al final
     */
    public boolean getFinalT(){
        return finalT;
    }

    /**
     * metodo que cambia el valor de si una ficha ya llego al final
     */
    public void setFinalT(boolean valor){
        finalT = valor;
    }

    /**
     * metodo que retorna el color de la ficha
     * @return color
     */
    public Color getFicha(){
        return ficha.getColor();
    }

    /**
     * metodo que retorna el puntaje de la partida
     * @return string del puntaje
     */
    public String getPuntaje(){
        return puntaje;
    }

    /**
     * metodo que dice si un buffo esta en uso
     * @param b booleano
     */
    public void setUsarBufo(boolean b){
        usarBufo = b;
        tengoBufo = b;
    }

    /**
     * metodo que revisa si el jugador tiene un buffo sin activar
     * @return booleano que confirma si se posee o no
     */
    public boolean getTengoBufo(){
        return tengoBufo;
    }

    /**
     * metodo que cambia setea el tener un buffo
     * @param b booleano
     */
    public void setTengoBufo(boolean b){
        tengoBufo = b;
    }

    /**
     * metodo que retorna el tipo de una ficha
     * @return entero que representa el tipo de una ficha
     */
    public int getTipo(){
        return ficha.getTipo();
    }

    /**
     * metodo para guardar un juego
     * @param file archivo donde se va a escribir
     * @param jugador nombre deljugador
     * @throws TetrisException se lanza al guardar mal el archivo
     */
    public void guardar(File file,String jugador) throws TetrisException{
        try{
            FileWriter writer = new FileWriter(file);
            writer.write(escribirTetris(jugador));
            writer.flush();
            writer.close();
        }catch (IOException e){
            throw new TetrisException(TetrisException.ENTRADA_SALIDA);
        }catch (Exception e) {
            throw new TetrisException(TetrisException.EXPORTAR);
        }
    }

    /**
     * metodo que transforma el tablero en un string
     * @param jugador nombre del jugador
     * @return matriz convertida en string
     */
    private String escribirTetris(String jugador) {
        StringBuilder matriz = new StringBuilder();
        for(int i  = 0;i<20;i++){
            for(int j = 0;j<10;j++){
                if(tablero[i][j]!=1 && tablero[i][j]!=7 && tablero[i][j]!=8 && tablero[i][j]!=9 && tablero[i][j]!=-1) {
                    matriz.append(tablero[i][j]).append(" ");
                }else{
                    matriz.append("0").append(" ");
                }
            }
            matriz.append("\n");
        }
        matriz.append("puntaje").append(" ").append(puntaje).append(" ").append(jugador);
        return matriz.toString();
    }

    /**
     * metodo que escribe el tablero en un archivo
     * @param file archivo a escribir
     * @param jugador nombre del jugador
     * @throws TetrisException se lanza al guardar mal el archivo
     */
    public void sobreescribir(File file,String jugador) throws TetrisException{
        try{
            FileWriter writer = new FileWriter(file,true);
            PrintWriter printer = new PrintWriter(writer);
            printer.append("\n");
            printer.append(escribirTetris(jugador));
            printer.close();
            writer.close();
        }catch (IOException e){
            throw new TetrisException(TetrisException.ENTRADA_SALIDA);
        }catch (Exception e) {
            throw new TetrisException(TetrisException.EXPORTAR);
        }
    }

    /**
     * metodo que escribe el tablero en un archivo, para el segundo tablero
     * @param file archivo a escribir
     * @param jugador nombre del jugador
     * @throws TetrisException se lanza al guardar mal el archivo
     */
    public void sobreescribir2(File file,String jugador) throws TetrisException{
        try{
            FileWriter writer = new FileWriter(file,true);
            PrintWriter printer = new PrintWriter(writer);
            printer.append("\n");
            printer.append(escribirTetris(jugador));
            printer.append("\n");
            printer.append("pc");
            printer.close();
            writer.close();
        }catch (IOException e){
            throw new TetrisException(TetrisException.ENTRADA_SALIDA);
        }catch (Exception e) {
            throw new TetrisException(TetrisException.EXPORTAR);
        }
    }

    /**
     * setea el puntaje
     * @param punto puntos
     */
    public void setPuntaje(int punto){
        int puntajes = Integer.parseInt(puntaje);
        puntajes += punto;
        puntaje = String.valueOf(puntajes);
    }

    /**
     * nombre del jugador
     * @return nombre
     */
    public String getNickname(){
        return nickname;
    }

    /**
     * agrega un record
     * @param ruta ubicacion del archivo de records
     * @param nickname nombre del jugador que marco dicho puntaje
     */
    public void records(String ruta,String nickname){
        try {
            FileWriter escribir = new FileWriter(ruta,true);
            PrintWriter linea = new PrintWriter(escribir);
            linea.append(nickname).append(": ").append(puntaje).append("\n");
            linea.close();
            escribir.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * setea el tablero
     * @param tableromod nuevo tablero
     */
    public void setTablero(int[][] tableromod){
        tablero = tableromod;
    }
}
