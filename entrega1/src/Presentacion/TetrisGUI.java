package Presentacion;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;

import Dominio.*;

public class TetrisGUI extends JFrame {

    private static final int ancho = Toolkit.getDefaultToolkit().getScreenSize().width/2;
    private static final int alto = Toolkit.getDefaultToolkit().getScreenSize().height/2;
    private JPanel base;
    private Seleccion seleccion;
    private JPanel imagenI;
    private JPanel botones;
    private JPanel panelpuntajes;
    private JPanel panelinstrucciones;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem abrir;
    private JMenuItem guardarComo;
    private JFileChooser fileChooser;

    private JButton nuevoJuego;
    private JButton tablaDePuntaje;
    private JButton salirJuego;
    private JButton instrucciones;
    private JButton volver;

    /**
     * constructor de la clase
     */
    private TetrisGUI(){
        this.setTitle("Tetris");
        prepareElementos();
        prepareAcciones();
    }

    /**
     * metodo main
     * @param arcs viene por defecto
     */
    public static void main(String [] arcs){
        TetrisGUI gui = new TetrisGUI();
        gui.setVisible(true);
    }

    /**
     * prepara elementos de la clase
     */
    private void prepareElementos(){
        Image logo = Toolkit.getDefaultToolkit().getImage("logo.png");
        setIconImage(logo);
        setSize(ancho+20,alto+20);
        setLocationRelativeTo(null);
        prepareElementosMenu();
        prepareElementosFondo();
    }

    /**
     * prepara los elementos del menu
     */
    private void prepareElementosMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        guardarComo=new JMenuItem("Guardar Como");
        abrir=new JMenuItem("Abrir");
        menu.add(abrir);menu.add(guardarComo);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    /**
     * prepara los elementos del fondo
     */
    private void prepareElementosFondo() {
        base = new JPanel();
        base.setLayout(new GridLayout(2,1));
        ImageIcon imagen1 = new ImageIcon("imagen1P.jpg");
        imagenI = new JPanel();
        imagenI.setBackground(Color.BLACK);
        JLabel label1 = new JLabel(null,imagen1,SwingConstants.CENTER);
        imagenI.add (label1);
        botones = new JPanel();
        botones.setBackground(Color.BLACK);
        botones.setLayout(null);
        nuevoJuego = new JButton("Nuevo Juego");
        tablaDePuntaje = new JButton("Tabla de Puntaje");
        instrucciones = new JButton("Instrucciones");
        salirJuego = new JButton("Salir");
        nuevoJuego.setBounds((ancho/2)-75,10,150,30);
        tablaDePuntaje.setBounds((ancho/2)-75,50,150,30);
        instrucciones.setBounds((ancho/2)-75,90,150,30);
        salirJuego.setBounds((ancho/2)-75,130,150,30);
        botones.add(nuevoJuego);botones.add(tablaDePuntaje);botones.add(instrucciones);botones.add(salirJuego);
        base.add(imagenI);base.add(botones);
        add(base);

    }

