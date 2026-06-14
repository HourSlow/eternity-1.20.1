#version 150

uniform sampler2D DiffuseSampler;
uniform float InTime;
uniform vec2 InSize;
in vec2 texCoord;
out vec4 fragColor;

float hash(vec2 p) {
    return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    vec2 uv = texCoord;
    float time = InTime;

    // 1. GLITCHES
    float glitchInterval = floor(time * 4.0);
    if (hash(vec2(glitchInterval, 1.0)) > 0.9) {
        float yBlock = floor(uv.y * 4.0);
        if (hash(vec2(yBlock, glitchInterval)) > 0.5) {
            uv.x += (hash(vec2(glitchInterval)) - 0.5) * 0.05;
        }
    }

    vec3 sceneColor = texture(DiffuseSampler, uv).rgb;

    // 2. GRAYSCALE
    float L = dot(sceneColor, vec3(0.2126, 0.7152, 0.0722));
    sceneColor = vec3(L);

    // 4. SOFT DAMPING
    sceneColor *= 0.8;
    sceneColor = pow(sceneColor, vec3(1.2));

    // 5. CRTV LINES
    float scanline = sin(uv.y * 450.0 + (time * 4.0)) * 0.02;
    sceneColor -= scanline;

    // 6. VIGNETTE
    vec2 centeredUV = texCoord - 0.5;
    float vignette = 1.0 - dot(centeredUV, centeredUV) * 2.5;

    fragColor = vec4(sceneColor * vignette, 1.0);
}