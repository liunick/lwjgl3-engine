package entities;

import math.Vector3f;

import org.lwjgl.glfw.GLFW;

import constants.Constants;

public class Camera {
	private Vector3f position = new Vector3f(Constants.CAM_X, Constants.CAM_Y, Constants.CAM_Z);
	
	private float pitch = 20;
	private float yaw = 10;
	private float roll = 0;
	private long windowID;
	
	
	public Camera(long windowID) {
		this.windowID = windowID;
	}

	public void move() {
		/*
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_W) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_UP) == 1) {
			position.z -= Constants.CAM_SPEED;
		}
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_D) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_RIGHT) == 1) {
			position.x += Constants.CAM_SPEED;
		}
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_A) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_LEFT) == 1) {
			position.x -= Constants.CAM_SPEED;
		}
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_S) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_DOWN) == 1) {
			position.z += Constants.CAM_SPEED;
		}
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_Q) == 1) {
			position.y += Constants.CAM_SPEED;
		}
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_E) == 1) {
			position.y -= Constants.CAM_SPEED;
		}*/
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
	
	
}
