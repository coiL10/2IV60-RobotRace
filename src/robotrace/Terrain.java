package robotrace;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * Represents the terrain, to be implemented according to the Assignments.
 */
class Terrain {

    
    
    public Terrain() {
        
    }

    /**
     * Draws the terrain.
     */
    public void draw(GL2 gl, GLU glu, GLUT glut) {
        double dx = .5;                                                        
        double dy = .5;                                                         
                                                               
        gl.glColor3f(1f, 0f, 0.5f);                                             
        for(double x = -20; x< 20; x+= dx){
            for(double y = -20; y< 20; y+= dy){
                //define the points 
                Vector p1 = new Vector(x, y, getHeight(x, y));
                Vector p2 = new Vector(x+dx, y, getHeight(x+dx, y));
                Vector p3 = new Vector(x, y+dy, getHeight(x, y+dy));            
                Vector p4 = new Vector(x+dx, y+dy, getHeight(x+dx, y+dy));              
       
                gl.glBegin(GL.GL_TRIANGLE_STRIP);                                   
                    gl.glVertex3d(p1.x(), p1.y(), p1.z());                                                            
                    gl.glVertex3d(p2.x(), p2.y(), p2.z());                                                      
                    gl.glVertex3d(p3.x(), p3.y(), p3.z());                        
                    gl.glVertex3d(p4.x(), p4.y(), p4.z());                                                            
                                        
               gl.glEnd();
            }  
        }
    }
    
      /**
     * The height function used so we can get a hilly terrain
     */
    public double getHeight(double x, double y) {           
        return 0.6*Math.cos(0.3*x+0.2*y)+0.4*Math.cos(x-0.5*y);                 //using the height function given in the assignment description
    }
   
}
