#version 120
uniform sampler2D track;

void main()
{
    vec4 color = texture2D(track,gl_TexCoord[0].st);
    gl_FragColor = color;
}
