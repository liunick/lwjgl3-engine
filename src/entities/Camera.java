package entities;

import math.Vector3f;
import utilities.CursorPositionCallback;
import utilities.MouseButtonCallback;
import utilities.ScrollCallback;

import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.opengl.GL11;

import constants.Constants;

public class Camera {
	//private Vector3f position = new Vector3f(Constants.CAM_X, Constants.CAM_Y, Constants.CAM_Z);
	private Vector3f position = new Vector3f(100, 0, -50);
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private float pitch = 20;
	private float yaw = 0;
	private float roll = 0;
	private long windowID;
	
	private double scrollValue = 0;
	private int clickValue = -1;
	private float lastValueY = 0;
	private float lastValueX = 0;
	private double mouseDx = 0;
	private double mouseDy = 0;
	
	private boolean inWire = false;
	private boolean beingPressed = false;
	
	private Player player;
	
	private GLFWScrollCallback scrollCallback;
	private GLFWMouseButtonCallback mouseButtonCallback;
	private GLFWCursorPosCallback cursorPositionCallback = new CursorPositionCallback();
	
	public void glfwScrollCallback(long windowID, double dx, double dy) {
		scrollValue = dy;
	}
	
	public void glfwMouseButtonCallback(long windowID, int button, int action, int mods) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS) {
			clickValue = 0;
		} else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && action == GLFW.GLFW_PRESS) {
			clickValue = 1;
		} else if (button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE && action == GLFW.GLFW_PRESS) {
			clickValue = 2;
		} else {
			clickValue = -1;
		}
	}
	
	public void glfwCursorPos(long windowID, double dx, double dy) {
		mouseDx = dx;
		mouseDy = dy;
	}
	
	public Camera(long windowID, Player player) {
		this.windowID = windowID;
		this.player = player;
		GLFW.glfwSetScrollCallback(windowID, scrollCallback = GLFWScrollCallback.create(this::glfwScrollCallback));
		GLFW.glfwSetMouseButtonCallback(windowID, mouseButtonCallback = GLFWMouseButtonCallback.create(this::glfwMouseButtonCallback));
		GLFW.glfwSetCursorPosCallback(windowID, cursorPositionCallback = GLFWCursorPosCallback.create(this::glfwCursorPos));
		
	}
	
	public Camera(long windowID) {
		this.windowID = windowID;
	}
	
	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
		float theta = player.getRy() + angleAroundPlayer;
		float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(theta)));
		
		position.x = player.getPosition().x + offsetX;
		position.z = player.getPosition().z + offsetZ;
		position.y = player.getPosition().y + verticalDistance;
		
	}

	public void move() {
		
		
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		this.yaw = -(player.getRy() + angleAroundPlayer);
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom() {
		
		float zoomLevel =  (float)(scrollValue); // * 0.1f
		distanceFromPlayer -= zoomLevel;
		scrollValue = 0;
	}
	
	private void calculatePitch() {
		
		if(clickValue == 1) {
			float pitchChange = (float)mouseDy - lastValueY; // *0.1f
			pitch -= pitchChange;
		}
		if(beingPressed && !inWire && GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_P) != 1) {
			beingPressed = false;
			inWire = true;
		}
		if(beingPressed && inWire && GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_P) != 1) {
			beingPressed = false;
			inWire = false;
		}
    	if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_P) == 1 && !inWire) {
			GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
			
			beingPressed = true;
		} else if (GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_P) == 1 && inWire) {
			GL11.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
			beingPressed = true;
		}
		lastValueY = (float) mouseDy;
	}
	
	private void calculateAngleAroundPlayer() {
		
		if(clickValue == 0) {
			float angleChange = (float) mouseDx - lastValueX; // *0.1f
			angleAroundPlayer -= angleChange;
		} 
		lastValueX = (float) mouseDx;
	}
	
}
