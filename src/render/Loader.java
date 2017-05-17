package render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import model.RawModel;
import texture.Texture;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	public Loader() {
		
	}
	
	/**
	 * Takes in positions of a model and turns it into a raw model
	 * 1. Create a VAO
	 * 2. Store data in attribute lists
	 * 3. Unbind the VAO
	 * @param pos coordinates of the position
	 * @return RawModel
	 */
	public RawModel loadToVAO(float[] pos, float[] textureCoords, float[] normals, int[] indices) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInVAO(0, 3, pos);
		storeDataInVAO(1, 2, textureCoords);
		storeDataInVAO(2, 3, normals);
		unbindVAO();
		return new RawModel(vaoID, indices.length);
	}
	
	public int loadTexture(String fileName) {
		//C:/Users/NL/Desktop/Code/LWJGL Project/Game Files/
		Texture texture = new Texture("res/" + fileName + ".png");
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
		int textureID = texture.getID();
		textures.add(textureID);
		return textureID;
	}
	
	public void cleanUp() {
		for (Integer x : vaos)
			GL30.glDeleteVertexArrays(x);
		for (Integer x : vbos)
			GL15.glDeleteBuffers(x);
		for (Integer x : textures)
			GL11.glDeleteTextures(x);
	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoID);
		vaos.add(vaoID);
		return vaoID;
	}
	
	/**
	 * Takes in the data and assigns it to a VBO
	 * 1. Create a VBO
	 * 2. Bind the VBO
	 * 3. Store the data in a float buffer
	 * @param attribListNum
	 * @param data
	 */
	private void storeDataInVAO(int attribListNum, int coordSize, float[] data) {
		int vboID = GL15.glGenBuffers();					//Create an empty vbo
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);					//Bind the VBO w/ first param being the type of data stored
		FloatBuffer dataFloatBuffer = storeDataAsFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataFloatBuffer, GL15.GL_STATIC_DRAW);	//Stores the data in the VBO
		GL20.glVertexAttribPointer(attribListNum, coordSize, GL11.GL_FLOAT, false, 0, 0);		//Store the VBO in the attriblist number
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0); 							//0 unbinds the currently bound vertex array
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer dataIntBuffer = storeDataAsIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, dataIntBuffer, GL15.GL_STATIC_DRAW);
	}
	
	private IntBuffer storeDataAsIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private FloatBuffer storeDataAsFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
}
