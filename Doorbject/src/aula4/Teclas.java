/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aula4;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class Teclas 
             implements GLEventListener, KeyListener{

    GLU glu = new GLU();
    GLUT glut = new GLUT();
    
    public static void main(String args[])
    {
        new Teclas();
    }
    private boolean up;
    private boolean down,shot;
    
    public Teclas()
    {
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);
        
        JFrame frame = new JFrame("Exemplo01");
        frame.setSize(250, 250);
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
        //gl.glRotated(90, 1, 0, 0);
        
        
        gl.glRotated(g2, 0, 1, 0);
        
        
        for(int i=0; i < 4; i++){
            
        gl.glRotated(90, 0, 1, 0);
        gl.glPushMatrix();
        
        gl.glPushMatrix();
          gl.glScaled(2, 1, 1);
          glut.glutWireCube(1);
        gl.glPopMatrix();
        
        
        gl.glPushMatrix();
            gl.glTranslated(1, 0.5, 0);
            gl.glRotated(g, 0, 0, 1);
            gl.glTranslated(1, -0.5, 0);
            
            gl.glPushMatrix();
                gl.glScaled(2, 1, 1);
                glut.glutWireCube(1);
            gl.glPopMatrix();
            
            //Dedo1
            gl.glPushMatrix();
                gl.glTranslated(1, 0.5, 0);
                gl.glRotated(g, 0, 0 ,1); 
                gl.glTranslated(0.25, -0.125, 0);
                gl.glPushMatrix();
                    gl.glScaled(0.5, 0.25, 0.25);
                    glut.glutWireCube(1); // Dedo
                gl.glPopMatrix();

                gl.glTranslated(0.25,-0.125, 0);
                gl.glRotated(-g*2, 0, 0 ,1); 
                gl.glTranslated(0.25, 0.125, 0);
                gl.glScaled(0.5, 0.25, 0.25);
                glut.glutWireCube(1); // Dedo
            gl.glPopMatrix();
            
            
            //Dedo2
            gl.glPushMatrix();
                gl.glTranslated(0,0,0.5-0.125);
                gl.glTranslated(1, 0.5, 0);
                gl.glRotated(g, 0, 0 ,1); 
                gl.glTranslated(0.25, -0.125, 0);
                gl.glPushMatrix();
                    gl.glScaled(0.5, 0.25, 0.25);
                    glut.glutWireCube(1); // Dedo
                gl.glPopMatrix();

                gl.glTranslated(0.25,-0.125, 0);
                gl.glRotated(-g*2, 0, 0 ,1); 
                gl.glTranslated(0.25, 0.125, 0);
                gl.glScaled(0.5, 0.25, 0.25);
                glut.glutWireCube(1); // Dedo
            gl.glPopMatrix();
            
            
            //Dedo3
            gl.glPushMatrix();
                gl.glTranslated(0,0,-0.5+0.125);
                gl.glTranslated(1, 0.5, 0);
                gl.glRotated(g, 0, 0 ,1); 
                gl.glTranslated(0.25, -0.125, 0);
                gl.glPushMatrix();
                    gl.glScaled(0.5, 0.25, 0.25);
                    glut.glutWireCube(1); // Dedo
                gl.glPopMatrix();

                gl.glTranslated(0.25,-0.125, 0);
                gl.glRotated(-g*2, 0, 0 ,1); 
                gl.glTranslated(0.25, 0.125, 0);
                gl.glScaled(0.5, 0.25, 0.25);
                glut.glutWireCube(1); // Dedo
            gl.glPopMatrix();
            
            
                  //Dedo4
            gl.glPushMatrix();
                gl.glTranslated(1, -0.5, 0);
                gl.glRotated(-g, 0, 0 ,1); 
                gl.glTranslated(0.25, 0.125, 0);
                gl.glPushMatrix();
                    gl.glScaled(0.5, 0.25, 0.25);
                    glut.glutWireCube(1); // Dedo
                gl.glPopMatrix();

                gl.glTranslated(0.25, 0.125, 0);
                gl.glRotated(g*2, 0, 0 ,1); 
                gl.glTranslated(0.25, -0.125, 0);
                gl.glScaled(0.5, 0.25, 0.25);
                glut.glutWireCube(1); // Dedo
            gl.glPopMatrix();
      
        gl.glPopMatrix();
        gl.glPopMatrix();
        }
        
        
       if(up)
          g += 0.2;
       else
           if(down)
               g -= 0.2;
                
        
        if(g> 90)
            g=90;
        else
            if(g < 0)
                g=0;
        
        
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

    
    @Override
    public void keyTyped(KeyEvent ke) {
      
    }

    @Override
    public void keyPressed(KeyEvent ke) {
      
        if(ke.getKeyCode() == KeyEvent.VK_UP)
            up = true;
        
        if(ke.getKeyCode() == KeyEvent.VK_DOWN)
            down = true;
        
        if (ke.getKeyCode() == KeyEvent.VK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
            shot = true;
        }
                
    }

    @Override
    public void keyReleased(KeyEvent ke) {

        if(ke.getKeyCode() == KeyEvent.VK_UP)
            up = false;
        
        if(ke.getKeyCode() == KeyEvent.VK_DOWN)
            down = false;
        if (ke.getKeyCode() == KeyEvent.VK_SPACE || ke.getKeyCode() == KeyEvent.VK_ENTER) {
            shot = false;
        }

    }

}



