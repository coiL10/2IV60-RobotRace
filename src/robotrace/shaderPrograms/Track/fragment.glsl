uniform sampler2D useTexture;

void main()
{
    vec4 color = texture2D(useTexture,gl_texCoord[0].st)
    gl_FragColor = color;
}
