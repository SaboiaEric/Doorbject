package fonte;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
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
    float rot, distancia = 0.0f;

    //Informacoes sobre a luz
    float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f};
    float luzDifusa[] = {1.0f, 1.0f, 1.0f, 1.0f};	   // "cor"
    float luzEspecular[] = {1.0f, 1.0f, 1.0f, 1.0f};// "brilho"
    float posicaoLuz[] = {0.0f, 50.0f, 50.0f, 1.0f};

    // Informacoes sobre o material
    float especularidade[] = {1.0f, 0.0f, 0.0f, 1.0f};
    int especMaterial = 60;
    double eqn[] = {-0.15, 0.15, 0, 0};

    //Informações sobre a tela
    private boolean up, down, shot;

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
    }

    double g = -10, g2, inc = 0.2;

    public void display(GLAutoDrawable gLAutoDrawable) {

        GL2 gl = gLAutoDrawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        gl.glTranslated(0, 0, g);

        //Seta a cor do objeto
        gl.glColor3f(0, 1, 0);

        //Faz a rotação do objeto
        //X = direita, esquerda
        //Y = cima, baixo
        gl.glRotated(rot++, 1, 1, distancia); //angulo, x,y,z

        //gl.glRotated(rot,distancia,1,0);
        //glut.glutSolidSphere(1,50,50);
        //glut.glutSolidTeapot(1);
        glut.glutSolidCube(1);

        if (shot) {
            g -= 20;
        }

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
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }

        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }

        if (ke.getKeyCode() == KeyEvent.VK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
            shot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }

        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }

        if (ke.getKeyCode() == KeyEvent.VK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
            shot = false;
        }
    }
}
