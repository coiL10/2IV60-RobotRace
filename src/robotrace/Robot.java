package robotrace;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2ES3.GL_QUADS;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;

/**
* Represents a Robot, to be implemented according to the Assignments.
*/
class Robot {
    
    /** The position of the robot. */
    public Vector position = new Vector(0, 0, 0);
    
    /** The direction in which the robot is running. */
    public Vector direction = new Vector(1, 0, 0);

    /** The material from which this robot is built. */
    private final Material material;
    
    private final static float TORSO_HEIGHT = 0.5f;
    private final static float TORSO_WIDTH = 0.4f;
    private final static float TORSO_THICKNESS = 0.2f;

    private final static float NECK_HEIGHT = 0.1f;
    private final static float NECK_HALF_WIDTH = 0.1f;
    
    private final static float HEAD_HALF_WIDTH = 0.1f;
    
    private final static float ARM_WIDTH = 0.1f;
    private final static float UPPER_ARM_LEGTH = 0.25f;
    private final static float LOWER_ARM_LENGTH = 0.35f;
    
    private final static float LEG_WIDTH = 0.15f;
    private final static float UPPER_LEG_LEGTH = 0.35f;
    private final static float LOWER_LEG_LENGTH = 0.45f;
   
    private final static float speed = 40;
  

    
    /**
     * Constructs the robot with initial parameters.
     */
    public Robot(Material material
            
    ) {
        this.material = material;

        
    }

    /**
     * Draws this robot (as a {@code stickfigure} if specified).
     */
    public void draw(GL2 gl, GLU glu, GLUT glut, float tAnim) {
        //draw torso neck and head
        
        drawTorso(gl,glu,glut,tAnim);
        float armOffset = TORSO_WIDTH/2.0f + ARM_WIDTH/2.0f;
        float legOffset = TORSO_WIDTH/2.0f - LEG_WIDTH/2.0f;
        //draw right arm
        drawArm(gl,glu,glut,tAnim,armOffset);
        //draw left arm
        drawArm(gl,glu,glut,tAnim,-armOffset);
        //draw right leg
        drawLeg(gl,glu,glut,tAnim,legOffset);
        //draw legt leg
        drawLeg(gl,glu,glut,tAnim,-legOffset);
        
        
    }
    
    public void drawTorso(GL2 gl, GLU glu, GLUT glut, float tAnim){
        this.material.setMaterial(gl);
        gl.glPushMatrix();
        
        gl.glScalef(TORSO_WIDTH,TORSO_THICKNESS,TORSO_HEIGHT);
        //draw a meter cube torso
        glut.glutSolidCube(1f);
        //undo
        gl.glScalef(1.0f/TORSO_WIDTH,1.0f/TORSO_THICKNESS,1.0f/TORSO_HEIGHT);
        //put the coordinate system on top of the torse
        gl.glTranslatef(0f, 0f,TORSO_HEIGHT/2.0f);
        
        drawNeck(gl,glu,glut,tAnim);
        gl.glPopMatrix();        
    }
    public void drawNeck(GL2 gl, GLU glu, GLUT glut, float tAnim){
       
        
        glut.glutSolidCylinder(NECK_HALF_WIDTH, NECK_HEIGHT, 20, 20);
        gl.glTranslatef(0f,0f,NECK_HEIGHT);
        
        drawHead(gl,glu,glut,tAnim);
    }
    public void drawHead(GL2 gl, GLU glu, GLUT glut, float tAnim){
      
       
        gl.glTranslatef(0f, 0f,0.01f);
        glut.glutSolidSphere(HEAD_HALF_WIDTH, 20, 20);
      
    }
    
    public void drawArm(GL2 gl, GLU glu, GLUT glut, float tAnim,float offset){
      
        gl.glPushMatrix();
        gl.glTranslatef(offset, 0f,TORSO_HEIGHT/2.0f);
        //If right arm
        if(offset < 0){
                rotateArm(tAnim/10, Vector.X,gl,1);
        }
        //left arm
        else{
            rotateArm(tAnim/10, Vector.X,gl,-1);
        }
        drawUpperPartArm(gl,glu,glut,tAnim,offset);
    
        gl.glPopMatrix();        
        
    }
     public void rotateArm(float t, Vector axis, GL2 gl, int direction)
    {
        gl.glRotated(Math.sin(t * speed) * (45*direction), axis.x(), axis.y(), axis.z());
    }
     
     
    public void drawUpperPartArm(GL2 gl, GLU glu, GLUT glut, float tAnim,float offset){
        
        
        gl.glTranslatef(0f, 0f,-UPPER_ARM_LEGTH/2);
        gl.glScalef(ARM_WIDTH,ARM_WIDTH,UPPER_ARM_LEGTH);
        glut.glutSolidCube(1f);
        //undo
        gl.glScalef(1.0f/ARM_WIDTH,1.0f/ARM_WIDTH,1.0f/UPPER_ARM_LEGTH);
        //put the coordinate system at the end of upper part
        gl.glTranslatef(0f, 0f,-UPPER_ARM_LEGTH/2);
        gl.glRotated(30, 1.0, 0.0, 0.0);
        drawLowerPartArm(gl,glu,glut,tAnim,offset);
       
    }
    
    public void drawLowerPartArm(GL2 gl, GLU glu, GLUT glut, float tAnim,float offset){
        gl.glTranslatef(0f, 0f,-LOWER_ARM_LENGTH/2);
        gl.glScalef(ARM_WIDTH,ARM_WIDTH,LOWER_ARM_LENGTH);
        glut.glutSolidCube(1f);
    }
    
    public void drawLeg(GL2 gl, GLU glu, GLUT glut, float tAnim,float offset){

        gl.glPushMatrix();
        gl.glTranslatef(offset, 0f,-TORSO_HEIGHT/2.0f);
        //If right leg
        if(offset < 0){
                rotateLeg(tAnim/10, Vector.X,gl,1);
        }
        //left right
        else{
            rotateLeg(tAnim/10, Vector.X,gl,-1);
        }
        drawUpperPartLeg(gl,glu,glut,tAnim,offset);
    
        gl.glPopMatrix();
    }
    
     public void rotateLeg(float t, Vector axis, GL2 gl, int direction)
    {
        gl.glRotated(Math.sin(t * speed) * (45*direction), axis.x(), axis.y(), axis.z());
    
    }
    
    public void drawUpperPartLeg(GL2 gl, GLU glu, GLUT glut, float tAnim,float offset){
        
        
        gl.glTranslatef(0f, 0f,-UPPER_LEG_LEGTH/2);
        gl.glScalef(LEG_WIDTH,LEG_WIDTH,UPPER_LEG_LEGTH);
        glut.glutSolidCube(1f);
        //undo
        gl.glScalef(1.0f/LEG_WIDTH,1.0f/LEG_WIDTH,1.0f/UPPER_LEG_LEGTH);
        //put the coordinate system at the end of upper part
        gl.glTranslatef(0f, 0f,-UPPER_LEG_LEGTH/2);
        gl.glRotated(-30, 1.0, 0.0, 0.0);
        drawLowerPartLeg(gl,glu,glut,tAnim,offset);
       
    }
    
    public void drawLowerPartLeg(GL2 gl, GLU glu, GLUT glut, float tAnim,float offset){
        gl.glTranslatef(0f, 0f,-LOWER_LEG_LENGTH/2);
        gl.glScalef(LEG_WIDTH,LEG_WIDTH,LOWER_LEG_LENGTH);
        glut.glutSolidCube(1f);
    }
    
    
    
}
