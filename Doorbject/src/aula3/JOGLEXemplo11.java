/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula01;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javafx.animation.Animation;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 *
 * @author Glauco
 */
public class JOGLEXemplo11 
             implements GLEventListener {

    GLU glu = new GLU();
    GLUT glut = new GLUT();
    
    public static void main(String args[])
    {
        new JOGLEXemplo11();
    }
    
    public JOGLEXemplo11()
    {
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);
        
        JFrame frame = new JFrame("Exemplo01");
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

    
    }
    
    
    
    public void init(GLAutoDrawable glAuto) {
        Animator a = new Animator(glAuto);
        a.start();
      
    }

    double g, g2, inc=0.2;
    @Override
    public void display(GLAutoDrawable glAuto) {

        GL2 gl = glAuto.getGL().getGL2();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT |
                   GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-10);
        
        
        gl.glPushMatrix();
                gl.glScaled(0.5, 1.5, 0);
                glut.glutWireCube(1);
            gl.glPopMatrix();
            gl.glTranslated(0.5, -1.25, 0);
            gl.glPushMatrix();
                gl.glScaled(1.5, 1.5, 0);
                glut.glutWireCube(2);
            gl.glPopMatrix();

            gl.glPushMatrix();
                   gl.glTranslated(0.25, 1.25, 0);
                   gl.glRotated(90, 0, 0, 1);
                   gl.glTranslated(1.5, 0.25, 0);
                   gl.glScaled(1, 1, 0);
                   glut.glutWireCube(1);
            gl.glPopMatrix();

        
    }

       
    
    
    
    
    
    
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
  
        GL2 gl = gLAutoDrawable.getGL().getGL2(); 
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60,1,1,30);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-5);
        gl.glEnable(GL2.GL_DEPTH_TEST);
       }


    public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
      
    }

    public void dispose(GLAutoDrawable glad) {

    }

    private void desenhaEstrela(GL2 gl) {
    gl.glBegin(GL2.GL_TRIANGLES);
           gl.glVertex3d(-1, 1, 0);
           gl.glVertex3d( 0, 3, 0);
           gl.glVertex3d( 1, 1, 0);
           
           gl.glVertex3d(-1, -1, 0);
           gl.glVertex3d( 0, -3, 0);
           gl.glVertex3d( 1, -1, 0);
           
           gl.glVertex3d(-1, 1, 0);
           gl.glVertex3d( -3, 0, 0);
           gl.glVertex3d( -1, -1, 0);
           
           gl.glVertex3d( 1,  1, 0);
           gl.glVertex3d( 3,  0, 0);
           gl.glVertex3d( 1, -1, 0);
           
           
        gl.glEnd();

    }

}











