package tester;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFWErrorCallback.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLXARBCreateContext;

import constants.Constants;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import math.Vector3f;
import model.RawModel;
import model.TexturedModel;
import render.Loader;
import render.MasterRenderer;
import render.EntityRenderer;
import render.OBJLoader;
import render.Window;
import shaders.StaticShader;
import terrains.Terrain;
import texture.ModelTexture;
import texture.TerrainTexture;
import texture.TerrainTexturePack;

public class Main {
	
	
	//private static long windowID;
	private static long variableYieldTime;
	private static long lastTime;
	private static Random random = new Random();
	
	
	public static void main(String[] args) {		
		//windowID = Window.createWindow("Project");
		Window window = new Window("Project");
		Loader loader = new Loader();
		
		//RawModel rm = loader.loadToVAO(vertices, textureCoords, indices); 
		RawModel rm = OBJLoader.loadObjModel("stall", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		TexturedModel tm = new TexturedModel(rm, texture);
		Light light = new Light(new Vector3f(Constants.LIGHT_X, Constants.LIGHT_Y, Constants.LIGHT_Z), new Vector3f(1, 1, 1));
		Entity entity = new Entity(tm, new Vector3f(0, 0, -25f), 0,0,0,1);
		Camera camera = new Camera(window.getWindowID());
		Player player = new Player(window, tm, new Vector3f(100,0, -50), 0, 0, 0, 1);
		
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendmap"));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);
		
		
		MasterRenderer renderer = new MasterRenderer();
		
		//Main Game Loop
		while(!window.shouldClose()) {
			//entity.increasePosition(0.0000f, 1f, 0f);
			entity.increaseRotation(0f, 1f, 0f);
			camera.move();
			player.move();
			
	
			renderer.processTerrain(terrain2);
			renderer.processTerrain(terrain);
			renderer.processEntity(entity);
			renderer.processEntity(player);
			
			renderer.render(light, camera);
			window.update();		
		}
		
		window.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();

		
		
	}
	
	
	
	
}
