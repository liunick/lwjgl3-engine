package texture;

public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper  =1;
	private float reflectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean fakeLighting = false;
	
	public boolean isFakeLighting() {
		return fakeLighting;
	}


	public void setFakeLighting(boolean fakeLighting) {
		this.fakeLighting = fakeLighting;
	}


	public boolean isHasTransparency() {
		return hasTransparency;
	}


	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}


	public float getShineDamper() {
		return shineDamper;
	}


	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}


	public float getReflectivity() {
		return reflectivity;
	}


	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}


	public ModelTexture(int textureID) {
		this.textureID = textureID;
	}
	
	
	public int getID() {
		return textureID;
	}
}
