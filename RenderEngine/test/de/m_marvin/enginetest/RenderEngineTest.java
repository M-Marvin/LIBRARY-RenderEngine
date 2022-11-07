//package de.m_marvin.enginetest;
//
//import java.io.IOException;
//
//import org.lwjgl.glfw.GLFW;
//import org.lwjgl.opengl.GL33;
//
//import de.m_marvin.renderengine.GLStateManager;
//import de.m_marvin.renderengine.buffers.BufferBuilder;
//import de.m_marvin.renderengine.buffers.BufferUsage;
//import de.m_marvin.renderengine.buffers.VertexBuffer;
//import de.m_marvin.renderengine.inputbinding.UserInput;
//import de.m_marvin.renderengine.inputbinding.bindingsource.KeySource;
//import de.m_marvin.renderengine.models.OBJLoader;
//import de.m_marvin.renderengine.models.RawModel;
//import de.m_marvin.renderengine.resources.ResourceLoader;
//import de.m_marvin.renderengine.resources.locationtemplates.ResourceLocation;
//import de.m_marvin.renderengine.shaders.ShaderInstance;
//import de.m_marvin.renderengine.shaders.ShaderLoader;
//import de.m_marvin.renderengine.textures.AbstractTextureMap;
//import de.m_marvin.renderengine.textures.utility.TextureLoader;
//import de.m_marvin.renderengine.translation.Camera;
//import de.m_marvin.renderengine.translation.PoseStack;
//import de.m_marvin.renderengine.utility.NumberFormat;
//import de.m_marvin.renderengine.vertecies.RenderPrimitive;
//import de.m_marvin.renderengine.vertecies.VertexFormat;
//import de.m_marvin.renderengine.windows.Window;
//import de.m_marvin.unimat.impl.Matrix4f;
//import de.m_marvin.univec.impl.Vec3f;
//
//public class RenderEngineTest {
//		
//	public static void main(String... args) {
//		try {
//			new RenderEngineTest().start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//	}
//	
//	public static AbstractTextureMap<ResourceLocation> texture;
//	public static long currentTickTime;
//	
//	public void start() throws IOException {
//		
//		System.out.println("start");
//		
//		GLStateManager.initialisate(System.err);
//		
//		ResourceLoader<ResourceLocation, TestSourceFolders> resourceLoader = new ResourceLoader<>();
//		ShaderLoader<ResourceLocation, TestSourceFolders> shaderLoader = new ShaderLoader<ResourceLocation, TestSourceFolders>(TestSourceFolders.SHADERS, resourceLoader);
//		TextureLoader<ResourceLocation, TestSourceFolders> textureLoader = new TextureLoader<ResourceLocation, TestSourceFolders>(TestSourceFolders.TEXTURES, resourceLoader);
//		OBJLoader<ResourceLocation, TestSourceFolders> modelLoader = new OBJLoader<ResourceLocation, TestSourceFolders>(TestSourceFolders.MODELS, resourceLoader);
//		
//		Window window2 = new Window(1000, 600, "Test");
//		window2.makeContextCurrent();
//		Camera camera = new Camera(new Vec3f(0F, 0.5F, 10F), new Vec3f(0F, 0F, 0F));
//		UserInput input = new UserInput();
//		input.attachToWindow(window2.windowId());
//
//		textureLoader.buildAtlasMapFromTextures(new ResourceLocation("test", ""), new ResourceLocation("test", "atlas_1"), false, false);
//		textureLoader.buildAtlasMapFromTextures(new ResourceLocation("test", ""), new ResourceLocation("test", "atlas_1_interpolated"), false, true);
//
//		modelLoader.loadModelsIn(new ResourceLocation("test", ""), new ResourceLocation("test", ""));
//		
//		shaderLoader.loadShadersIn(new ResourceLocation("test", ""));
//		//shaderLoader.loadShader(new ResourceLocation("test", "testShader"), new ResourceLocation("test", "shader1"), Optional.empty());
//		
//		input.registerBinding("movement.forward").addBinding(KeySource.getKey(GLFW.GLFW_KEY_W));
//		input.registerBinding("movement.backward").addBinding(KeySource.getKey(GLFW.GLFW_KEY_S));
//		input.registerBinding("movement.leftside").addBinding(KeySource.getKey(GLFW.GLFW_KEY_A));
//		input.registerBinding("movement.rightside").addBinding(KeySource.getKey(GLFW.GLFW_KEY_D));
//		input.registerBinding("movement.rotateleft").addBinding(KeySource.getKey(GLFW.GLFW_KEY_LEFT));
//		input.registerBinding("movement.rotateright").addBinding(KeySource.getKey(GLFW.GLFW_KEY_RIGHT));
//		input.registerBinding("movement.rotateup").addBinding(KeySource.getKey(GLFW.GLFW_KEY_UP));
//		input.registerBinding("movement.rotatedown").addBinding(KeySource.getKey(GLFW.GLFW_KEY_DOWN));
//		
//		VertexFormat format = new VertexFormat().appand("position", NumberFormat.FLOAT, 3, false).appand("normal", NumberFormat.FLOAT, 3, true).appand("color", NumberFormat.FLOAT, 4, false).appand("uv", NumberFormat.FLOAT, 2, false);
//		
//		BufferBuilder buffer = new BufferBuilder(64000);
//		
//		PoseStack poseStack = new PoseStack();
//		
//		poseStack.push();
//		buffer.begin(RenderPrimitive.TRIANGLES, format);
//		poseStack.translate(0, 1.5F, 0);
//		poseStack.scale(2, 2, 1);
//		buffer.vertex(poseStack, -1, -1, 2).normal(poseStack, 1, 0, 1).color(1, 1, 1, 1).uv(0, 0).endVertex();
//		buffer.vertex(poseStack, 1, -1, 2).normal(poseStack, 0, 1, 1).color(1, 1, 1, 1).uv(1, 0).endVertex();
//		buffer.vertex(poseStack, -1, 1, 2).normal(poseStack, 1, 1, 1).color(1, 1, 1, 1).uv(0, 1).endVertex();
//		buffer.vertex(poseStack, 1, -1, 2).normal(poseStack, 0, 0, 1).color(1, 1, 1, 1).uv(1, 0).endVertex();
//		buffer.vertex(poseStack, -1, 1, 2).normal(poseStack, 0, 0, 1).color(1, 1, 1, 1).uv(0, 1).endVertex();
//		buffer.vertex(poseStack, 1, 1, 2).normal(poseStack, 0, 0, 1).color(1, 1, 1, 1).uv(1, 1).endVertex();
//		buffer.index(0).index(1).index(2);//.index(3);
//		buffer.index(3).index(5).index(4);//.index(3);
//		buffer.end();
//		poseStack.pop();
//		
//		VertexBuffer vertexBuffer2 = new VertexBuffer();
//		vertexBuffer2.upload(buffer, BufferUsage.STATIC);
//		
//		Matrix4f projectionMatrix = Matrix4f.perspective(65, 1000F / 600F, 1, 1000); //Matrix4f.orthographic(-100, 100, 100, -100, -10F, 10F);
//		
//		texture = textureLoader.getTextureMap(new ResourceLocation("test", "atlas_1"));
//		
//		window2.registerWindowListener((shouldClose, resized, focused, unfocused, maximized, restored) -> {
//			if (resized.isPresent()) GLStateManager.resizeViewport(0, 0, resized.get().x(), resized.get().y());
//		});
//		
//		GLStateManager.clearColor(1, 1, 1, 0.5F);
//		GLStateManager.enable(GL33.GL_DEPTH_TEST);
//		GLStateManager.enable(GL33.GL_BLEND);
//		GLStateManager.enable(GL33.GL_CULL_FACE);
//		GLStateManager.blendFunc(GL33.GL_SRC_ALPHA, GL33.GL_ONE_MINUS_SRC_ALPHA);
//		
//		//input.addTextInputListener((character, functionalKey) -> System.out.println(character + " " + functionalKey));
//		
//		RawModel<ResourceLocation> model = modelLoader.getModel(new ResourceLocation("test", "motor_item"));
//		
//		buffer.begin(RenderPrimitive.QUADS, format);
//		AbstractTextureMap<ResourceLocation> atlas = textureLoader.getTextureMap(new ResourceLocation("test", "atlas_1"));
//		model.drawModelToBuffer((texture, vertex, normal, uv) -> {
//			atlas.activateTexture(texture);
//			buffer.vertex(vertex.x(), vertex.y(), vertex.z()).normal(normal.x(), normal.y(), normal.z()).color(1, 1, 1, 1).uv(atlas, uv.x(), uv.y()).endVertex();
//		});
//		buffer.end();
//		
//		VertexBuffer modelBuffer = new VertexBuffer();
//		modelBuffer.upload(buffer, BufferUsage.STATIC);
//		buffer.freeMemory();
//		
//		Thread testThread = new Thread(() -> {
//			
//			while (texture != null) {
//				
//				texture.nextFrame();
//				currentTickTime = System.currentTimeMillis();
//				
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
//			
//		}, "TestThread");
//		testThread.start();
//		ShaderInstance shader = shaderLoader.getShader(new ResourceLocation("test", "testShader"));
//		while (!window2.shouldClose()) {
//			
//			long currentTime = System.currentTimeMillis();
//			float partialTick = (currentTickTime + 3000 - currentTime) / 3000F;
//			
//			Vec3f motion = new Vec3f(0F, 0F, 0F);
//			float motionSensitivity = 0.2F;
//			if (input.isBindingActive("movement.forward")) motion.z = -motionSensitivity;
//			if (input.isBindingActive("movement.backward")) motion.z = +motionSensitivity;
//			if (input.isBindingActive("movement.leftside")) motion.x = -motionSensitivity;
//			if (input.isBindingActive("movement.rightside")) motion.x = +motionSensitivity;
//			camera.move(motion);
//			
//			Vec3f rotation = new Vec3f(0F, 0F, 0F);
//			float rotationSensitivity = 2F;
//			if (input.isBindingActive("movement.rotateleft")) rotation.y = +rotationSensitivity;
//			if (input.isBindingActive("movement.rotateright")) rotation.y = -rotationSensitivity;
////			if (input.isBindingActive("movement.rotateup")) rotation.x = -rotationSensitivity;
////			if (input.isBindingActive("movement.rotatedown")) rotation.x = +rotationSensitivity;
//			camera.rotate(rotation);
//			
//			camera.upadteViewMatrix();
//			Matrix4f viewMatrix = camera.getViewMatrix();
//			
//			shader.useShader();
//			shader.getUniform("ModelViewMat").setMatrix4f(viewMatrix);
//			shader.getUniform("ProjMat").setMatrix4f(projectionMatrix);
//			shader.getUniform("Texture").setTextureSampler(texture);
//			shader.getUniform("AnimMat").setMatrix3f(texture.frameMatrix());
//			shader.getUniform("AnimMatLast").setMatrix3f(texture.lastFrameMatrix());
//			shader.getUniform("Interpolation").setFloat(partialTick);
//			
//			vertexBuffer2.bind();
//			vertexBuffer2.drawAll(RenderPrimitive.TRIANGLES);
//			vertexBuffer2.unbind();
//			
//			modelBuffer.bind();
//			modelBuffer.drawAll(RenderPrimitive.QUADS);
//			modelBuffer.unbind();
//			
//			shader.unbindShader();
//			
//			window2.glSwapFrames();
//			window2.pollEvents();
//			
//		}
//		
//		vertexBuffer2.discard();
//		
//		texture = null;
//		GLStateManager.terminate();
//		
//		System.out.println("exit");
//		
//	}
//	
//}
