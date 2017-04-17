package render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import constants.Constants;
import entities.Entity;
import math.MathUtils;
import model.RawModel;
import model.TexturedModel;
import shaders.StaticShader;

public class MasterRenderer {
	
	public MasterRenderer(StaticShader shader) {
		init();
		shader.start();
		shader.loadProjectionMatrix(MathUtils.createProjectionMatrix(Constants.FOV, Constants.NEAR_PLANE, Constants.FAR_PLANE));
		shader.stop();
	}
	
	public void render() {
		prepare();
	}
	
	public void renderEntity(Entity entity, StaticShader shader) {
		TexturedModel tm = entity.getTm();
		RawModel rm = tm.getModel();
		GL30.glBindVertexArray(rm.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
		shader.loadSpecular(tm.getTexture().getShineDamper(), tm.getTexture().getShineDamper());
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
	
	/**
	 * Prepare the FrameBuffer
	 * Clears the FrameBuffer to a color
	 */
	private void prepare() {
		glEnable(GL_DEPTH_TEST);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearColor(0.3f, 0.95f, 0.8f, 1.0f);							//Clear the color buffers to a certain set color: 0.3f, 0.95f, 0.8f, 1.0
		
		
	}
	
	/**
	 * Initialize OpenGL to enable Framebuffer characteristics
	 */
	private void init() {
												//Allows OpenGL's framebuffer to sort whether objects are in front of each other
		GL11.glEnable(GL11.GL_CULL_FACE); 
		GL11.glCullFace(GL11.GL_BACK);
		
	}
	
	public void cleanUp() {
		
	}
	
}
