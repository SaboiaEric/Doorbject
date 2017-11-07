/*
 * HelloJOGL.java
 *
 * Created on 8 de Agosto de 2007, 19:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package aula4;




import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

public class Luz5 implements GLEventListener {
    
    GLUT glut = new GLUT();
    GLU glu  = new GLU();
    GLJPanel canvas = new GLJPanel();
    GL2 gl;
    float rot, inc=1;
    
    float luzAmbiente[]  ={1f,0f,0f,1.0f};
    
    public Luz5() {
        
        canvas.addGLEventListener(this);
        
        JFrame frame = new JFrame("Luz 1");
        frame.setSize(500, 500);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        
        
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new Thread(new Runnable() {
                    public void run() {
                        System.exit(0);
                    }
                }).start();
            }
        });
        
        
    }
    
    public void init(GLAutoDrawable gLAutoDrawable) {
        gl = gLAutoDrawable.getGL().getGL2();
        
        Animator ani = new Animator(gLAutoDrawable);
        ani.start();
        
        gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);


        gl.glEnable(GL2.GL_LIGHTING);  
	gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        
        //Valores da Luz Ambiente
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT,  luzAmbiente,0); 
        
    }
    
    public void display(GLAutoDrawable gLAutoDrawable) {
        gl = gLAutoDrawable.getGL().getGL2();
        
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-10);
        
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        glut.glutSolidTeapot(2);
        gl.glTranslated(2,2,0);
        glut.glutSolidCube(1);
    }
    
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
        gl = gLAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60,1,1,30);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-3);
    }
    
    public void displayChanged(GLAutoDrawable gLAutoDrawable,boolean modeChanged, boolean deviceChanged) {
        
    }
    
    public static void main(String args[]) {
        new Luz5();
    }

    public void dispose(GLAutoDrawable glad) {
        
    }
    
    
    
    
}


