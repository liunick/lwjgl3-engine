package utilities;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorPositionCallback extends GLFWCursorPosCallback{

	private double dy;
	private double dx;
	
	public void invoke(long windowID, double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public double getDx() {
		return dx;
	}
	
	public double getDy() {
		return dy;
	}
}
