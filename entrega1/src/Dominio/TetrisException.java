package Dominio;

public class TetrisException extends Exception{
    public static final String IMPORTAR = "No se pudo importar el archivo";
    public static final String EXPORTAR = "No se pudo exportar el archivo";
    public static final String ENTRADA_SALIDA = "Ha ocurrido un error al cargar el archivo";

    /**
     * Constructor de tetris Exception
     * @param msg mensaje a lanzar
     */
    public TetrisException(String msg){
        super(msg);
    }
}
