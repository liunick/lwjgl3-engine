package tester;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFWErrorCallback.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLXARBCreateContext;

import constants.Constants;
import entities.Camera;
import entities.Entity;
import entities.Light;
import math.Vector3f;
import model.RawModel;
import model.TexturedModel;
import render.Loader;
import render.MasterRenderer;
import render.OBJLoader;
import render.Window;
import shaders.StaticShader;
import texture.ModelTexture;

public class Main {
	
	private static long lostTime = System.nanoTime();
	private static long currTime = lostTime;
	private static long timer = System.currentTimeMillis();
	private static double delta = 0.0;
	private static int fps = 0;
	private static int ups = 0;
	private static long windowID;
	private static long variableYieldTime;
	private static long lastTime;
	
	public static void main(String[] args) {		
		windowID = Window.createWindow("Project");
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		MasterRenderer renderer = new MasterRenderer(shader);
		//RawModel rm = loader.loadToVAO(vertices, textureCoords, indices); 
		RawModel rm = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		TexturedModel tm = new TexturedModel(rm, texture);
		Light light = new Light(new Vector3f(0,20, -1), new Vector3f(1, 1, 1));
		Entity entity = new Entity(tm, new Vector3f(0, 0, -25f), 0,0,0,1);
		Camera camera = new Camera(windowID);
		
		//Main Game Loop
		while(!Window.shouldClose(windowID)) {
			
			//entity.increasePosition(0.0000f, 1f, 0f);
			entity.increaseRotation(0f, 1f, 0f);
			camera.move();
			updateTimer();	
			
			renderer.render();
			shader.start();	
			shader.loadLight(light);
			shader.loadViewMatrix(camera);
			renderer.renderEntity(entity, shader);
			
			shader.stop();
			Window.render(windowID);	
		}
		Window.cleanUp(windowID);
		renderer.cleanUp();
		loader.cleanUp();
		shader.cleanUp();
		
		
	}
	
	public static void updateTimer() {
		currTime = System.nanoTime();
		delta += (currTime - lostTime) / Constants.NS;
		lostTime = currTime;	
		while (delta >= 1.0) {
			glfwPollEvents();
			ups++;
			delta--;
		}
		fps++;		
		if (System.currentTimeMillis() > timer + 1000) {
			Window.setTitle(windowID, "ups: " + ups + ", fps: " + fps);
			ups = 0;
			fps = 0;
			timer += 1000;
		}
	}
}
