// 'time' contains seconds since the program was linked.
uniform float time;
varying vec3 N, P;

vec4 shading(vec3 P, vec3 N, gl_LightSourceParameters light, gl_MaterialParameters mat) {
    vec4 result  = vec4(0,0,0,1);   // Opaque black

	// Compute vector towards light source
    vec3 L = normalize(vec3(light.position - vec4(P,0.0)));

	// Compute diffuse contribution
	
	vec4 diffuseContribution = light.diffuse * mat.diffuse * dot(L, N);
	result += diffuseContribution; 


    vec3 E = normalize(-P.xyz);   // Compute the position of camera in view space - position of the point
    vec3 V = reflect(-L, N);   // Compute direction towards viewer
    
	// Compute specular contribution
	
		// The specular contribution is the product between the specular reflection coefficient, 
		// the specular material coefficient and the cosine of the angle at the power of shininess
		// dot( L, N) is the cosine of the angle between the vector towards light source and the normal in view space
	if( dot( L, N) >= 0.0){
		vec4 specularContribution = light.specular*mat.specular*pow(max(dot(E, V), 0.0), mat.shininess);
		result += specularContribution;
	}
	
    return result;
}


void main()
{
    gl_MaterialParameters mat = gl_FrontMaterial;
	gl_LightSourceParameters light = gl_LightSource[0];
		
	// Adapt the FragColor according to the contribution of each light source
	gl_FragColor = shading(P, N, light, mat);
	
}
