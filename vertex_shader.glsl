#version 330 core
layout (location = 0) in vec3 aPos;

void main() {
    // TODO: Do some transformations
    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 0);
}