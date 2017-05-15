package render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import constants.Constants;
import entities.Camera;
import entities.Entity;
import entities.Light;
import math.MathUtils;
import math.Matrix4f;
import math.Vector3f;
import model.TexturedModel;
import shaders.StaticShader;
import shaders.TerrainShader;
import terrains.Terrain;

public class MasterRenderer {
	private StaticShader shader = new StaticShader();
	private TerrainShader terrainShader = new TerrainShader();
	private EntityRenderer renderer;
	private TerrainRenderer terrainRenderer;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	private Matrix4f projectionMatrix;
	private Vector3f skyColor = new Vector3f(Constants.BACKGROUND_R, Constants.BACKGROUND_G, Constants.BACKGROUND_B);
	
	public MasterRenderer() {
		init();
		projectionMatrix = MathUtils.createProjectionMatrix(Constants.FOV, Constants.NEAR_PLANE, Constants.FAR_PLANE);
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}
	
	public static void enableCull() {
		GL11.glEnable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCull() {
		GL11.glDisable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public void render(Light light, Camera camera) {
		prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		shader.loadSkyColor(Constants.BACKGROUND_R, Constants.BACKGROUND_G, Constants.BACKGROUND_B);
		renderer.renderEntities(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.loadLight(light);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadSkyColor(Constants.BACKGROUND_R, Constants.BACKGROUND_G, Constants.BACKGROUND_B);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		terrains.clear();
		entities.clear();
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity) {
		TexturedModel tm = entity.getTm();
		List<Entity> batch = entities.get(tm);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(tm, newBatch);
		}
	}
	
	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	/**
	 * Prepare the FrameBuffer
	 * Clears the FrameBuffer to a color
	 */
	public void prepare() {
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(Constants.BACKGROUND_R, Constants.BACKGROUND_G, Constants.BACKGROUND_B, Constants.BACKGROUND_A);							//Clear the color buffers to a certain set color: 0.3f, 0.95f, 0.8f, 1.0
	}
	
	/**
	 * Initialize OpenGL to enable Framebuffer characteristics
	 */
	private void init() {																//Allows OpenGL's framebuffer to sort whether objects are in front of each other
		GL11.glEnable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_BACK);
	}
	
}
