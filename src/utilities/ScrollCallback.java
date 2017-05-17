package utilities;

import org.lwjgl.glfw.GLFWScrollCallback;

public class ScrollCallback extends GLFWScrollCallback{

	private double currentDy;
	
	public void invoke(long windowID, double dx, double dy) {
		currentDy = dy;
	}
	
	public double getDy() {
		return currentDy;
	}

	
}
