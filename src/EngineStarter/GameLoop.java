package EngineStarter;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Camera.Camera;
import Entity.Light;
import Loader.OBJLoader;
import Renderer.Renderer;
import Loader.TextureLoader;
import Loader.VAOLoader;
import Models.Entity;
import Models.RawModel;
import Models.TextureModel;
import Models.TexturedModel;
import Renderer.DisplayManager;
import Shaders.StaticShader;

public class GameLoop {
	
	public static void main(String [] args) throws Exception{
	
		DisplayManager.startDisplay();
		
		
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer(shader);

		
		RawModel rawModel = OBJLoader.loadObjModel("dragon");
		TextureModel textureModel = new TextureModel(TextureLoader.loadTexture("stallTexture"));
		TexturedModel staticModel = new TexturedModel(rawModel, textureModel);
		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -50), new Vector3f(0, 0, 0), 1);
		Camera camera = new Camera();
		Light lightSource = new Light(new Vector3f(0, 0, -25), new Vector3f(1, 1, 1));
		
		while(!Display.isCloseRequested()) {
			entity.increaseRotation(0, 1, 0);
			camera.move();
			shader.start();
			shader.loadLightSource(lightSource);
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		VAOLoader.cleanUp();
		TextureLoader.cleanUp();
		DisplayManager.stopDisplay();
	}

}
