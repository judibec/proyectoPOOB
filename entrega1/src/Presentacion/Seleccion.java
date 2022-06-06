package Presentacion;
import Dominio.*;

import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Objects;


public class Seleccion extends JPanel implements KeyListener, ItemListener {
    private static final int ancho = Toolkit.getDefaultToolkit().getScreenSize().width/2;
    private static final int alto = Toolkit.getDefaultToolkit().getScreenSize().height/2;
    private JButton solo;
    private JButton unovsuno;
    private JButton unovspc;
    private JButton volver;
    private JButton colorBack = new JButton("Color del Tablero");
    private JButton aceptar = new JButton("Aceptar");
    private JButton salirJuego = new JButton("Salir");
    private JButton pausa = new JButton("Pausar");

    private JPanel selecciones;
    private JPanel imagenI;
    private JPanel botones;
    private JPanel menu;
    private JPanel BaseOp;
    private JPanel CreaOp;
    private JPanel juego;
    private JPanel vacio;
    private JPanel puntajes;
    private JPanel relleno;
    private JPanel tablero;
    private JPanel tablero2;
    private JPanel cuadrito;
    private JPanel[][] cuadrados;
    private JPanel cuadrito2;
    private JPanel[][] cuadrados2;

    private JComboBox<String> velocidades;
    private JComboBox<String> BufosUsar;

    private JTextField unjugador;
    private JTextField segjugador;

    private JLabel labelJ1;
    private JLabel labelJ2;
    private JLabel scorePu;
    private JLabel scorePu2;
    private JLabel jug1;
    private JLabel jug2;
    private JLabel scoreTi;
    private JLabel scoreTi2;

    private Color colorJuego = Color.BLACK;
    private Color colorBorde = Color.BLUE;
    private Color ficha;
    private Color ficha2;

    private JColorChooser colorChooser;

    private String jugador1;
    private String jugador2;
    private String TipoVel;
    private String TipoBufo;
    private String[] bufoDisp;

    private Timer tiempo;
    private Timer tiempo2;
    private Timer tiempoJuego;
    private Timer tiempoJuego2;
    private Timer tiempoBufos;
    private Timer tiempoBufos2;

    private Tetris juegoLogico;
    private Tetris juegoLogico2;

    private int ConsTablero;
    private int[][] tableroLog;
    private int[][] tableroLog2;
    private int velo;
    private int cantBufo = 4;
    private int dela = 500;
    private int dela2 = 500;
    private int bufoActivado=0;
    private int bufoActivado2=0;

    private boolean musica = false;
    private boolean ra = false;
    private Clip clip;
    private Border borde;
    private Border borde2;

    private Pc pc;

    /**
     * constructor de seleccion
     * @param menu panel inicial
     * @param file archivo a cargar o abrir (de ser necesario)
     * @throws TetrisException se lanza al guardar o cargar mal el archivo
     * @throws FileNotFoundException excepcion del paquete IO
     */
    public Seleccion(JPanel menu,File file) throws TetrisException, FileNotFoundException {
        this.menu = menu;
        prepareElementos();
        prepareAcciones();
        tiempo = new Timer(dela, this::prueba);
        tiempo2 = new Timer(dela2, this::prueba2);
        tiempoBufos = new Timer(3000, this::reiniciarTiempo);
        tiempoBufos2 = new Timer(3000, this::reiniciarTiempo2);
        if(file != null){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            int cantLineas = (int) bufferedReader.lines().count();
            if(cantLineas <= 21) {
                jueguito();
                add(relleno, BorderLayout.NORTH);
                add(juego, BorderLayout.CENTER);
                juego.setVisible(true);
                empezarJuego(file);
            }else if (cantLineas==42){
                jueguito2();
                add(relleno, BorderLayout.NORTH);
                add(juego, BorderLayout.CENTER);
                juego.setVisible(true);
                empezarJuego2(file);
            }else{
                jueguito2();
                add(relleno, BorderLayout.NORTH);
                add(juego, BorderLayout.CENTER);
                juego.setVisible(true);
                empezarJuego3(file);
            }
        }else {
            add(selecciones);
        }
    }

    /**
     * prepara las acciones del panel
     */
    private void prepareAcciones() {
       volver.addActionListener(e->volver());
       solo.addActionListener(e->solo());
       unovsuno.addActionListener(e->unovsuno());
       unovspc.addActionListener(e->unovspc());
       colorBack.addActionListener(e->colorT());
       aceptar.addActionListener(e-> {
           try {
               aceptar();
           } catch (TetrisException ex) {
               ex.printStackTrace();
           }
       });
       salirJuego.addActionListener(e->volver());
       pausa.addActionListener(e->pausa());
       this.addKeyListener(this);
    }

