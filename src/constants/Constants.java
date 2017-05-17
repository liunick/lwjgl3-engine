package constants;

public class Constants {

	//Display
	public static final int DISPLAY_WIDTH = 1280;
	public static final int DISPLAY_HEIGHT = 720;
	
	//Background Color
	public static final float BACKGROUND_R = 1f;
	public static final float BACKGROUND_G = 1f;
	public static final float BACKGROUND_B = 1f;
	public static final float BACKGROUND_A = 1.0f;
	
	//Terrain
	public static final int TERRAIN_VERTEX_COUNT = 128;
	public static final float TERRAIN_SIZE = 800;
	
	//Projection Matrix
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.5f;
	public static final float FAR_PLANE = 1000;
	
	//FPS/UPS
	public static final double NS = 1000000000/60.0;
	
	//Camera
	public static final float CAM_SPEED = 0.8f;
	public static final float CAM_X = 0;
	public static final float CAM_Y = 50;
	public static final float CAM_Z = 50;
	public static final float CAM_PITCH = 20;
	public static final float CAM_YAW = 10;
	public static final float CAM_ROLL = 0;
	
	//Light
	public static final float LIGHT_X = 20000;
	public static final float LIGHT_Y = 20000;
	public static final float LIGHT_Z = 2000;
	
	//Fog
	public static final float FOG_DENSITY= 0.007f;
	public static final float FOG_GRADIENT = 1.5f;
	
	//Player
	public static final float PLAYER_SPEED = 2;
	public static final float PLAYER_TURN = 5;
	public static final float PLAYER_JUMP = 3;
	
	//Environment
	public static final float ENVIRONMENT_GRAVITY = -0.2f;
	public static final float ENVIRONMENT_HEIGHT = 0;
	
}
