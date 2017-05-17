package render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import constants.Constants;

public class Window {
	
	private long lastFrameTime;
	private long lostTime = System.nanoTime();
	private long currTime = lostTime;
	private long timer = System.currentTimeMillis();
	private double delta = 0.0;
	private int fps = 0;
	private int ups = 0;
	private boolean inWire = false;
	
	private int framecount = 0;
	
	
	private long windowID;
	
	public Window(String title) {
		this.windowID = createWindow(title);
	}
	
	public long getWindowID() {
		return windowID;
	}
	
	public boolean shouldClose() {
		if (!glfwWindowShouldClose(windowID))
			return false;
		return true;
	}
	
	public void hide() {
		glfwHideWindow(windowID);
	}
	
	public void show() {
		glfwShowWindow(windowID);
	}
	
	public void cleanUp() {
		glfwHideWindow(windowID);
		glfwDestroyWindow(windowID);
	}
	
	public void update() {
    	//
    	glfwSwapBuffers(windowID);
    	updateTimer();
    	//System.out.println("------------------------------------\nFrame: " + framecount); //+framecount lags the system???
    	//framecount++;

    	
	}
	
	public void setTitle(String title) {
		glfwSetWindowTitle(windowID, title);
	}
	
    public float getDelta() {
    	return (float) delta;
    }
	
	private long createWindow(String title) {
		glfwInit();
		int display_width = Constants.DISPLAY_WIDTH;
		int display_height = Constants.DISPLAY_HEIGHT;
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_SAMPLES, GL_DONT_CARE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
		glfwWindowHint(GLFW_DECORATED, GL_TRUE);
		glfwWindowHint(GLFW_FOCUSED, GL_TRUE);
		
		long windowID = glfwCreateWindow(display_width, display_height, title, NULL, NULL);
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		int x = (vidmode.width() - display_width) / 2;
		int y = (vidmode.height() - display_height) / 2;
		glfwSetWindowPos(windowID, x, y);		
		
		glfwMakeContextCurrent(windowID);
		glfwSwapInterval(1);
		GL.createCapabilities();
		
		return windowID;
	}
	
    private void updateTimer() {    	
		currTime = System.nanoTime();
		delta += (currTime - lostTime) / Constants.NS;
		lostTime = currTime;	
		//System.out.println(delta);
		while (delta >= 1.0) {
			glfwPollEvents();
			ups++;
			delta--;
		}
		fps++;		
		if (System.currentTimeMillis() > timer + 1000) {
			setTitle("ups: " + ups + ", fps: " + fps);
			ups = 0;
			fps = 0;
			timer += 1000;
		}
	}


    

	
	
}