    /**
     * metodo para los botones de volver o salir
     */
    private void volver(){
        if (JOptionPane.showConfirmDialog(null, "Se devolvera al Menu Principal, Desea Regresar?",
                "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            setVisible(false);
            if(velo == 2){
                tiempoJuego.stop();
            }if (musica){
                clip.stop();
            }
            tiempo.stop();
            if(ConsTablero == 2 || ConsTablero == 3){
                tiempo2.stop();
            }
            menu.setVisible(true);
        }
    }

    /**
     * acciones para el boton de juego individual
     */
    private void solo(){
        ConsTablero = 1;
        selecciones.setVisible(false);
        CreaOpciones();
        this.add(BaseOp);
        BaseOp.setVisible(true);
    }

    /**
     * acciones para el boton de juego 1 vs 1
     */
    private void unovsuno(){
        ConsTablero = 2;
        selecciones.setVisible(false);
        CreaOpciones();
        this.add(BaseOp);
        BaseOp.setVisible(true);
    }

    /**
     * acciones para el boton de juego 1 vs Pc
     */
    private void unovspc(){
        ConsTablero = 3;
        selecciones.setVisible(false);
        CreaOpciones();
        this.add(BaseOp);
        BaseOp.setVisible(true);
    }

    /**
     * acciones para el boton aceptar que inicia el juego
     * @throws TetrisException Se lanza excepcion de ser necesaria
     */
    private void aceptar() throws TetrisException {
        BaseOp.setVisible(false);
        ingresoNickname();
        if (ConsTablero == 1) {
            jueguito();
            empezarJuego(null);
        }else{
            jueguito2();
            if(ConsTablero == 2) {
                empezarJuego2(null);
            }else if(ConsTablero == 3){
                empezarJuego3(null);
            }
        }
        add(relleno, BorderLayout.NORTH);
        add(juego, BorderLayout.CENTER);
        juego.setVisible(true);
    }

    /**
     * acciones para pausar el juego
     */
    private void pausa(){
        if (Objects.equals(pausa.getText(), "Pausar")){
            tiempo.stop();
            if (ConsTablero == 2 || ConsTablero == 3) {
                tiempo2.stop();
            }if(musica) {
                clip.stop();
            }
            pausa.setText("Reanudar");
        }else{
            tiempo.restart();
            if(ConsTablero == 2 || ConsTablero == 3){
                tiempo2.restart();
            }if(musica) {
                clip.start();
            }
            requestFocus();
            pausa.setText("Pausar");
        }
    }

    /**
     * metodo para cambiar el color del borde del tablero
     */
    private void colorT(){
        colorChooser = new JColorChooser();
        colorBorde = colorChooser.showDialog(this,"Elige un color",colorBorde);
    }

    /**
     * prepara elementos de la clase
     */
    private void prepareElementos() {
        selecciones = new JPanel();
        setFocusable(true);
        selecciones.setLayout(new GridLayout(2,1));
        ImageIcon imagen1 = new ImageIcon("imagen1P.jpg");
        imagenI = new JPanel();
        imagenI.setBackground(Color.BLACK);
        JLabel label1 = new JLabel(null,imagen1,SwingConstants.CENTER);
        imagenI.add (label1);
        botones = new JPanel();
        botones.setBackground(Color.BLACK);
        solo = new JButton("Individual");
        unovsuno = new JButton("Uno VS Uno");
        unovspc = new JButton("Uno VS PC");
        volver = new JButton("Volver");
        botones.setLayout(null);
        solo.setBounds((ancho/2)-70,10,150,30);
        unovsuno.setBounds((ancho/2)-70,50,150,30);
        unovspc.setBounds((ancho/2)-70,90,150,30);
        volver.setBounds((ancho/2)-70,130,150,30);
        botones.add(solo);botones.add(unovsuno);botones.add(unovspc);botones.add(volver);
        selecciones.add(imagenI);selecciones.add(botones);
//        add(selecciones);
    }

    /**
     * Genera el panel donde se seleccionan las opciones de la partida
     */
    private void CreaOpciones(){
        BaseOp = new JPanel();
        BaseOp.setLayout(new GridLayout(2,1));
        ImageIcon imagen1 = new ImageIcon("imagen1P.jpg");
        imagenI = new JPanel();
        imagenI.setBackground(Color.BLACK);
        JLabel label1 = new JLabel(null,imagen1,SwingConstants.CENTER);
        imagenI.add (label1);
        BaseOp.add(imagenI);
        CreaOp = new JPanel();
        CreaOp.setLayout(null);
        colorBack.setBounds((ancho/2)-70,80,150,30);
        volver.setBounds((ancho/2)-180,130,150,30);
        aceptar.setBounds((ancho/2)+50,130,150,30);
        if(ConsTablero == 1 || ConsTablero == 3){
            labelJ1 = new JLabel("Jugador 1");
            labelJ1.setForeground(Color.WHITE);
            labelJ1.setBounds((ancho/2)-70,5,100,30);
            unjugador = new JTextField();
            unjugador.setBounds((ancho/2)-70,30,150,30);
            velocidad();
            Bufos();
            CreaOp.add(labelJ1);CreaOp.add(unjugador);CreaOp.add(colorBack);CreaOp.add(volver);CreaOp.add(aceptar);CreaOp.add(velocidades);CreaOp.add(BufosUsar);
        }else if(ConsTablero == 2){
            labelJ1 = new JLabel("Jugador 1");
            labelJ1.setForeground(Color.WHITE);
            labelJ1.setBounds((ancho/2)-180,5,100,30);
            unjugador = new JTextField();
            unjugador.setBounds((ancho/2)-180,30,150,30);
            labelJ2 = new JLabel("Jugador 2");
            labelJ2.setForeground(Color.WHITE);
            labelJ2.setBounds((ancho/2)+50,5,100,30);
            segjugador = new JTextField();
            segjugador.setBounds((ancho/2)+50,30,150,30);
            velocidad();
            Bufos();
            CreaOp.add(labelJ1);CreaOp.add(unjugador);CreaOp.add(labelJ2);CreaOp.add(segjugador);CreaOp.add(colorBack);CreaOp.add(volver);CreaOp.add(aceptar);CreaOp.add(velocidades);CreaOp.add(BufosUsar);
        }
        CreaOp.setBackground(Color.BLACK);
        BaseOp.add(CreaOp);
        add(BaseOp);

    }

    /**
     * guarda los nicknames en base a la partida seleccionada
     */
    private void ingresoNickname(){
        if(ConsTablero == 1 || ConsTablero == 3){
            if (ConsTablero == 3){
                jugador2 = "CPU";
            }
            if (unjugador.getText().length() == 0){
                jugador1 = "Jugador 1";
            }else{
                jugador1 = unjugador.getText();
            }
        }else if(ConsTablero == 2){
            if (unjugador.getText().length() == 0){
                jugador1 = "Jugador 1";
            }if(segjugador.getText().length() == 0){
                jugador2 = "Jugador 2";
            }else {
                jugador1 = unjugador.getText();
                jugador2 = segjugador.getText();
            }
        }
    }

    /**
     * genera el panel del juego individual
     */
    private void jueguito(){
        setLayout(new BorderLayout());
        vacio = new JPanel();
        puntajes = new JPanel();

        relleno = new JPanel();
        jug1 = new JLabel(jugador1);
        relleno.add(jug1);

        juego = new JPanel();
        juego.setLayout(new GridLayout(1,3,10,10));
        juego.setBackground(colorBorde);
        tablero = new JPanel();
        tablero.setLayout(new GridLayout(20,10,1,1));
        cuadrados = new JPanel[20][10];
        for (int i = 0;i<20 ;i++){
            for (int j = 0;j<10;j++) {
                cuadrito = new JPanel();
                cuadrito.setBackground(colorJuego);
                cuadrados[i][j] = cuadrito;
                tablero.add(cuadrito);
            }
        }

        puntajes.setLayout(null);
        vacio.setLayout(null);

        JLabel scoreTi = new JLabel("Puntaje:");
        scorePu = new JLabel("0");
        scoreTi.setBounds(30,30,100,30);
        scorePu.setBounds(130,30,100,30);
        pausa.setBounds(30,230,150,30);
        salirJuego.setBounds(30,270,150,30);
        puntajes.add(pausa);puntajes.add(salirJuego);puntajes.add(scoreTi);puntajes.add(scorePu);
        tablero.setBackground(Color.GRAY);
        //juego.add(vacio,BorderLayout.WEST);juego.add(tablero,BorderLayout.CENTER);juego.add(score,BorderLayout.EAST);
        juego.add(vacio);juego.add(tablero);juego.add(puntajes);

        FondoJpanel fondo = new FondoJpanel("chillP2.jpg");
        fondo.setBounds(0,0,getWidth()/3,getHeight());
        puntajes.add(fondo);

        FondoJpanel fondo2 = new FondoJpanel("bmo.gif");
        fondo2.setBounds(0,0,getWidth()/3,getHeight());
        vacio.add(fondo2);


    }

    /**
     * genera el JcomboBox para seleccionar la velocidad del juego
     */
    private void velocidad(){
        velocidades = new JComboBox<String>();
        velocidades.setBounds((ancho/2)+150,80,150,30);
        velocidades.addItem("Seleccione Velocidad");
        velocidades.addItem("Uniforme");
        velocidades.addItem("Acelerada");
        velocidades.addItemListener(this);

    }

    /**
     * genera el JcomboBox para seleccionar los buffos de la partida
     */
    private void Bufos(){
        BufosUsar = new JComboBox<String>();
        BufosUsar.setBounds((ancho/2)-300,80,190,30);
        BufosUsar.addItem("Seleccione Buffos");
        BufosUsar.addItem("Todos");
        BufosUsar.addItem("Stop Time");
        BufosUsar.addItem("Stop Diece");
        BufosUsar.addItem("2x");
        BufosUsar.addItem("Slow");
        BufosUsar.addItem("Stop Time - Stop Diece");
        BufosUsar.addItem("Stop Time - 2x");
        BufosUsar.addItem("Stop Time - Slow");
        BufosUsar.addItem("Stop Diece - 2x");
        BufosUsar.addItem("Stop Diece - Slow");
        BufosUsar.addItem("Stop Time - Stop Diece - 2x");
        BufosUsar.addItem("Stop Time - Stop Diece - Slow");
        BufosUsar.addItem("Stop Time - 2x - Slow");
        BufosUsar.addItem("Stop Diece - 2x - Slow");
        BufosUsar.addItemListener(this);

    }

    /**
     * genera el panel del juego 1 vs 1 o 1 vs Pc
     */
    private void jueguito2(){
        setLayout(new BorderLayout());
        puntajes = new JPanel();
        relleno = new JPanel();
        jug1 = new JLabel(jugador1);
        jug1.setBounds(10,10,100,30);
        jug2 = new JLabel(jugador2);
        jug1.setBounds(100,10,100,30);
        relleno.add(jug2);
        relleno.add(jug1);


        juego = new JPanel();
        juego.setLayout(new GridLayout(1,3,10,10));
        juego.setBackground(colorBorde);
        tablero = new JPanel();
        tablero.setLayout(new GridLayout(20,10,1,1));
        tablero2 = new JPanel();
        tablero2.setLayout(new GridLayout(20,10,1,1));
        cuadrados = new JPanel[20][10];
        cuadrados2 = new JPanel[20][10];
        for (int i = 0;i<20 ;i++){
            for (int j = 0;j<10;j++) {
                cuadrito = new JPanel();
                cuadrito.setBackground(colorJuego);
                cuadrados[i][j] = cuadrito;
                tablero.add(cuadrito);
                cuadrito2 = new JPanel();
                cuadrito2.setBackground(colorJuego);
                cuadrados2[i][j] = cuadrito2;
                tablero2.add(cuadrito2);
            }
        }
        puntajes.setLayout(null);
        scoreTi = new JLabel("Puntaje " + jugador1);
        scorePu = new JLabel("0");
        scoreTi2 = new JLabel("Puntaje " + jugador2);
        scorePu2 = new JLabel("0");
        scoreTi.setBounds(30,30,180,30);
        scorePu.setBounds(180,30,200,30);
        scoreTi2.setBounds(30,60,200,30);
        scorePu2.setBounds(180,60,180,30);
        pausa.setBounds(30,230,150,30);
        salirJuego.setBounds(30,270,150,30);
        puntajes.add(pausa);puntajes.add(salirJuego);puntajes.add(scoreTi);puntajes.add(scorePu);puntajes.add(scoreTi2);puntajes.add(scorePu2);
        tablero.setBackground(Color.GRAY);
        tablero2.setBackground(Color.GRAY);
        juego.add(tablero2);juego.add(puntajes);juego.add(tablero);
        FondoJpanel fondo = new FondoJpanel("chillP2.jpg");
        fondo.setBounds(0,0,getWidth()/3,getHeight());
        puntajes.add(fondo);
    }

    /**
     * reconoce la opcion seleccionada en los JComboBox
     * @param e opcion seleccionada
     */
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() ==velocidades){
            TipoVel = velocidades.getSelectedItem().toString();
            if(Objects.equals(TipoVel, "Uniforme") || Objects.equals(TipoVel, "Seleccione Velocidad") || TipoVel == null){
                velo = 1;
            }else{
                velo = 2;
            }
        }
        if(e.getSource() ==BufosUsar){
            TipoBufo = BufosUsar.getSelectedItem().toString();
            if(Objects.equals(TipoBufo, "Stop Time")){
                cantBufo = 1;
                bufoDisp = new String[]{"ST"};
            }else if(Objects.equals(TipoBufo, "Stop Diece")){
                cantBufo = 1;
                bufoDisp = new String[]{"SD"};
            }else if(Objects.equals(TipoBufo, "2x")){
                cantBufo = 1;
                bufoDisp = new String[]{"2x"};
            }else if(Objects.equals(TipoBufo, "Slow")){
                cantBufo = 1;
                bufoDisp = new String[]{"Slow"};
            }else if(Objects.equals(TipoBufo, "Stop Time - Stop Diece")){
                cantBufo = 2;
                bufoDisp = new String[]{"ST","SD"};
            }else if(Objects.equals(TipoBufo, "Stop Time - 2x")){
                cantBufo = 2;
                bufoDisp = new String[]{"ST","2x"};
            }else if(Objects.equals(TipoBufo, "Stop Time - Slow")){
                cantBufo = 2;
                bufoDisp = new String[]{"ST","Slow"};
            }else if(Objects.equals(TipoBufo, "Stop Diece - 2x")){
                cantBufo = 2;
                bufoDisp = new String[]{"SD","2x"};
            }else if(Objects.equals(TipoBufo, "Stop Diece - Slow")){
                cantBufo = 2;
                bufoDisp = new String[]{"SD","Slow"};
            }else if(Objects.equals(TipoBufo, "Stop Time - Stop Diece - 2x")){
                cantBufo = 3;
                bufoDisp = new String[]{"ST","SD","2x"};
            }else if(Objects.equals(TipoBufo, "Stop Time - Stop Diece - Slow")){
                cantBufo = 3;
                bufoDisp = new String[]{"ST","SD","Slow"};
            }else if(Objects.equals(TipoBufo, "Stop Time - 2x - Slow")){
                cantBufo = 3;
                bufoDisp = new String[]{"ST","2x","Slow"};
            }else if(Objects.equals(TipoBufo, "Stop Diece - 2x - Slow")){
                cantBufo = 3;
                bufoDisp = new String[]{"SD","2x","Slow"};
            }
        }
    }

    /**
     * encargado de cargar las imagenes o gif de la interfaz
     */
    public static class FondoJpanel extends JPanel{
        private final String imageUrl;

        public FondoJpanel(String imageUrl){
            this.imageUrl = imageUrl;
        }

        @Override
        public void paint(Graphics g) {
            Image imagen = new ImageIcon(imageUrl).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            setOpaque(false);
            super.paint(g);
        }
    }

    /**
     * metodo que llama la atencion de los perifericos
     * @param e tecla seleccionada
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * metodo que llama la atencion de los perifericos
     * @param e tecla seleccionada
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'w'){
            juegoLogico.movimiento("w");
        }if(e.getKeyChar() == 's') {
            juegoLogico.movimiento("s");
            if(bufoActivado == 2){
                tiempo.restart();
                bufoActivado = 0;
            }
        }if(e.getKeyChar() == 'd') {
            juegoLogico.movimiento("d");
        }if(e.getKeyChar() == 'a') {
            juegoLogico.movimiento("a");
        }if (e.getKeyChar() == '.'){
            if(juegoLogico.getTengoBufo()) {
                bufoActivado=juegoLogico.activarBuffo();
                activarElBufo();
                juegoLogico.setUsarBufo(false);
                tiempoBufos.start();
            }
        }
        if(ConsTablero == 2 || ConsTablero == 3) {
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                juegoLogico2.movimiento("w");
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                juegoLogico2.movimiento("a");
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                juegoLogico2.movimiento("d");
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                juegoLogico2.movimiento("s");
                if(bufoActivado2 == 2){
                    tiempo2.restart();
                    bufoActivado2 = 0;
                }
            }
            if (e.getKeyChar() == '0'){
                if(juegoLogico2.getTengoBufo()) {
                    bufoActivado2=juegoLogico2.activarBuffo();
                    activarElBufo2();
                    juegoLogico2.setUsarBufo(false);
                    tiempoBufos2.start();
                }
            }
        }
        refresque();
    }

    /**
     * metodo que llama la atencion de los perifericos
     * @param e tecla seleccionada
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * metodo que modifica el delay del juego dependiendo del buffo activado
     */
    private void activarElBufo() {
        if(bufoActivado == 1 || bufoActivado == 2){
            tiempo.stop();
        }else if(bufoActivado == 3){
            int delayT = dela * 2;
            tiempo.setDelay(delayT);
        }else if(bufoActivado == 4){
            int delayT = dela / 2;
            tiempo.setDelay(delayT);
        }
    }

    /**
     * metodo que modifica el delay del juego dependiendo del buffo activado para el tablero 2
     */
    private void activarElBufo2() {
        if(bufoActivado2 == 1 || bufoActivado2 == 2){
            tiempo2.stop();
        }else if(bufoActivado2 == 3){
            int delayT2 = dela2 * 2;
            tiempo2.setDelay(delayT2);
        }else if(bufoActivado2 == 4){
            int delayT2 = dela2 / 2;
            tiempo2.setDelay(delayT2);
        }
    }

    /**
     * prepara los elementos logicos del panel del juego
     * @param file si es necesario cargar un juego
     * @throws TetrisException lanza de ser necesario
     */
    public void empezarJuego(File file) throws TetrisException {
        juegoLogico = new Tetris();
        if(cantBufo<4) {
            juegoLogico.setBufos(bufoDisp);
            juegoLogico.setCantBufo(cantBufo);
        }
        if(file != null){
            tableroLog = juegoLogico.crearTablero(file);
            jugador1 = juegoLogico.getNickname();
            jug1.setText(jugador1);
        }else {
            tableroLog = juegoLogico.tableroLog();
        }
//        ReproducirSonido("tetrisMusica.wav");
//        musica = true;
        if (velo == 2){
            dela = 1200;
            tiempo.setDelay(dela);
            tiempoJuego = new Timer(10000,this::aumento);
            tiempoJuego.start();
        }
        tiempo.start();
    }

    /**
     * prepara los elementos logicos del panel del juego
     * @param file si es necesario cargar un juego
     * @throws TetrisException lanza de ser necesario
     */
    public void empezarJuego2(File file) throws TetrisException {
        juegoLogico = new Tetris();
        juegoLogico2 = new Tetris();
        if(cantBufo<4) {
            juegoLogico.setBufos(bufoDisp);
            juegoLogico.setCantBufo(cantBufo);
            juegoLogico2.setBufos(bufoDisp);
            juegoLogico2.setCantBufo(cantBufo);
        }
        if(file != null) {
            tableroLog = juegoLogico.crearTablero(file);
            tableroLog2 = juegoLogico2.crearTablero2(file);
            jugador1 = juegoLogico.getNickname();
            jugador2 = juegoLogico2.getNickname();
            jug1.setText(jugador1);
            jug2.setText(jugador2);
            scoreTi.setText("Puntaje " + jugador1);
            scoreTi2.setText("Puntaje " + jugador2);
            ConsTablero = 2;
        }else {
            tableroLog = juegoLogico.tableroLog();
            tableroLog2 = juegoLogico2.tableroLog();
        }
//        ReproducirSonido("tetrisMusica.wav");
//        musica = true;
        if (velo == 2){
            dela = 1200;
            tiempo.setDelay(dela);
            tiempoJuego = new Timer(10000,this::aumento);
            if(ConsTablero == 2){
                dela2 = 1200;
                tiempo2.setDelay(dela2);
                tiempoJuego2 = new Timer(10000,this::aumento2);
                tiempoJuego2.start();
            }
            tiempoJuego.start();
        }
        if(ConsTablero == 2)
            tiempo2.start();
        tiempo.start();
    }

    /**
     * prepara los elementos logicos del panel del juego
     * @param file si es necesario cargar un juego
     * @throws TetrisException lanza de ser necesario
     */
    public void empezarJuego3(File file) throws TetrisException {
        juegoLogico = new Tetris();
        juegoLogico2 = new Tetris();
        pc = new Pc(juegoLogico2);
        if(cantBufo<4) {
            juegoLogico.setBufos(bufoDisp);
            juegoLogico.setCantBufo(cantBufo);
            juegoLogico2.setBufos(bufoDisp);
            juegoLogico2.setCantBufo(cantBufo);
        }
        if(file != null) {
            tableroLog = juegoLogico.crearTablero(file);
            tableroLog2 = juegoLogico2.crearTablero2(file);
            jugador1 = juegoLogico.getNickname();
            jugador2 = juegoLogico2.getNickname();
            jug1.setText(jugador1);
            jug2.setText(jugador2);
            scoreTi.setText("Puntaje " + jugador1);
            scoreTi2.setText("Puntaje " + jugador2);
            ConsTablero = 3;
        }else {
            tableroLog = juegoLogico.tableroLog();
            tableroLog2 = juegoLogico2.tableroLog();
        }
//        ReproducirSonido("tetrisMusica.wav");
//        musica = true;
        if (velo == 2){
            dela = 1200;
            tiempo.setDelay(dela);
            tiempoJuego = new Timer(10000,this::aumento);
            if(ConsTablero == 3){
                dela2 = 1200;
                tiempo2.setDelay(dela2);
                tiempoJuego2 = new Timer(10000,this::aumento2);
                tiempoJuego2.start();
            }
            tiempoJuego.start();
        }
        if(ConsTablero == 3)
            tiempo2.start();
        tiempo.start();
    }

    /**
     * aumenta el delay si la velocidad se escogio acelerada
     * @param actionEvent tiempo para llamar al metodo
     */
    private void aumento(ActionEvent actionEvent) {
        if(dela > 100) {
            dela -= 100;
        }else if(dela >= 80){
            dela -= 10;
        }
        tiempo.setDelay(dela);
    }

    /**
     * aumenta el delay si la velocidad se escogio acelerada
     * @param actionEvent tiempo para llamar al metodo
     */
    private void aumento2(ActionEvent actionEvent) {
        if(dela2 > 100) {
            dela2 -= 100;
        }else if(dela2 >= 80){
            dela2 -= 10;
        }
        tiempo2.setDelay(dela2);
    }

    /**
     * metodo que controla la bajada automatica del juego
     * @param actionEvent tiempo para llamar al metodo
     */
    private void prueba(ActionEvent actionEvent){
        if (!juegoLogico.getFinalT() && juegoLogico.perder()) {
            juegoLogico.jugar("s");
            if(bufoActivado2 == 2 && ConsTablero == 3){
                pc.AccionRandom();
            }
            ficha = juegoLogico.getFicha();
            if(ConsTablero != 1 && ficha == Color.RED){
                ra = true;
                tiempo2.stop();
            }
        } else if (juegoLogico.perder()) {
            juegoLogico.jugar("f");
            juegoLogico.setFinalT(false);
            if(ConsTablero != 1 && ra){
                ra = false;
                tiempo2.restart();
            }

        } else {
            if (velo == 2) {
                tiempoJuego.stop();
            }
            tiempo.stop();
            if(ConsTablero == 2){
                tiempo2.stop();
                juegoLogico2.records("Records.txt",jugador2);
            }
            juegoLogico.records("Records.txt",jugador1);
            JOptionPane.showMessageDialog(null, "Perdiste :(", "FIN DEL JUEGO", JOptionPane.INFORMATION_MESSAGE);
            volver();
        }
        refresque();
    }

    /**
     * metodo que controla la bajada automatica del juego
     * @param actionEvent tiempo para llamar al metodo
     */
    private void prueba2 (ActionEvent actionEvent){
        if (!juegoLogico2.getFinalT() && juegoLogico2.perder()) {
            juegoLogico2.jugar("s");
            if(ConsTablero == 3){
                pc.AccionRandom();
            }
            ficha2 = juegoLogico2.getFicha();
            if(ficha2 == Color.RED){
                ra = true;
                tiempo.stop();
            }
        } else if (juegoLogico2.perder()) {
            juegoLogico2.jugar("f");
            juegoLogico2.setFinalT(false);
            if(ra){
                ra = false;
                tiempo.restart();
            }
        } else {
            if (velo == 2) {
                tiempoJuego.stop();
                tiempoJuego2.stop();
            }
            tiempo.stop();
            juegoLogico.records("Records.txt",jugador1);
            tiempo2.stop();
            juegoLogico2.records("Records.txt",jugador2);
            JOptionPane.showMessageDialog(null, "Perdiste :(", "FIN DEL JUEGO", JOptionPane.INFORMATION_MESSAGE);
            volver();
        }
        refresque();
    }

    /**
     * pinta varios JPanel que representan el juego y sus fichas
     */
    private void refresque(){
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                cuadrados[i][j].revalidate();
                revisarBufo(i,j);
                if (tableroLog[i][j] == 1) {
                    if (juegoLogico.getTipo() == 3) {
                        borde = BorderFactory.createLineBorder(Color.RED, 2);
                        cuadrados[i][j].setBorder(borde);
                    } else if (juegoLogico.getTipo() == 1) {
                        borde = BorderFactory.createLineBorder(new Color(192, 192, 192), 2);
                        cuadrados[i][j].setBorder(borde);
                    }
                    cuadrados[i][j].setBackground(ficha);
                }if(tableroLog[i][j] == -2 || tableroLog[i][j] == -3 || tableroLog[i][j] == -4 || tableroLog[i][j] == -5 || tableroLog[i][j] == -6){
                    borde = BorderFactory.createLineBorder(new Color(192, 192, 192), 2);
                    cuadrados[i][j].setBorder(borde);
                }if (tableroLog[i][j] == 0) {
                    cuadrados[i][j].setBorder(null);
                    cuadrados[i][j].setBackground(colorJuego);
                }if(tableroLog[i][j] == 2 || tableroLog[i][j] == -2){
                    cuadrados[i][j].setBackground(Color.YELLOW);
                }if(tableroLog[i][j] == 3 || tableroLog[i][j] == -3) {
                    cuadrados[i][j].setBackground(Color.ORANGE);
                }if(tableroLog[i][j] == 4 || tableroLog[i][j] == -4) {
                    cuadrados[i][j].setBackground(Color.GREEN);
                }if(tableroLog[i][j] == 5 || tableroLog[i][j] == -5) {
                    cuadrados[i][j].setBackground(Color.CYAN);
                }if(tableroLog[i][j] == 6 || tableroLog[i][j] == -6) {
                    cuadrados[i][j].setBackground(Color.MAGENTA);
                }
            }
        }
        scorePu.setText(juegoLogico.getPuntaje());
        if(ConsTablero == 2 || ConsTablero == 3){
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 10; j++) {
                    revisarBufo(i,j);
                    cuadrados2[i][j].revalidate();
                    if (tableroLog2[i][j] == 1) {
                        if(juegoLogico2.getTipo() == 3) {
                            borde2 = BorderFactory.createLineBorder(Color.RED, 2);
                            cuadrados2[i][j].setBorder(borde2);
                        }else if (juegoLogico2.getTipo() == 1) {
                            borde2 = BorderFactory.createLineBorder(new Color(192, 192, 192), 2);
                            cuadrados2[i][j].setBorder(borde2);
                        }
                        cuadrados2[i][j].setBackground(ficha2);
                    }if(tableroLog2[i][j] == -2 || tableroLog2[i][j] == -3 || tableroLog2[i][j] == -4 || tableroLog2[i][j] == -5 || tableroLog2[i][j] == -6){
                        borde2 = BorderFactory.createLineBorder(new Color(192, 192, 192), 2);
                        cuadrados2[i][j].setBorder(borde2);
                    }if (tableroLog2[i][j] == 0) {
                        cuadrados2[i][j].setBorder(null);
                        cuadrados2[i][j].setBackground(colorJuego);
                    }if(tableroLog2[i][j] == 2 || tableroLog2[i][j] == -2){
                        cuadrados2[i][j].setBackground(Color.YELLOW);
                    }if(tableroLog2[i][j] == 3 || tableroLog2[i][j] == -3) {
                        cuadrados2[i][j].setBackground(Color.ORANGE);
                    }if(tableroLog2[i][j] == 4 || tableroLog2[i][j] == -4) {
                        cuadrados2[i][j].setBackground(Color.GREEN);
                    }if(tableroLog2[i][j] == 5 || tableroLog2[i][j] == -5) {
                        cuadrados2[i][j].setBackground(Color.CYAN);
                    }if(tableroLog2[i][j] == 6 || tableroLog2[i][j] == -6) {
                        cuadrados2[i][j].setBackground(Color.MAGENTA);
                    }
                }
            }
            scorePu2.setText(juegoLogico2.getPuntaje());
        }
    }

    /**
     * pinta los buffos
     * @param i posicion i de la matriz
     * @param j posicion j de la matriz
     */
    private void revisarBufo(int i, int j){
        Color colorBuffo;
        if(tableroLog[i][j]==7){
            colorBuffo = new Color(204,204,0);
            cuadrados[i][j].setBackground(colorBuffo);
        }else if(tableroLog[i][j]==8){
            colorBuffo = new Color(153,0,153);
            cuadrados[i][j].setBackground(colorBuffo);
        }else if(tableroLog[i][j]==9){
            colorBuffo = new Color(51,153,0);
            cuadrados[i][j].setBackground(colorBuffo);
        }else if(tableroLog[i][j]== -1){
            colorBuffo = new Color(255,102,0);
            cuadrados[i][j].setBackground(colorBuffo);
        }
        if(ConsTablero == 2 || ConsTablero == 3){
            Color colorBuffo2;
            if(tableroLog2[i][j]==7){
                colorBuffo2 = new Color(204,204,0);
                cuadrados2[i][j].setBackground(colorBuffo2);
            }else if(tableroLog2[i][j]==8){
                colorBuffo2 = new Color(153,0,153);
                cuadrados2[i][j].setBackground(colorBuffo2);
            }else if(tableroLog2[i][j]==9){
                colorBuffo2 = new Color(51,153,0);
                cuadrados2[i][j].setBackground(colorBuffo2);
            }else if(tableroLog2[i][j]== -1){
                colorBuffo2 = new Color(255,102,0);
                cuadrados2[i][j].setBackground(colorBuffo2);
            }
        }
    }

    /**
     * reproduce el sonido del juego
     * @param nombreSonido URL del archivo .wav
     */
    public void ReproducirSonido(String nombreSonido){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl contolV = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            contolV.setValue(-20.0f);
            clip.start();
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir el sonido.");
        }
    }

    /**
     * reinicia los tiempos despues de emplear un buffo
     * @param actionEvent tiempo al que se llama el metodo
     */
    private void reiniciarTiempo(ActionEvent actionEvent) {
        if(bufoActivado == 1) {
            tiempo.restart();
            bufoActivado = 0;
        }else if (bufoActivado == 3 || bufoActivado == 4){
            tiempo.setDelay(dela);
            bufoActivado = 0;
        }
        tiempoBufos.stop();
    }

    /**
     * reinicia los tiempos despues de emplear un buffo
     * @param actionEvent tiempo al que se llama el metodo
     */
    private void reiniciarTiempo2(ActionEvent actionEvent) {
        if(bufoActivado2 == 1) {
            tiempo2.restart();
            bufoActivado2 = 0;
        }else if (bufoActivado2 == 3 || bufoActivado2 == 4){
            tiempo2.setDelay(dela2);
            bufoActivado2 = 0;
        }
        tiempoBufos2.stop();
    }

    /**
     * metodo que guarda la partida
     * @param file archivo donde se guarda
     * @throws TetrisException lanza de ser necesario
     */
    public void guardar(File file) throws TetrisException{
        juegoLogico.guardar(file,jugador1);
        if(ConsTablero == 2){
            juegoLogico2.sobreescribir(file,jugador2);
        }else if(ConsTablero == 3){
            juegoLogico2.sobreescribir2(file,jugador2);
        }
    }
}
