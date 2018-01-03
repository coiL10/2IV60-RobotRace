package robotrace;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;

/**
* Materials that can be used for the robots.
*/
public enum Material {

    /** 
     * Gold material properties.
     * Modify the default values to make it look like gold.
     */
    GOLD (
            new float[] {0.24725f, 0.1995f, 0.0745f, 1.0f},
            new float[] {0.75164f, 0.60648f, 0.22648f, 1.0f},
            new float[] {0.628281f, 0.555802f, 0.366065f, 1.0f},
            51f),

    /**
     * Silver material properties.
     * Modify the default values to make it look like silver.
     */
    SILVER (
            
        new float[] {0.192f,0.192f,0.192f,1.0f},
        new float[] {0.50754f, 0.50754f, 0.50754f, 1.0f},
        new float[] {0.508273f, 0.508273f, 0.508273f, 1.0f},
        51.2f),

    /** 
     * Orange material properties.
     * Modify the default values to make it look like orange.
     */
    ORANGE (
        new float[] {0.2f,0.1f,0.0f,1.0f},    
        new float[] {1.0f, 0.5f, 0.0f, 1.0f},
        new float[] {0.02f, 0.008f, 0.0f, 1.0f},
        0.25f
    ),

    /**
     * Wood material properties.
     * Modify the default values to make it look like Wood.
     */
    WOOD (
        new float[] {0.0f,0.05f,0.0f,1.0f},    
        new float[] {0.6484375f, 0.40625f, 0.12890625f, 1.0f},
        new float[] {0.01f, 0.01f, 0.01f, 1.0f},
        0.078f);
     /** The diffuse RGBA reflectance of the material. */
    float[] ambience;
    
    /** The diffuse RGBA reflectance of the material. */
    float[] diffuse;

    /** The specular RGBA reflectance of the material. */
    float[] specular;
    
    /** The specular exponent of the material. */
    float shininess;

    /**
     * Constructs a new material with diffuse and specular properties.
     */
    private Material(float[] ambience,float[] diffuse, float[] specular, float shininess) {
        this.ambience = ambience;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }
    public void setMaterial(GL2 gl){
       //set material properties
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_AMBIENT, ambience,0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_DIFFUSE, diffuse, 0);
        gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SPECULAR, specular, 0);
        gl.glMaterialf(GL.GL_FRONT_AND_BACK, GLLightingFunc.GL_SHININESS, shininess);
   }
}