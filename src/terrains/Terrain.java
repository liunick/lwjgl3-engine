package terrains;

import constants.Constants;
import model.RawModel;
import render.Loader;
import texture.ModelTexture;
import texture.TerrainTexture;
import texture.TerrainTexturePack;

public class Terrain {

	private float x;
	private float z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	
	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, TerrainTexture blendMap) {
		this.texturePack = texturePack;
		this.blendMap = blendMap;
		this.x = gridX * Constants.TERRAIN_SIZE;
		this.z = gridZ * Constants.TERRAIN_SIZE;
		model = generateTerrain(loader);
	}
	
	public float getX() {
		return x;
	}
	
	public float getZ() {
		return z;
	}
	
	public RawModel getModel() {
		return model;
	}
	
	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	private RawModel generateTerrain(Loader loader){
		int count = Constants.TERRAIN_VERTEX_COUNT * Constants.TERRAIN_VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(Constants.TERRAIN_VERTEX_COUNT-1)*(Constants.TERRAIN_VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<Constants.TERRAIN_VERTEX_COUNT;i++){
			for(int j=0;j<Constants.TERRAIN_VERTEX_COUNT;j++){
				vertices[vertexPointer*3] = (float)j/((float)Constants.TERRAIN_VERTEX_COUNT - 1) * Constants.TERRAIN_SIZE;
				vertices[vertexPointer*3+1] = 0;
				vertices[vertexPointer*3+2] = (float)i/((float)Constants.TERRAIN_VERTEX_COUNT - 1) * Constants.TERRAIN_SIZE;
				normals[vertexPointer*3] = 0;
				normals[vertexPointer*3+1] = 1;
				normals[vertexPointer*3+2] = 0;
				textureCoords[vertexPointer*2] = (float)j/((float)Constants.TERRAIN_VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)Constants.TERRAIN_VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<Constants.TERRAIN_VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<Constants.TERRAIN_VERTEX_COUNT-1;gx++){
				int topLeft = (gz*Constants.TERRAIN_VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*Constants.TERRAIN_VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}
	
}
