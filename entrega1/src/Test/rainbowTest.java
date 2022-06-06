package Test;
import Dominio.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class rainbowTest {
    Tetris tetris;

    @Test
    public void deberiaAgregar(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.RED){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        assertEquals(Color.RED, tetris.getFicha());
    }

    @Test
    public void deberiaCambiarColor(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.RED){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        tetris.movimiento("a");
        assertEquals(Color.ORANGE, tetris.getFicha());
        tetris.movimiento("d");
        assertEquals(Color.YELLOW, tetris.getFicha());
    }

    @Test
    public void deberiaSumarAlVolverARojo(){
        tetris = new Tetris();
        tetris.sacarFicha();
        while(tetris.getFicha() != Color.RED){
            tetris = new Tetris();
            tetris.sacarFicha();
        }
        tetris.movimiento("a");
        tetris.movimiento("d");
        tetris.movimiento("a");
        tetris.movimiento("d");
        tetris.movimiento("a");
        tetris.movimiento("d");
        tetris.movimiento("a");
        assertEquals(Color.RED, tetris.getFicha());
        assertEquals("10", tetris.getPuntaje());
    }
}
