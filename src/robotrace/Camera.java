package robotrace;

/**
 * Implementation of a camera with a position and orientation. 
 */
class Camera {

    /** The position of the camera. */
    public Vector eye = new Vector(3f, 6f, 5f);

    /** The point to which the camera is looking. */
    public Vector center = Vector.O;

    /** The up vector. */
    public Vector up = Vector.Z;

    /**
     * Updates the camera viewpoint and direction based on the
     * selected camera mode.
     */
    public void update(GlobalState gs, Robot focus) {

        switch (gs.camMode) {
            
            // First person mode    
            case 1:
                setFirstPersonMode(gs, focus);
                break;
                
            // Default mode    
            default:
                setDefaultMode(gs);
        }
    }

    /**
     * Computes eye, center, and up, based on the camera's default mode.
     */
    private void setDefaultMode(GlobalState gs) {
        //change the position of the center
        center = gs.cnt;
        up = Vector.Z;
        
        //compute the Eye postion with respect to (0,0,0)
        Vector eye0 = new Vector(
                gs.vDist * Math.cos(gs.phi)* Math.cos(gs.theta),
                gs.vDist * Math.cos(gs.phi) * Math.sin(gs.theta),
                gs.vDist * Math.sin(gs.phi)
        );
        //eye position with respect to gs.cnt
        //TODO not sure if is addtion
        this.eye = gs.cnt.add(eye0);
    }

    /**
     * Computes eye, center, and up, based on the first person mode.
     * The camera should view from the perspective of the robot.
     */
    private void setFirstPersonMode(GlobalState gs, Robot focus) {
                //the eye point should be at the position of the robot
        eye = focus.position;             
        //move it along the z axis to match the head (TODO: height is hardcoded)
        eye = eye.add(new Vector(0, 0, 1.2));   
        //the up vector is along the z axis
        up = Vector.Z;                                                       

        // center is is the direction of the robot
        center = focus.direction;    
        //normalize, scale it and add the position of the robot to find the center
        center.normalized().scale(gs.vDist);                                    
        center = eye.add(center); 
    }
}
