package fonte;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.media.opengl.*;
import static javax.media.opengl.GL.GL_BLEND;
import static javax.media.opengl.GL.GL_ONE_MINUS_SRC_ALPHA;
import static javax.media.opengl.GL.GL_SRC_ALPHA;
import javax.media.opengl.awt.GLJPanel;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_COLOR_MATERIAL;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

//Pesquisas que podem ajudar:
/*
https://stackoverflow.com/questions/1939317/how-do-i-find-method-calls
 */
public class Principal implements GLEventListener, KeyListener {

    GLUT glut = new GLUT();
    GLU glu = new GLU();
    GLJPanel canvas = new GLJPanel();
    GL2 gl;
    TextRenderer textRenderer;
    
    //Informacoes sobre a luz
    float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f};
    float luzDifusa[] = {1.0f, 1.0f, 1.0f, 1.0f};	   // "cor"
    float luzEspecular[] = {1.0f, 1.0f, 1.0f, 1.0f};// "brilho"
    float posicaoLuz[] = {0.0f, 50.0f, 50.0f, 1.0f};

    // Informacoes sobre o material
    float especularidade[] = {1.0f, 0.0f, 0.0f, 1.0f};
    int especMaterial = 60;
    double eqn[] = {-0.15, 0.15, 0, 0};
    
    
    // variaveis referentes ao jogo
    int qtdVidas = 3;
    int pontos = 0;
    GameState gameState;
    CircleType circleType;
    boolean canPress;
    boolean indicate;
    
    // variavel utilizada para marcar pontos
    boolean scored;
    
    
    // variaveis referentes aos desenhos
    float rot, distancia = 0.0f;
    double incblend = 0.05, blend = 0;
    double x = 0, y = 0;
    double incx = 0;
    double incy = 0;    
    double inc = 0.05;
    double TorusPosZ = -15;
    int r, g, b;
    
    
    public Principal() {
        GLJPanel canvas = new GLJPanel();

        canvas.addGLEventListener(this);

        JFrame frame = new JFrame("Doorbject");
        frame.setSize(500, 500);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.addKeyListener(this);

    }

    public void init(GLAutoDrawable gLAutoDrawable) {
        gl = gLAutoDrawable.getGL().getGL2();
        gl.glClearColor(1.0f, 1.0f, 0.0f, 1.0f);

        Animator a = new Animator(gLAutoDrawable);
        a.start();

        // Define a refletancia do material 
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, especularidade, 0);

        // Define a concentração do brilho
        gl.glMateriali(GL.GL_FRONT_AND_BACK, GL2.GL_SHININESS, especMaterial);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, especularidade, 0);

        // Ativa o uso da luz ambiente 
        gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, luzAmbiente, 0);

        // Define os parâmetros da luz de número 0
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, luzDifusa, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, luzEspecular, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, posicaoLuz, 0);

        // Habilita a defini��o da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        //Habilita o uso de iluminação
        gl.glEnable(GL2.GL_LIGHTING);
        // Habilita a luz de n�mero 0
        gl.glEnable(GL2.GL_LIGHT1);
        // Habilita o depth-buffering
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        
        
        // inicializa
        criaProximoCirculo();
        gameState = GameState.Menu;
        textRenderer = new TextRenderer(new Font("Verdana", Font.BOLD, 50));

    }

    public void display(GLAutoDrawable gLAutoDrawable) {
        
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        switch (gameState) {
            case Menu:
                desenhaTexto("DOORBJECT", 80, 300, 1, Color.RED);
                desenhaTexto("Aperte V para iniciar", 120, 270, 0.4f, Color.DARK_GRAY);
                desenhaTexto("COMANDOS:", 120, 200, 0.5f, Color.RED);
                desenhaTexto("Aperte Z para VERMELHO", 120, 160, 0.4f, Color.RED);
                desenhaTexto("Aperte X para VERDE", 120, 140, 0.4f, Color.GREEN);
                desenhaTexto("Aperte C para AZUL", 120, 120, 0.4f, Color.BLUE);
                break;
            case InGame:
                desenhaTexto(pontos + " pontos", 10, 10, 0.4f, Color.DARK_GRAY);
                desenhaVidas(gl);
                desenhaCirculos(gl);
                break;
            case GameOver:
                blend = 1;
                desenhaTexto("PONTUAÇÃO:", 60, 350, 0.4f, Color.BLACK);
                desenhaTexto(pontos + " pontos", 60, 300, 0.8f, Color.DARK_GRAY);
                desenhaTexto("VOCÊ PERDEU", 60, 200, 1, Color.RED);
                desenhaTexto("Aperte V para continuar", 120, 170, 0.4f, Color.DARK_GRAY);
                break;
        }
        
        if(indicate)
        {
            gl.glPushMatrix();                    
            gl.glEnable(GL_BLEND);
            gl.glBlendFunc(GL_ONE_MINUS_SRC_ALPHA, GL_SRC_ALPHA);
            gl.glColor4f(r, g, b, (float)(blend+=incblend));
            gl.glTranslated(0, 0, -10.0);
            gl.glBegin(GL2.GL_QUADS);
            gl.glVertex3d(250, 250, 1);
            gl.glVertex3d(250, -250, 1);
            gl.glVertex3d(-250, -250, 1);
            gl.glVertex3d(-250, 250, 1);
            gl.glEnd();
            gl.glDisable(GL_BLEND);
            gl.glPopMatrix();
        }
        
        if(blend >= 1) indicate = false;
        
        
        
        verificaJogo();    
        
    }
    
    private void desenhaTexto(String text, int x, int y, float fontscale, Color fontcolor)
    {
            textRenderer.beginRendering(500, 500);
            textRenderer.setColor(fontcolor);
            textRenderer.setSmoothing(true);

            textRenderer.draw3D(text, (float)x, (float)y, (float)0, (float)fontscale);
            textRenderer.endRendering(); 
            textRenderer.flush();
    }

    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
        gl = gLAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, 1, 1, 30);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0, 0, -5);
    }

    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean modeChanged, boolean deviceChanged) {

    }

    public static void main(String args[]) {
        Principal principal;
        principal = new Principal();
        
    }

    public void dispose(GLAutoDrawable glad) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_V) {
            blend = 1;
            
            if(gameState.equals(GameState.Menu))
            {
                scored = false;
                canPress = true;
                qtdVidas = 3;
                pontos = 0;
                gameState = GameState.InGame;
            }
            
            if(gameState.equals(GameState.GameOver)) gameState = GameState.Menu;
        }
        else if(canPress)
        {
            if (ke.getKeyCode() == KeyEvent.VK_Z) { // letra Z
                scored = circleType.equals(CircleType.Red); // se for vermelho, marcou ponto
            }

            if (ke.getKeyCode() == KeyEvent.VK_X) { // letra X
                scored = circleType.equals(CircleType.Green); // se for verde, marcou ponto
            }

            if (ke.getKeyCode() == KeyEvent.VK_C) { // letra C
                scored = circleType.equals(CircleType.Blue); // se for azul, marcou ponto
            }

            mostraIndicador();
        }
    }
    
    private void mostraIndicador()
    {
        canPress = false;
        indicate = true;
        
        b = 0;
        blend = 0;

        if(scored)
        {
            r = 0;
            g = 1;
        }
        else {
            r = 1;
            g = 0;                
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
    
    private void desenhaVidas(GL2 g1){
        gl.glPushMatrix();
        gl.glTranslated(10, -10, -20);
        
        gl.glPushMatrix();
            
            gl.glColor3f(1, 0, 0);
        
            for(int i = 0; i < qtdVidas; i++)
            {
                gl.glPushMatrix();
                    gl.glRotated(rot+=0.5, 1, 1, distancia);
                    glut.glutSolidCube(1);
                gl.glPopMatrix();

                gl.glTranslated(-2, 0, 0);
            }
        gl.glPopMatrix();
    }
    
    private void desenhaCirculos(GL2 gl){
        
        switch(circleType) 
        {
            case Blue:
                gl.glColor3f(0, 0, 1);
                break;
            case Red:
                gl.glColor3f(1, 0, 0);
                break;
            case Green:
                gl.glColor3f(0, 1, 0);
                break;
        }
        
        gl.glPushMatrix();
            gl.glTranslated(x, y, TorusPosZ-10.0);
            glut.glutSolidTorus(0.1,2.0,30,30);
        gl.glPopMatrix();
        TorusPosZ += inc;
        
        // movimenta o circulo sempre em direção ao centro da 
        if(x > 0) x -= incx;
        else x += incx;
        
        if(y > 0) y -= incy;
        else y += incy;
    }
        
    private void verificaJogo(){
        
        
        if(TorusPosZ >= 15){
            
            if(scored)
            {
                pontos += 10;
            }
            else
            {
                qtdVidas--;

                // se a quantidade de vidas for igual a 0, GAMEOVER
                if(qtdVidas <= 0) gameState = GameState.GameOver;
            }            
            
            canPress = true;
            scored = false;
            criaProximoCirculo();
        }
        
    }
    
    private void criaProximoCirculo()
    {
        TorusPosZ = -10;

        Random r = new Random();

        // Define uma posição aleatoria entre -5 e 5 para x e y
        x = r.nextInt(10) - 5;
        y = r.nextInt(10) - 5;

        // Define um número para que o movimento em direção ao centro da tela
        // seja suave
        incx = Math.abs(x / 500.0);
        incy = Math.abs(y / 500.0);

        // Sorteia uma cor para o próximo circulo
        switch(r.nextInt(3))
        {
            case 0:
                circleType = CircleType.Green;
                break;
            case 1:
                circleType = CircleType.Red;
                break;
            case 2:
                circleType = CircleType.Blue;
                break;
        }
    }
}