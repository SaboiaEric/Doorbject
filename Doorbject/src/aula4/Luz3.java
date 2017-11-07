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

public class Luz3 implements GLEventListener {
    
    GLUT glut = new GLUT();
    GLU glu  = new GLU();
    GLJPanel canvas = new GLJPanel();
    GL2 gl;
    float rot, incZ=0.1f;
    
    
    float posicaoLuz[]   ={0.0f, 0.0f, 5.0f, 0.0f};
    
    
    public Luz3() {
        
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
        
        
        Animator ani = new Animator(gLAutoDrawable);
        ani.start();
        
        
        gl = gLAutoDrawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        

        gl.glEnable(GL2.GL_LIGHTING);  
	gl.glEnable(GL2.GL_LIGHT0);
	gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0 );
    }
    
    public void display(GLAutoDrawable gLAutoDrawable) {
        
        gl = gLAutoDrawable.getGL().getGL2();
        
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-10);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0 );
        
        gl.glPushMatrix();
            gl.glTranslated(posicaoLuz[0], posicaoLuz[1],posicaoLuz[2]);
            glut.glutSolidSphere(0.25, 10, 10);
        gl.glPopMatrix();
        
        gl.glColor3f(0,0,1);
        glut.glutSolidTeapot(1);
        
        gl.glTranslated(2,2,0);
        gl.glColor3f(2,0,0);
        glut.glutSolidCube(1);
        
        
        posicaoLuz[0] += incZ;
               
        if(posicaoLuz[0] < -10)
           incZ=0.01f;
        
        if(posicaoLuz[0] > 10)
           incZ=-0.01f;
        
        
        
   
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
        new Luz3();
    }

    public void dispose(GLAutoDrawable glad) {
        
    }
    
    
    
    
}


