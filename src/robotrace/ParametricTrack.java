package robotrace;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Implementation of RaceTrack, creating a track from a parametric formula
 */
public class ParametricTrack extends RaceTrack {

    private static final double step = 0.02;

    /**
     * Draws this track, based on the control points.
     */
    @Override
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        Vector tangent = new Vector(0,0,0);
        double step = 0.02;
        for(double j= -2; j<NR_LANES-1; j++){
                gl.glBegin(gl.GL_LINE_LOOP);
                for(double i=0; i<1; i += step){
                    tangent = getTangent(i).cross(new Vector(0,0,1));
                    tangent.x = tangent.x*LANE_WIDTH;
                    tangent.y = tangent.y*LANE_WIDTH;
                  
                    gl.glVertex3d(getPoint(i).x()+j*tangent.x,getPoint(i).y+j*tangent.y,getPoint(i).z);
                }
                gl.glEnd();
        }
        
        for(double j= -2; j<3; j++){
                gl.glBegin(gl.GL_TRIANGLE_STRIP);
                double pOffset = 0.0005;
                for(double i=0; i<1; i = i+pOffset){
                    
                    Vector a = new Vector(0,0,0);
                    a = getTangent(i).cross(new Vector(0,0,1));
                    a.normalized();
                    a.x = a.x*1.22;
                    a.y = a.y*1.22;
                    gl.glColor3f(0.5f, 0.0f, 1.0f);
                    gl.glVertex3d(getPoint(i).x+2*j*a.x,getPoint(i).y+2*j*a.y,getPoint(i).z);
                    gl.glVertex3d(getPoint(i+pOffset).x+(j+1)*a.x,getPoint(i+pOffset).y+(j+1)*a.y,getPoint(i+pOffset).z);
                }
                gl.glEnd();
            }
    }

    @Override
    protected Vector getPoint(double t) {

        return new Vector((10 * Math.cos(2 * Math.PI * t)), (14 * Math.sin(2 * Math.PI * t)), 1);

    }

    @Override
    protected Vector getTangent(double t) {
        //after taking the derivative we get
        double x = -20 * Math.PI * Math.sin(2 * Math.PI * t);
        double y = 28 * Math.PI * Math.cos(2 * Math.PI * t);
        double z = 0;

        //normalize
        double len = Math.sqrt(x * x + y * y);
        x = x / len;
        y = y / len;

        return new Vector(x, y, z);
    }

}
