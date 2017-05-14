package render;

import static org.lwjgl.opengl.GL11.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import constants.Constants;
import entities.Camera;
import entities.Entity;
import entities.Light;
import math.MathUtils;
import model.RawModel;
import model.TexturedModel;
import shaders.StaticShader;

public class Renderer {
	
	private StaticShader shader;
	
	public Renderer(StaticShader shader) {
		this.shader = shader;
		init();
		shader.start();
		shader.loadProjectionMatrix(MathUtils.createProjectionMatrix(Constants.FOV, Constants.NEAR_PLANE, Constants.FAR_PLANE));
		shader.stop();
	}
	
	/**
	 * Initialize OpenGL to enable Framebuffer characteristics
	 */
	private void init() {																//Allows OpenGL's framebuffer to sort whether objects are in front of each other
		GL11.glEnable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_BACK);
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
	
	
	public void renderEntities(Map<TexturedModel, List<Entity>> entities) {
		for(TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for (Entity entity : batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}		
	}
	
	private void prepareTexturedModel(TexturedModel model) {
		RawModel rm = model.getModel();
		GL30.glBindVertexArray(rm.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		shader.loadSpecular(model.getTexture().getShineDamper(), model.getTexture().getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entity) {
		shader.loadTransformationMatrix(MathUtils.createTransformationMatrix(entity.getPosition(), 
				entity.getRx(), entity.getRy(), entity.getRz(), entity.getScale()));
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * 
	public void render(Light light, Camera camera) {
		prepare();
		shader.start();
		shader.loadLight(light);
		shader.loadViewMatrix(camera);
		
		for (TexturedModel te : entities.keySet()) {
			
		}
		
		shader.stop();
		entities.clear();
		
	}
	
	public void renderEntity(Entity entity, StaticShader shader) {
		TexturedModel tm = entity.getTm();
		RawModel rm = tm.getModel();
		GL30.glBindVertexArray(rm.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		shader.loadSpecular(tm.getTexture().getShineDamper(), tm.getTexture().getReflectivity());
		shader.loadTransformationMatrix(MathUtils.createTransformationMatrix(entity.getPosition(), 
				entity.getRx(), entity.getRy(), entity.getRz(), entity.getScale()));
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getTm().getTexture().getID());
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, rm.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	public void renderTexturedModel(TexturedModel model) {
		RawModel rm = model.getModel();
		GL30.glBindVertexArray(rm.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rm.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public void renderRawModel(RawModel model) {
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	*/

	

}
