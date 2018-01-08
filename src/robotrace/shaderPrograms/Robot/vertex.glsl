#version 120

varying vec3 N, P;	// Varying allows us to "communicate" these variables to the fragment shader

void main() {
    N = normalize(gl_NormalMatrix*gl_Normal);   // Get the surface normal in the view space
    P = vec3(gl_ModelViewMatrix * gl_Vertex);   // Compute vertex position in 3-D view coordinates
	// Output of vertex shader
    gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_Position    = gl_ModelViewProjectionMatrix * gl_Vertex;
} 