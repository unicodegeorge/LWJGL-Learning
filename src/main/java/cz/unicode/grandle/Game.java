package cz.unicode.grandle;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Game {
    private static final float[] vertices = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,

            0.5f, 0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,

    };

    private static final float[] playerCube = {
            -0.25f, -0.25f, 0.0f,
            0.25f, -0.25f, 0.0f,
            0.25f, 0.25f, 0.0f,

            0.25f, 0.25f, 0.0f,
            -0.25f, 0.25f, 0.0f,
            -0.25f, -0.25f, 0.0f,

    };


    private static int vboId;
    private static int vaoId;
    private static long window1;
    private static int vboId2;
    private static int vaoId2;

    private static int playerVao;
    private static int playerVbo;
    private static byte[] data;
    private static ByteBuffer pixels;
    private static boolean wasdControl = false;

    public static void init(long window) {
        String text = "ABCD";
        int s = 256; //Take whatever size suits you.
        BufferedImage b = new BufferedImage(s, s, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = b.createGraphics();
        g.drawString(text, 0, 0);

        int co = b.getColorModel().getNumComponents();

        data = new byte[co * s * s];
        b.getRaster().getDataElements(0, 0, s, s, data);

        pixels = BufferUtils.createByteBuffer(data.length);
        pixels.put(data);
        pixels.rewind();

        Shader.initShaders();
        vaoId = GL33.glGenVertexArrays();
        vboId = GL33.glGenBuffers();
        vaoId2 = GL33.glGenVertexArrays();
        vboId2 = GL33.glGenBuffers();

        playerVao = GL33.glGenVertexArrays();
        playerVbo = GL33.glGenBuffers();


        window1 = window;
        GL33.glBindVertexArray(playerVao);
        {
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, playerVbo);
            FloatBuffer fb = BufferUtils.createFloatBuffer(playerCube.length)
                    .put(playerCube)
                    .flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(0);

        }


        GL33.glBindVertexArray(vaoId);
        {

            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId);
            {
                FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                        .put(vertices)
                        .flip();

                GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
                GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
                GL33.glEnableVertexAttribArray(0);


                MemoryUtil.memFree(fb);
            }
            //Setup texture
            //Setup coords
            //Setup matrix
        }

        GL33.glBindVertexArray(vaoId2);
        {

            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId2);
            {
                FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                        .put(vertices)
                        .flip();

                GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
                GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
                GL33.glEnableVertexAttribArray(0);


                MemoryUtil.memFree(fb);
            }
            //Setup texture
            //Setup coords
            //Setup matrix
        }


    }

    public static void render() {
        GL33.glUseProgram(Shader.shaderId);

        if (!wasdControl) {
            GL33.glBindVertexArray(vaoId);
            GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 3);
            GL33.glBindVertexArray(vaoId2);
            GL33.glDrawArrays(GL33.GL_TRIANGLES, 3, 3);
        }

        if (wasdControl) {
            GL33.glBindVertexArray(playerVao);
            GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 3);
            GL33.glDrawArrays(GL33.GL_TRIANGLES, 3, 3);
        }

    }

    public static void update() throws InterruptedException {
        GL33.glBindVertexArray(playerVao);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, playerVbo);
        if (GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {

            playerCube[1] += 0.0001;
            playerCube[4] += 0.0001;
            playerCube[7] += 0.0001;
            playerCube[10] += 0.0001;
            playerCube[13] += 0.0001;
            playerCube[16] += 0.0001;
            FloatBuffer ds = BufferUtils.createFloatBuffer(playerCube.length)
                    .put(playerCube)
                    .flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, ds, GL33.GL_STATIC_DRAW);
        } else if (GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
            playerCube[1] -= 0.0001;
            playerCube[4] -= 0.0001;
            playerCube[7] -= 0.0001;
            playerCube[10] -= 0.0001;
            playerCube[13] -= 0.0001;
            playerCube[16] -= 0.0001;
            FloatBuffer ds = BufferUtils.createFloatBuffer(playerCube.length)
                    .put(playerCube)
                    .flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, ds, GL33.GL_STATIC_DRAW);
        } else if (GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
            playerCube[0] += 0.0001;
            playerCube[3] += 0.0001;
            playerCube[6] += 0.0001;
            playerCube[9] += 0.0001;
            playerCube[12] += 0.0001;
            playerCube[15] += 0.0001;
            FloatBuffer ds = BufferUtils.createFloatBuffer(playerCube.length)
                    .put(playerCube)
                    .flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, ds, GL33.GL_STATIC_DRAW);
        } else if (GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
            playerCube[0] -= 0.0001;
            playerCube[3] -= 0.0001;
            playerCube[6] -= 0.0001;
            playerCube[9] -= 0.0001;
            playerCube[12] -= 0.0001;
            playerCube[15] -= 0.0001;
            FloatBuffer ds = BufferUtils.createFloatBuffer(playerCube.length)
                    .put(playerCube)
                    .flip();
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, ds, GL33.GL_STATIC_DRAW);
            // TODO: Diagonal movement
//        } else if(GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS && GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
//            playerCube[0] -= 0.0001;
//            playerCube[3] -= 0.0001;
//            playerCube[6] -= 0.0001;
//            playerCube[9] -= 0.0001;
//            playerCube[12] -= 0.0001;
//            playerCube[15] -= 0.0001;
//            playerCube[1] += 0.0001;
//            playerCube[4] += 0.0001;
//            playerCube[7] += 0.0001;
//            playerCube[10] += 0.0001;
//            playerCube[13] += 0.0001;
//            playerCube[16] += 0.0001;
//
//            FloatBuffer ds = BufferUtils.createFloatBuffer(playerCube.length)
//                    .put(playerCube)
//                    .flip();
//            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, ds, GL33.GL_STATIC_DRAW);
//        }
        }
            if (GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_BACKSPACE) == GLFW.GLFW_PRESS) {
                Thread.sleep(100);
                wasdControl = !wasdControl;
            }
            if (GLFW.glfwGetKey(window1, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
                GL33.glBindVertexArray(vaoId);
                {
                    GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId);
                    if (vertices[0] >= 1.0f) {
                        vertices[0] = -2.0f;
                        vertices[3] = -1.0f;
                        vertices[6] = -1.0f;
                    }
                    vertices[0] += 0.0001;
                    vertices[3] += 0.0001;
                    vertices[6] += 0.0001;

                    FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                            .put(vertices)
                            .flip();

                    GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
                }

                GL33.glBindVertexArray(vaoId2);
                {
                    GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboId2);
                    if (vertices[15] >= 1.0f) {
                        vertices[15] = -2.0f;
                        vertices[12] = -2.0f;
                        vertices[9] = -1.0f;
                    }
                    vertices[9] += 0.0001;
                    vertices[12] += 0.0001;
                    vertices[15] += 0.0001;

                    FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                            .put(vertices)
                            .flip();

                    GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);

                }


            }


        }
    }
