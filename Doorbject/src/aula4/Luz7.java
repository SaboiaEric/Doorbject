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

public class Luz7 implements GLEventListener {
    
    GLUT glut = new GLUT();
    GLU glu  = new GLU();
    GLJPanel canvas = new GLJPanel();
    GL2 gl;
    float rot, inc=1;
    
    float luzDifusa[]   ={1f,0f,0f,1.0f};
    float matDifusa1[]  ={0f,0f,1f,0.0f};
    float matDifusa2[]  ={1.0f,1f,0f,0.0f};
    
    public Luz7() {
        
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
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glShadeModel(GL2.GL_SMOOTH);

        gl.glEnable(GL2.GL_LIGHTING);  
	gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        
        //Valores da Luz Ambiente
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE,  luzDifusa,0); 
    
        
        
        
    }
    
    public void display(GLAutoDrawable gLAutoDrawable) {
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-10);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Define a concentra��o do brilho
	gl.glColor3f(0,0,1);
        
       // gl.glEnable(GL.GL_COLOR_MATERIAL);
        gl.glRotated(rot++, 0,1,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, matDifusa1,0);
        glut.glutSolidTeapot(2);
        gl.glTranslated(2,2,0);
        
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, matDifusa2,0);
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
        new Luz7();
    }

    public void dispose(GLAutoDrawable glad) {
        
    }
    
    
    
    
}


