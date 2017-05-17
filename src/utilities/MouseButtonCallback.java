package utilities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonCallback extends GLFWMouseButtonCallback {

	private long windowID;
	private int button;
	private int action;
	private int mods;
	
	public void invoke(long windowID, int arg1, int arg2, int arg3) {
		this.windowID = windowID;
		this.button = button;
		this.action = action;
		this.mods = mods;
	}
	
	public boolean isLeftButtonDown() {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS) {
			return true;
		}
		return false;
	}
	
	public boolean isRightButtonDown() {
		if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && action == GLFW.GLFW_PRESS) {
			System.out.println("here");
			return true;
		}
		return false;
	}
	
	public boolean isMiddleButtonDown() {
		if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && action == GLFW.GLFW_PRESS) {
			return true;
		}
		return false;
	}

}
