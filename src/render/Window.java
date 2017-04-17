package render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import constants.Constants;

public class Window {
	
	
	public Window() {
	}
	
	public static long createWindow(String title) {
		glfwInit();
		int display_width = Constants.DISPLAY_WIDTH;
		int display_height = Constants.DISPLAY_HEIGHT;
		glfwDefaultWindowHints();
		
		//glfwWindowHint(GLFW_DEPTH_BITS, GL_TRUE);
		//glfwWindowHint(GL_DONT_CARE, GL_TRUE);
		/*glfwWindowHint(GLFW_RED_BITS, GL_TRUE);
		glfwWindowHint(GLFW_GREEN_BITS, GL_TRUE);
		glfwWindowHint(GLFW_BLUE_BITS, GL_TRUE);
		glfwWindowHint(GLFW_ALPHA_BITS, GL_TRUE);
		glfwWindowHint(GLFW_STENCIL_BITS, GL_TRUE);
		glfwWindowHint(GLFW_ACCUM_RED_BITS, GL_TRUE);
		glfwWindowHint(GLFW_ACCUM_GREEN_BITS, GL_TRUE);
		glfwWindowHint(GLFW_ACCUM_BLUE_BITS, GL_TRUE);
		glfwWindowHint(GLFW_ACCUM_ALPHA_BITS, GL_TRUE);
		glfwWindowHint(GLFW_AUX_BUFFERS, GL_TRUE);
		//glfwWindowHint(GLFW_STEREO, GL_TRUE);*/
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
	
	public static void cleanUp(long windowID) {
		glfwHideWindow(windowID);
		glfwDestroyWindow(windowID);
		
	}
	
	public static void destroy(long windowID) {
		glfwDestroyWindow(windowID);
	}
	
	public static void hide(long windowID) {
		glfwHideWindow(windowID);
	}
	
	public static void show(long windowID) {
		glfwShowWindow(windowID);
	}
	
	public static void render(long windowID) {
		glfwSwapBuffers(windowID);
	}
	
	public static void setTitle(long windowID, String title) {
		glfwSetWindowTitle(windowID, title);
	}
	
	public static boolean shouldClose(long windowID) {
		if (!glfwWindowShouldClose(windowID))
			return false;
		return true;
	}
	
}
