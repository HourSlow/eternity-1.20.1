#version 150

uniform sampler2D DiffuseSampler;
uniform float InTime;
uniform vec2 InSize;
uniform float u_Fade;

in vec2 texCoord;
out vec4 fragColor;

float hash(vec2 p) {
    return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    vec2 uv = texCoord;
    float time = InTime;

    // GLITCHES
    float glitchInterval = floor(time * 4.0);
    if (hash(vec2(glitchInterval, 1.0)) > 0.9) {
        float yBlock = floor(uv.y * 4.0);
        if (hash(vec2(yBlock, glitchInterval)) > 0.5) {
            uv.x += (hash(vec2(glitchInterval)) - 0.5) * 0.05 * u_Fade;
        }
    }

    vec3 baseColor = texture(DiffuseSampler, texCoord).rgb;

    vec3 effectColor = texture(DiffuseSampler, uv).rgb;

    // GRAYSCALE
    float L = dot(effectColor, vec3(0.2126, 0.7152, 0.0722));
    effectColor = vec3(L);

    // SOFT DAMPING
    effectColor *= 0.8;
    effectColor = pow(effectColor, vec3(1.2));

    // CRTV LINES
    float scanline = sin(uv.y * 450.0 + (time * 4.0)) * 0.02 * u_Fade;
    effectColor -= scanline;

    // VIGNETTE
    vec2 centeredUV = texCoord - 0.5;
    float vignette = 1.0 - dot(centeredUV, centeredUV) * (2.5 * u_Fade);
    effectColor *= vignette;

    // MASTER BLEND (INTRO/OUTRO)
    vec3 finalColor = mix(baseColor, effectColor, u_Fade);

    fragColor = vec4(finalColor, 1.0);
}