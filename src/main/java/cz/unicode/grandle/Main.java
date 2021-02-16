package cz.unicode.grandle;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;

import java.sql.SQLOutput;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        GLFW.glfwInit();

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);

        long window = GLFW.glfwCreateWindow(800, 600, "LEARNING THIS IS HELLA FUN!", 0, 0);

        if (window == 0) {
            GLFW.glfwTerminate();
            throw new Exception("Unable to open window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL33.glViewport(0, 0, 800, 600);
        boolean toggledColors = true;

        boolean goingUp = true;

        float r = 0;
        float g = 0;
        float b = 0;

        Game.init(window);

        while (!GLFW.glfwWindowShouldClose(window)) {


            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
            GL33.glClearColor(0.149f, 0.149f, 0.149f, 100f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            Game.render();
            Game.update();
        }

        GLFW.glfwTerminate();

    }
}
