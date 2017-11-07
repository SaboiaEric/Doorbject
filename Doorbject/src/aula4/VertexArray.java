package aula4;

import com.sun.prism.impl.BufferUtil;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;
import java.util.Random;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

public class VertexArray implements GLEventListener {

    GLU glu = new GLU();
    final int pontos = 6;
    float[] colorsData;
    float[] pointsData;

    FloatBuffer points;
    FloatBuffer colors;

    Random random = new Random();
    private static int SIZE_3 = 3;

    /**
     * ***************************
     */

    public VertexArray() {
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
        gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL2.GL_COLOR_ARRAY);
        /**
         * ******************************************
         */

        initArrayData(gl);
    }

    public void display(GLAutoDrawable gLAutoDrawable) {
        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        draw(gl);
    }

    public void draw(GL2 gl) {

        gl.glColorPointer(SIZE_3,  GL.GL_FLOAT, 0, colors);
        gl.glVertexPointer(SIZE_3, GL.GL_FLOAT, 0, points);
        gl.glDrawArrays(GL.GL_TRIANGLES, 0, 6);
    }

    private void initArrayData(GL gl) {

        int nbValues = pontos * 3;

        pointsData = new float[nbValues];
        colorsData = new float[nbValues];

        pointsData[0] = 0;
        pointsData[1] = 0;
        pointsData[2] = 0;

        pointsData[3] = 2;
        pointsData[4] = 0;
        pointsData[5] = 0;

        pointsData[6] = 1;
        pointsData[7] = 2;
        pointsData[8] = 0;

        pointsData[9] = 2;
        pointsData[10] = 2;
        pointsData[11] = 2;

        pointsData[12] = 4;
        pointsData[13] = 2;
        pointsData[14] = 2;

        pointsData[15] = 3;
        pointsData[16] = 4;
        pointsData[17] = 2;

        for (int i = 0; i < 18; i++) {
            colorsData[i] = (float) Math.random();
        }

        // Points.
        points = BufferUtil.newFloatBuffer(nbValues);
        points.put(pointsData, 0, nbValues);
        points.rewind();

        // Colors.
        colors = BufferUtil.newFloatBuffer(nbValues);
        colors.put(colorsData, 0, nbValues);
        colors.rewind();

    }

    @Override
    public void reshape(GLAutoDrawable gLAutoDrawable, int x, int y, int w, int h) {

        GL2 gl = gLAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, 1, 1, 30);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0, 0, -15);

    }

    public void displayChanged(GLAutoDrawable gLAutoDrawable, boolean modeChanged, boolean deviceChanged) {

    }

    public static void main(String args[]) {
        new VertexArray();
    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

}
