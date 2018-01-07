package robotrace;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
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
        double step = 0.002;
        //draw lines
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
        
        //draw the track
        for(double j= -2; j<2; j++){
             gl.glBegin(gl.GL_TRIANGLE_STRIP);

             for(double i=0; i<=1 + 2*step; i = i+ 2*step){

                 tangent = getTangent(i).cross(new Vector(0,0,1));
                 tangent.x = tangent.x*LANE_WIDTH;
                 tangent.y = tangent.y*LANE_WIDTH;
                 gl.glColor3f(0.5f, 0.0f, 1.0f);
                 gl.glVertex3d(getPoint(i).x+j*tangent.x,getPoint(i).y+j*tangent.y,getPoint(i).z);
                 gl.glVertex3d(getPoint(i).x+(j+1)*tangent.x,getPoint(i).y+(j+1)*tangent.y,getPoint(i).z);
             }
             gl.glEnd();
         }
           
          //draw the sides
          for (int j = -2; j <= 2; j += 4) {                                  //only use lane curves -2 and 2, these represent the outside lanes
                gl.glBegin(GL2ES3.GL_QUADS);                                           //this time quads are used, since the 4 points lay in the same plane
                double pOffset = 0.01;                                          //set the offset, this defines how many quads are used
                for (double i = 0; i < 1; i = i + pOffset) {                    
                    Vector a = getTangent(i).cross(new Vector(0, 0, 1));        //as with the drawing of the tracks above, use vector a to later on find the correct lane
                    a.normalized();
                    a.x = a.x * LANE_WIDTH;
                    a.y = a.y * LANE_WIDTH;
                      gl.glVertex3d(getPoint(i).x + j * a.x, getPoint(i).y + j * a.y, getPoint(i).z - 2);                 //set the first vertex, located at z-1
                     gl.glVertex3d(getPoint(i).x + j * a.x, getPoint(i).y + j * a.y, getPoint(i).z);                     //set the second vertex, located at z

                    Vector b = getTangent(i + pOffset).cross(new Vector(0, 0, 1));  //as with the drawing of the tracks use vector b to find the correct lane, this time increment i again
                    b.normalized();
                    b.x = b.x * LANE_WIDTH;
                    b.y = b.y * LANE_WIDTH;
                    gl.glVertex3d(getPoint(i + pOffset).x + j * b.x, getPoint(i + pOffset).y + j * b.y, getPoint(i + pOffset).z);       //set the vertex, at point i with offset and z
                            gl.glVertex3d(getPoint(i + pOffset).x + j * b.x, getPoint(i + pOffset).y + j * b.y, getPoint(i + pOffset).z - 2);   //set final vertex at point i with offset and z-1
                }
                gl.glEnd();                                                     //finish the quad
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
    
    /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    @Override
    public Vector getLanePoint(int lane, double t){
        
        //intitialize with the position on the lane
        Vector position = getPoint(t);
        Vector tangent = getTangent(t);
        //compute the perpendicular to the curve
        Vector perpendicular = tangent.cross(new Vector(0,0,1));
        Vector centerPos = position;
        if(lane <= 2){
            centerPos = centerPos.add(perpendicular.scale(LANE_WIDTH * (lane-1) + LANE_WIDTH/2.0));
        }
        else{
            centerPos = centerPos.subtract(perpendicular.scale(LANE_WIDTH * (lane-3) + LANE_WIDTH/2.0));
        }
        return centerPos;
        

    }
    
    /**
     * Returns the tangent of a lane at 0 <= t < 1.
     * Use this method to find the orientation of a robot on the track.
     */
    @Override
    public Vector getLaneTangent(int lane, double t){
        
        return getTangent(t);

    }

}
