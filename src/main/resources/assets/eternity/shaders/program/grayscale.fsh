#version 150

uniform sampler2D DiffuseSampler;
in vec2 texCoord;
out vec4 fragColor;

void main() {
	vec4 original = texture(DiffuseSampler, texCoord);
	vec3 tintColor = vec3(0.5, 0.25, 0.25);
	vec4 result = original * vec4(tintColor, 1);

	fragColor = result;
}