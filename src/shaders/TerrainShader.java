package shaders;

import entities.Camera;
import entities.Light;
import math.MathUtils;
import math.Matrix4f;
import math.Vector3f;

public class TerrainShader extends GenericShader {

	private static final String VERTEX_FILE = "src/shaders/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/terrainFragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPos;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;

	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "pos");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normals");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPos = super.getUniformLocation("lightPos");
		location_lightColor = super.getUniformLocation("lightColor");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.loadVector(location_skyColor, new Vector3f(r, g, b));
	}
	
	public void loadSpecular(float shineDamper, float reflectivity) {
		super.loadFloat(location_shineDamper, shineDamper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadLight(Light light) {
		super.loadVector(location_lightPos, light.getPos());
		super.loadVector(location_lightColor, light.getColor());
	}

	public void loadTransformationMatrix(Matrix4f mat) {
		super.loadMatrix(location_transformationMatrix, mat);
	}
	
	public void loadProjectionMatrix(Matrix4f mat) {
		super.loadMatrix(location_projectionMatrix, mat);
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f mat = MathUtils.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, mat);
	}
	
}
