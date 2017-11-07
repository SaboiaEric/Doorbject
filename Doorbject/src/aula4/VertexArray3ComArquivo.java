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

public class VertexArray3ComArquivo implements GLEventListener {
    GLU glu = new GLU();
    /* Novo */
    float[] colorsData;
    float[] pointsData;
    int[] indexsData;
    
    FloatBuffer points;
    FloatBuffer colors;
    IntBuffer   index;
    
    Random random = new Random();
    private double ry;
    /******************************/
    
    public VertexArray3ComArquivo() {
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
        
        Animator ani = new Animator(gLAutoDrawable);
        ani.start();


        //Novo
        gl.glEnableClientState( GL2.GL_VERTEX_ARRAY );
        gl.glEnableClientState( GL2.GL_COLOR_ARRAY );

        
        try {
            /*********************************************/
            
            
            initArrayData(gl);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    public void display(GLAutoDrawable gLAutoDrawable) {
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-15);
        gl.glRotated(ry++,0,1,0);
        
        draw(gl);
        
        
    }
    
    
    public void draw( GL gl ) {
  
        gl.glDrawElements(GL.GL_TRIANGLES,pointsData.length/3,GL.GL_UNSIGNED_INT,index);
    }
    
    private void initArrayData( GL2 gl ) throws IOException {
        
        
        try {
            
            /*
             *  Formato do arquivo
             *  PrimeiraLinha Valor 6 �  quantidade de vertices
             *  As proximas 6 linhas s�o os vertices x,y,z
             *  O proximo valor � 2 que e a quantidade dos indexes  
             *  As duas ultimas linhas sao os indices de cada triangulo desenhado. 
             *
             *
             
                6
                0,0,0
                1,0,0
                0,1,0
                3,0,0
                4,0,0
                0,1,0
                2
                0,1,2
                3,4,5

             
             */
            
            RandomAccessFile arq = new RandomAccessFile("c:/dados.txt","r");
            int quantVertices = Integer.parseInt(arq.readLine());
            
                  
            pointsData = new float[ quantVertices *3 ];
            colorsData = new float[ quantVertices *3 ];
            
          
            String vetorLinha[];
            for(int i=0; i < quantVertices; i++)
            {
                
                vetorLinha = arq.readLine().trim().split(",");
                
                pointsData[ i*3   ] = Integer.parseInt(vetorLinha[0]);
                colorsData[ i*3   ] = (float)Math.random();
   
                pointsData[ i*3+1 ] = Integer.parseInt(vetorLinha[1]);
                colorsData[ i*3+1   ] = (float)Math.random();
                
                pointsData[ i*3+2 ] = Integer.parseInt(vetorLinha[2]);
                colorsData[ i*3+2 ] = (float)Math.random();
            }
   
            int quantIndex = Integer.parseInt(arq.readLine());
            index = BufferUtil.newIntBuffer(quantIndex * 3);
            indexsData=new int[quantIndex*3];

            for(int i=0; i < quantIndex; i++)
            {
                
                vetorLinha = arq.readLine().trim().split(",");
                
                indexsData[ i*3   ] = Integer.parseInt(vetorLinha[0]);
                indexsData[ i*3+1 ] = Integer.parseInt(vetorLinha[1]);
                indexsData[ i*3+2 ] = Integer.parseInt(vetorLinha[2]);
            }
        
            
            // Points.
        points = BufferUtil.newFloatBuffer( quantVertices * 3 );
        points.put( pointsData, 0, quantVertices * 3);
        points.rewind();
        gl.glVertexPointer( 3, GL.GL_FLOAT, 0, points );
       
        
        // Colors.
        colors = BufferUtil.newFloatBuffer( quantVertices * 3 );
        colors.put( colorsData, 0, quantVertices * 3 );
        colors.rewind();
        gl.glColorPointer( 3, GL.GL_FLOAT, 0, colors );
        
        index = BufferUtil.newIntBuffer(indexsData.length);
        index.put( indexsData, 0, indexsData.length);
        index.rewind();
        
        
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        
        
        
        
        
        
        
    }
    
    
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {
        
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60,1,1,30);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0,0,-15);
        
    }
    
    public void displayChanged(GLAutoDrawable gLAutoDrawable,boolean modeChanged, boolean deviceChanged) {
        
    }
    
    public static void main(String args[]) {
        new VertexArray3ComArquivo();
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    
    }
    
    
}

