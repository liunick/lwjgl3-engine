package render;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import math.Vector2f;
import math.Vector3f;
import model.RawModel;

/**
 * Can be optimized by not using the extra
 * @author NL
 *
 */
public class OBJLoader {
	public static RawModel loadObjModel(String objFile, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/" + objFile + ".obj"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(fr);
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textureCoords = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		
		try {
			while (true) {
				line = br.readLine();
				String[] splitter = line.split(" ");
				if (line.startsWith("v ")) {
					vertices.add(new Vector3f(Float.parseFloat(splitter[1]), Float.parseFloat(splitter[2]),
							Float.parseFloat(splitter[3])));
				} else if (line.startsWith("vt ")) {
					textureCoords.add(new Vector2f(Float.parseFloat(splitter[1]), Float.parseFloat(splitter[2])));
				} else if (line.startsWith("vn ")) {
					normals.add(new Vector3f(Float.parseFloat(splitter[1]), Float.parseFloat(splitter[2]),
							Float.parseFloat(splitter[3])));
				} else if (line.startsWith("f ")) {
					textureArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					verticesArray = new float[vertices.size() * 3];
					break;
				}
			}
			
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = br.readLine();
					continue;
				}
				
				String[] splitter = line.split(" ");
				String[] vertex1 = splitter[1].split("/");
				String[] vertex2 = splitter[2].split("/");
				String[] vertex3 = splitter[3].split("/");
				
				processVertex(vertex1, textureCoords, normals, indices, normalsArray, textureArray);
				processVertex(vertex2, textureCoords, normals, indices, normalsArray, textureArray);
				processVertex(vertex3, textureCoords, normals, indices, normalsArray, textureArray);
				line = br.readLine();
			}
			br.close();
		} catch(Exception e ) {
			e.printStackTrace();
		}	
		indicesArray = new int[indices.size()];
		for (int x = 0; x < indices.size(); x++) {
			indicesArray[x] = indices.get(x);
		}
		
		int vertexCounter = 0;
		for (Vector3f vec : vertices) {
			verticesArray[vertexCounter++] = vec.x;
			verticesArray[vertexCounter++] = vec.y;
			verticesArray[vertexCounter++] = vec.z;
		}
		return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);	
	}
	
	private static void processVertex(String[] data, List<Vector2f> textureCoords, List<Vector3f> normals, List<Integer> indices,
			float[] normalsArray, float[]textureArray) {
		int vertexPointer = Integer.parseInt(data[0]) - 1;
		indices.add(vertexPointer);
		Vector2f tempTextureCoord = textureCoords.get(Integer.parseInt(data[1]) - 1);
		textureArray[vertexPointer * 2] = tempTextureCoord.x;
		textureArray[vertexPointer * 2 + 1] = 1- tempTextureCoord.y;
		Vector3f tempNormalCoord = normals.get(Integer.parseInt(data[2]) - 1);
		normalsArray[vertexPointer * 3] = tempNormalCoord.x;
		normalsArray[vertexPointer * 3 + 1] = tempNormalCoord.y;
		normalsArray[vertexPointer * 3 + 2] = tempNormalCoord.z;
	}
	
}