    /**
     * prepara acciones de la clase
     */
    private void prepareAcciones(){
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                salga();
            }
        });
        abrir.addActionListener(e->abrir());
        guardarComo.addActionListener(e->guardarComo());
        nuevoJuego.addActionListener(e-> {
            try {
                nuevoJuego(null);
            } catch (TetrisException | FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        instrucciones.addActionListener(e->instrucciones());
        tablaDePuntaje.addActionListener(e->records());
        salirJuego.addActionListener(e->salga());
        //this.addKeyListener(this);
    }

    /**
     * abre un archivo con una partida
     */
    private void abrir(){
        try {
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Clases de Java (.txt)", "txt");
            fileChooser.setDialogTitle("Abrir");
            fileChooser.setFileFilter(filter);

            int accion = fileChooser.showOpenDialog(this);
            if (accion == JFileChooser.APPROVE_OPTION) {
                nuevoJuego(fileChooser.getSelectedFile());
            }
        }catch (TetrisException | FileNotFoundException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * guarda un archivo con una partida
     */
    private void guardarComo(){
        try {
            fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto (.txt)", "txt");
            fileChooser.setDialogTitle("Guardar");
            fileChooser.setFileFilter(filter);

            int accion = fileChooser.showDialog(this, "Guardar");
            if (accion == JFileChooser.APPROVE_OPTION) {
                seleccion.guardar(fileChooser.getSelectedFile());

            }
        }catch (TetrisException e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * cierra el juego, JFrame
     */
    private void salga(){
        if (JOptionPane.showConfirmDialog(rootPane, "Desea salir del juego?",
                "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * llama a la clase seleccion
     * @param file archivo a leer de ser necesario
     * @throws TetrisException se lanza de ser necesario
     * @throws FileNotFoundException excepcion del paquete IO
     */
    private void nuevoJuego(File file) throws TetrisException, FileNotFoundException {
        seleccion = new Seleccion(base,file);
        base.setVisible(false);
        add(seleccion);
    }

    /**
     * metodo para el boton de volver en el panel de puntos
     */
    private void volverpun(){
        panelpuntajes.setVisible(false);
        base.setVisible(true);
    }

    /**
     * metodo para el boton de volver en el panel de instrucciones
     */
    private void volverins(){
        panelinstrucciones.setVisible(false);
        base.setVisible(true);
    }

    /**
     * metodo que lee el archivo de los records
     */
    private void records(){
        panelpuntajes = new JPanel();
        panelpuntajes.setLayout(new GridLayout(2,1));
        ImageIcon imagen1 = new ImageIcon("imagen1P.jpg");
        imagenI = new JPanel();
        imagenI.setBackground(Color.BLACK);
        JLabel label1 = new JLabel(null,imagen1,SwingConstants.CENTER);
        imagenI.add (label1);
        JPanel puntajes = new JPanel();
        puntajes.setLayout(new FlowLayout(FlowLayout.LEFT,(ancho/2)-50,5));
        puntajes.setBackground(Color.BLACK);
        volver = new JButton("Volver");
        volver.setBounds(20,alto,150,30);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Records.txt"));
            String line = bufferedReader.readLine();
            while (line != null) {
                JLabel labelpuntaje = new JLabel(line);
                labelpuntaje.setForeground(Color.WHITE);
                puntajes.add(labelpuntaje);
                line = bufferedReader.readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        puntajes.add(volver);
        volver.addActionListener(e->volverpun());
        base.setVisible(false);
        panelpuntajes.add(imagenI);panelpuntajes.add(puntajes);
        add(panelpuntajes);
    }

    /**
     * panel de las instruccions del juego
     */
    private void instrucciones(){
        panelinstrucciones = new JPanel();
        panelinstrucciones.setLayout(new GridLayout(2,1));
        ImageIcon imagen1 = new ImageIcon("imagen1P.jpg");
        imagenI = new JPanel();
        imagenI.setBackground(Color.BLACK);
        JLabel label1 = new JLabel(null,imagen1,SwingConstants.CENTER);
        imagenI.add (label1);
        JPanel instruccion = new JPanel();
        instruccion.setLayout(new FlowLayout(FlowLayout.LEFT,18,3));
        instruccion.setBackground(Color.BLACK);
        volver = new JButton("Volver");
        JLabel label1ins = new JLabel("Para el primer Jugador los controles son rotar(w), mover derecha(d), mover izquierda(a), bajar (s) y activar buffo(.)");
        JLabel label2ins = new JLabel("Para el segundo Jugador los controles son rotar(flecha arriba), mover derecha(flecha derecha), mover izquierda");
        JLabel label3ins = new JLabel("(flecha izquierda), bajar (flecha abajo) y activar buffo(0).");
        JLabel label4ins = new JLabel("Hay 3 tipos de fichas Useless(Borde Gris,no dejan eliminar una fila),Normal(Sin borde,representa la ficha de siempre)");
        JLabel label5ins = new JLabel("por ultimo el bomb(Borde Rojo, se autodestruye y explota el alrededor). Tambien hay 4 buffos los cuales son");
        JLabel label6ins = new JLabel("Stop Time(Amarillo, detiene el tiempo por 3 segundos), Slow(Verde, la ficha baja lento por 3 segundos)");
        JLabel label7ins = new JLabel("Stop Diece(Morado, detiene el tiempo hasta forzar bajar la ficha), 2x(Naranja, la ficha baja rapido por 3 segundos)");
        label1ins.setForeground(Color.WHITE);
        label2ins.setForeground(Color.WHITE);
        label3ins.setForeground(Color.WHITE);
        label4ins.setForeground(Color.WHITE);
        label5ins.setForeground(Color.WHITE);
        label6ins.setForeground(Color.WHITE);
        label7ins.setForeground(Color.WHITE);
        instruccion.add(label1ins);instruccion.add(label2ins);instruccion.add(label3ins);instruccion.add(label4ins);instruccion.add(label5ins);instruccion.add(label6ins);instruccion.add(label7ins);
        instruccion.add(volver);
        volver.addActionListener(e->volverins());
        base.setVisible(false);
        panelinstrucciones.add(imagenI);panelinstrucciones.add(instruccion);
        add(panelinstrucciones);
    }


}
