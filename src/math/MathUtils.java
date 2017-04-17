package math;

import constants.Constants;
import entities.Camera;
import render.Window;

public class MathUtils {
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f transformationMatrix = new Matrix4f();
		transformationMatrix.setIdentity();
		transformationMatrix = transformationMatrix.multiply(Matrix4f.translate(translation.x, translation.y, translation.z));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(rx, 1, 0, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(ry, 0, 1, 0));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.rotate(rz, 0, 0, 1));
		transformationMatrix = transformationMatrix.multiply(Matrix4f.scale(scale, scale, scale));
		return transformationMatrix;
	}
	
	public static Matrix4f createProjectionMatrix(float FOV, float NEAR_PLANE, float FAR_PLANE) {
		Matrix4f projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Constants.DISPLAY_WIDTH / (float) Constants.DISPLAY_HEIGHT;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV/2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
		return projectionMatrix;
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		viewMatrix = viewMatrix.multiply(Matrix4f.rotate(camera.getPitch(), 1, 0, 0));
		viewMatrix = viewMatrix.multiply(Matrix4f.rotate(camera.getYaw(), 0, 1, 0));
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		viewMatrix  = viewMatrix.multiply(Matrix4f.translate(negativeCameraPos.x, negativeCameraPos.y, negativeCameraPos.z));
		return viewMatrix;
	}
	
}
