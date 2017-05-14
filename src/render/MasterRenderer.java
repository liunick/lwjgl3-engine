package render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Camera;
import entities.Entity;
import entities.Light;
import math.Matrix4f;
import model.TexturedModel;
import shaders.StaticShader;

public class MasterRenderer {
	private StaticShader shader;
	private Renderer renderer;
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Matrix4f projectionMatrix;
	
	public void render(Light light, Camera camera) {
		renderer.prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		renderer.renderEntities(entities);
		shader.stop();
		entities.clear();
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
	}
	
	
}
