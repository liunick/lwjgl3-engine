package entities;

import org.lwjgl.glfw.GLFW;

import constants.Constants;
import math.Vector3f;
import model.TexturedModel;
import render.Window;

public class Player extends Entity{
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float currentJump = 0;
	private Window window;
	private long windowID;
	
	private boolean isInAir = false;
	
	public Player(TexturedModel tm, Vector3f position, float rx, float ry, float rz, float scale) {
		super(tm, position, rx, ry, rz, scale);
	}
	
	public Player(Window window, TexturedModel tm, Vector3f position, float rx, float ry, float rz, float scale) {
		super(tm, position, rx, ry, rz, scale);
		this.window = window;
		this.windowID = window.getWindowID();
	}
	
	public void move() {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed, 0);
		//System.out.println(window.getDelta());
		float distance = currentSpeed * window.getDelta();
		float dx = -(float)(distance * Math.sin(Math.toRadians(super.getRy())));
		float dz = -(float)(distance * Math.cos(Math.toRadians(super.getRy())));
		//float dy = (float)(currentJump * window.getDelta());
		super.increasePosition(dx, 0, dz);
		currentJump += Constants.ENVIRONMENT_GRAVITY;
		super.increasePosition(0, currentJump, 0);
		if(super.getPosition().y < Constants.ENVIRONMENT_HEIGHT) {
			currentJump = 0;
			isInAir = false;
			super.getPosition().y = Constants.ENVIRONMENT_HEIGHT;
		}
	}
	
	private void jump() {
		if(!isInAir) {
			this.currentJump = Constants.PLAYER_JUMP;
			isInAir = true;
		}
	}

	private void checkInputs() {
		
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_W) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_UP) == 1) {
			this.currentSpeed = Constants.PLAYER_SPEED;
		} else if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_S) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_DOWN) == 1) {
			this.currentSpeed = -Constants.PLAYER_SPEED;
		} else {
			this.currentSpeed = 0;
		}
		
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_D) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_RIGHT) == 1) {
			this.currentTurnSpeed = -Constants.PLAYER_TURN;
		} else if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_A) == 1 || GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_LEFT) == 1) {
			this.currentTurnSpeed = Constants.PLAYER_TURN;
		} else {
			this.currentTurnSpeed = 0;
		}
		
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_SPACE) == 1) {
			jump();
		} 
		
		/*
		if(GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_Q) == 1) {
			this.currentJump = Constants.PLAYER_SPEED;
		} else if (GLFW.glfwGetKey(windowID, GLFW.GLFW_KEY_E) == 1) {
			this.currentJump = -Constants.PLAYER_SPEED;
		} else {
			this.currentJump = 0;
		}*/

	}
	
}
