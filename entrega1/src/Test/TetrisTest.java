package Test;

import Dominio.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TetrisTest {
    Tetris tetris;
    Tetris tetris2;

    @BeforeEach
    public void setUp() {
        tetris = new Tetris();
    }

    @Test
    public void deberiaAgregar() {
        tetris = new Tetris();
        tetris.sacarFicha();
        tetris.crearBuffo();
        assertNotNull(tetris.tableroLog());
    }

    @Test
    public void deberiaRevisarBuffo(){
        tetris = new Tetris();
        assertTrue(tetris.revisarBufo());
        tetris.crearBuffo();
        while(tetris.revisarBufo()){
            tetris.crearBuffo();
        }
        assertFalse(tetris.revisarBufo());
    }

    @Test
    public void deberiaActivarBuffo(){
        tetris = new Tetris();
        tetris.crearBuffo();
        while(tetris.revisarBufo()){
            tetris.crearBuffo();
        }
        int bufo = tetris.activarBuffo();
        assertNotEquals(0,bufo);
    }

    @Test
    public void deberiaBorrarLinea(){
        tetris = new Tetris();
        tetris2 = new Tetris();
        int[][] tablero1 = tetris.tableroLog();
        for (int i = 0; i<20;i++){
            for (int j = 0;j<10;j++){
                if(i == 13){
                    tablero1[i][j]=1;
                }else{
                    tablero1[i][j]=0;
                }
            }
        }
        tetris.setTablero(tablero1);
        tetris.borrarLinea(13);
        for(int i = 0;i<10;i++) {
            assertEquals(tetris.tableroLog()[13][i], tetris2.tableroLog()[13][i]);
        }
    }

    @Test
    public void deberiaRevisarHaciaAbajo(){
        tetris = new Tetris();
        tetris2 = new Tetris();
        int[][] tablero1 = tetris.tableroLog();
        for (int i = 0; i<20;i++){
            for (int j = 0;j<10;j++){
                if(i == 13 && j == 0){
                    tablero1[i][j]=2;
                }else if(i == 14){
                    tablero1[i][j]=2;
                } else{
                    tablero1[i][j]=0;
                }
            }
        }
        tetris.setTablero(tablero1);
        tetris.revisarHaciaAbajo();
        for(int i = 0;i<10;i++){
            assertEquals(tetris.tableroLog()[13][i], tetris2.tableroLog()[13][i]);

        }
        assertEquals(2,tetris.tableroLog()[14][0]);
        assertEquals("10", tetris.getPuntaje());
    }

    @Test
    public void deberiaMoverLinea(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.CYAN){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        tetris.movimiento("s");
        assertEquals(1, tetris.tableroLog()[1][3]);
    }

    @Test
    public void deberiaMoverCuadrado(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.YELLOW){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        tetris.movimiento("s");
        assertEquals(1, tetris.tableroLog()[1][4]);
        assertEquals(1, tetris.tableroLog()[2][4]);
        assertEquals(1, tetris.tableroLog()[1][5]);
        assertEquals(1, tetris.tableroLog()[2][5]);
    }

    @Test
    public void deberiaMoverEle(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.ORANGE){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        tetris.movimiento("s");
        assertEquals(1, tetris.tableroLog()[3][4]);
    }

    @Test
    public void deberiaMoverEse(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.GREEN){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        tetris.movimiento("s");
        assertEquals(1, tetris.tableroLog()[1][4]);
        assertEquals(1, tetris.tableroLog()[2][4]);
        assertEquals(1, tetris.tableroLog()[2][5]);
        assertEquals(1, tetris.tableroLog()[3][5]);
    }

    @Test
    public void deberiaMoverTe(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.MAGENTA){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        tetris.movimiento("s");
        assertEquals(1, tetris.tableroLog()[1][4]);
        assertEquals(1, tetris.tableroLog()[1][5]);
        assertEquals(1, tetris.tableroLog()[1][6]);
        assertEquals(1, tetris.tableroLog()[2][5]);
    }
}

