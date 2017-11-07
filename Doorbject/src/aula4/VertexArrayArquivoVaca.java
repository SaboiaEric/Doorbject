package aula4;


import com.jogamp.opengl.util.Animator;
import com.sun.prism.impl.BufferUtil;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

public class VertexArrayArquivoVaca implements GLEventListener {
    GLU glu = new GLU();

    /* Novo */
    int pontos;
    int faces;
    float[] pointsData;
    float[] colorsData;

    
    int  [] index;
    
    FloatBuffer points[];
    FloatBuffer colors[];
    IntBuffer   indexs[];
    
    
    float ry;
    
    
    float  cor[][];
    private int partes;
    
    public VertexArrayArquivoVaca() {
        GLJPanel canvas = new GLJPanel();
        canvas.addGLEventListener(this);
        
        JFrame frame = new JFrame("Quadrado");
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
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        
        //Novo
        gl.glEnableClientState( GL2.GL_VERTEX_ARRAY );
        gl.glEnableClientState( GL2.GL_COLOR_ARRAY );


        //gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);
        /*********************************************/
        
	gl.glEnable(GL.GL_DEPTH_TEST);

        initArrayData(gl);
        
        gl.glClearColor(0.1f,0.9f,0.1f,1);
        
        Animator a = new Animator(gLAutoDrawable);
        a.start();
    }
    
    
    
    public void display(GLAutoDrawable gLAutoDrawable) {
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glLoadIdentity();
    

        //Habilita o depth-buffering
	gl.glEnable(GL.GL_DEPTH_TEST);
    
        gl.glTranslated(0,-1,-7);
            
        gl.glRotated(ry,0,1,0);
       
        ry+=0.1;
        draw(gl);
        
    }
    
    
    public void draw( GL2 gl ) {
       

     for(int i=0; i < partes; i++){
        gl.glColorPointer( 3, GL.GL_FLOAT, 0, colors[i] );
        gl.glVertexPointer( 3, GL.GL_FLOAT,0,points[i]);
        gl.glDrawElements(GL.GL_TRIANGLES,indexs[i].capacity(),GL.GL_UNSIGNED_INT,indexs[i]);
     }   

    }
    
    
    
    
    private void initArrayData( GL2 gl ) {
        
        
        try {
            
            RandomAccessFile model = new RandomAccessFile("c:/cowCor.txt","r");
            
            String linha;
            String v[];
            partes  = Integer.parseInt(model.readLine());
            
            points =  new FloatBuffer[partes];
            indexs =  new IntBuffer[partes];
            colors =  new FloatBuffer[partes];
            cor = new float[partes][3];
            
            for(int p=0; p < partes;p++) 
            {
                
                pontos = Integer.parseInt(model.readLine().trim());
                
                int size = pontos * 3;
                
                pointsData = new float[ size ];
                colorsData = new float [size];
                
                linha = model.readLine().trim();
                v = linha.split(" ");
            
                cor[p][0]= Float.parseFloat(v[0]);
                cor[p][1]= Float.parseFloat(v[1]);
                cor[p][2]= Float.parseFloat(v[2]);
                
                for(int i=0, j=0; i < pontos; i++, j+=3) 
                {
                    linha = model.readLine().trim();
                    linha = linha.substring(0,linha.length()-2);
                    v = linha.split(" ");
            
                    pointsData[j  ] = Float.parseFloat(v[0]);
                    pointsData[j+1] = Float.parseFloat(v[1]);
                    pointsData[j+2] = Float.parseFloat(v[2]);


                    colorsData[j  ] = cor[p][0];
                    colorsData[j+1] = cor[p][1];
                    colorsData[j+2] = cor[p][2];
                }
                
                faces = Integer.parseInt(model.readLine().trim());
                indexs[p] =    BufferUtil.newIntBuffer(faces*3);
                
                for(int i=0; i < faces;) {
                    linha = model.readLine();
                    if(linha == null)
                        break;
                    
                    
                    v = linha.trim().split(",");
                    
                    for(int j=0; j < v.length; j++) {
                        
                        int k = Integer.parseInt(v[j].trim());
                        
                        if(k!= -1)
                              indexs[p].put(k);
                        else
                            i++;
                        
                        
                    }
                    
                    
                }
                // Points.
                points[p] = BufferUtil.newFloatBuffer( size );
                points[p].put( pointsData, 0, size );
                points[p].rewind();

                colors[p] = BufferUtil.newFloatBuffer( size );
                colors[p].put( colorsData, 0, size );
                colors[p].rewind();


                indexs[p].rewind();
                
            }
            
            
            
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        
    }
    
    
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
        
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60,1,1,60);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-25);
        
        
        
    }
    
    public void displayChanged(GLAutoDrawable gLAutoDrawable,boolean modeChanged, boolean deviceChanged) {
        
    }
    
    public static void main(String args[]) {
        new VertexArrayArquivoVaca();
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
        
    }
    
    
}

