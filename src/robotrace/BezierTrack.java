
package robotrace;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Implementation of RaceTrack, creating a track from control points for a 
 * cubic Bezier curve
 */
public class BezierTrack extends RaceTrack {
    
    private Vector[] controlPoints;

    
    BezierTrack(Vector[] controlPoints) {
        this.controlPoints = controlPoints;
        

    }
    
     /*
     * Draws this track, based on the control points.
     */
    @Override
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        for(int lane = 0; lane < 4;lane++){
            gl.glBegin(GL.GL_TRIANGLE_STRIP);
            gl.glNormal3d(0,0,1);
            double step = 0.02;
            for(double i = 0;i <= 1;i+=step){
                Vector v1 = getCurvePnt(i,lane);
                Vector v2 = getCurvePnt(i,lane+1);
                
                gl.glVertex3d(v1.x(), v1.y(),1);
                gl.glVertex3d(v2.x(),v2.y(),1);
            }
            //close the track by adding one more triagle strip
            
            
            Vector v3 = getCurvePnt(0,lane);
            Vector v4 = getCurvePnt(0,lane+1);

            gl.glVertex3d(v3.x(), v3.y(),1);
            gl.glVertex3d(v4.x(),v4.y(),1);
            
            gl.glEnd();

        }
    }
    
    /*
      Return a point on the Bezier curve
      Inputt t- parament of the curve
             lane- lane of the track
      Output Vector point on the Bezier curve
    */
    private Vector getCurvePnt(double t,int lane){
        //get the nr of control points
        int nrSegments = controlPoints.length/4;
        int currentSeg = (int) Math.floor(t*nrSegments);
        //get the control points
        Vector P0 = controlPoints[currentSeg * 3 + currentSeg];
        Vector P1 = controlPoints[currentSeg * 3 + 1 + currentSeg];
        Vector P2 = controlPoints[currentSeg * 3 + 2 + currentSeg];
        Vector P3 = controlPoints[currentSeg * 3 + 3 + currentSeg];
        
        //set t to be between (0,1) for each segment
        double clumpT = (t - (((double) currentSeg) / nrSegments)) * nrSegments;     
        Vector point =  getCubicBezierPnt(clumpT, P0, P1, P2, P3);
        Vector tangent = getCubicBezierTng(clumpT, P0, P1, P2, P3).scale(-1);
        Vector normal = tangent.cross(new Vector(0,0,1)).normalized();
        
        return point.add(normal.scale(lane * LANE_WIDTH));
          
    }
    
    private Vector getCurvePntR(double t,int lane){
        //get the nr of control points
        int nrSegments = controlPoints.length/4;
        int currentSeg = (int) Math.floor(t*nrSegments);
        //get the control points
        Vector P0 = controlPoints[currentSeg * 3 + currentSeg];
        Vector P1 = controlPoints[currentSeg * 3 + 1 + currentSeg];
        Vector P2 = controlPoints[currentSeg * 3 + 2 + currentSeg];
        Vector P3 = controlPoints[currentSeg * 3 + 3 + currentSeg];
        
        //set t to be between (0,1) for each segment
        double clumpT = (t - (((double) currentSeg) / nrSegments)) * nrSegments;     
        Vector point =  getCubicBezierPnt(clumpT, P0, P1, P2, P3);
        Vector tangent = getCubicBezierTng(clumpT, P0, P1, P2, P3).scale(-1);
        Vector normal = tangent.cross(new Vector(0,0,1)).normalized();
        
        return point.add(normal.scale(lane * LANE_WIDTH +LANE_WIDTH/2.0));
          
    }
    
    
   /*
    Return a point on the spline discribed by the controll points
    input: t - parameter(0->1)
            P0,P1,P2,P3 - controll points
    return: Vector - position on the spline
    */
    protected Vector getCubicBezierPnt(double t,Vector P0,Vector P1,Vector P2,Vector P3) {
        //return a point on the spline discribed by the 
        return P0.scale((1 - t) * (1 - t) * (1 - t)).add(P1.scale(3 * t * (1 - t) * (1 - t))).add(P2.scale(3 * t * t * (1 - t))).add(P3.scale(t * t * t));
    }
    
    /*
    Return a point on the spline discribed by the controll points
    input: t - parameter(0->1)
            P0,P1,P2,P3 - controll points
    return: Vector - position on the spline
    */
    protected Vector getCubicBezierTng(double t, Vector P0, Vector P1,Vector P2, Vector P3) {
        //obtained by derivating the Bezie spline of order n=3
        return P1.subtract(P0).scale(3 * (1 - t) * (1 - t)).add(P2.subtract(P1).scale(6 * (1 - t) * t)).add(P3.subtract(P2).scale(3 * t * t));
    }
    
    @Override
    protected Vector getTangent(double t) {
        //obtained by derivating the Bezie spline of order n=3
        return Vector.O;
    }
    
     @Override
    protected Vector getPoint(double t) {
        //obtained by derivating the Bezie spline of order n=3
        return Vector.O;
    }
    
     /**
     * Returns the center of a lane at 0 <= t < 1.
     * Use this method to find the position of a robot on the track.
     */
    @Override
    public Vector getLanePoint(int lane, double t){
        
        //intitialize with the position on the lane
        Vector position = getCurvePntR(t, lane-1);
        return position;

    }
    
    @Override
    public Vector getLaneTangent(int lane, double t){
        
            //find the segment
            int numberSegments = controlPoints.length / 4;                     
            int segment = (int) Math.floor(t * numberSegments);                

            Vector P0 = controlPoints[segment * 3 + segment];                   
            Vector P1 = controlPoints[segment * 3 + 1 + segment];
            Vector P2 = controlPoints[segment * 3 + 2 + segment];
            Vector P3 = controlPoints[segment * 3 + 3 + segment];
            double newT = (t - (((double) segment) / numberSegments)) * numberSegments; 
            Vector tangent = getCubicBezierTng(newT, P0, P1, P2, P3);             
            return tangent;

    }
    

}
