package de.m_marvin.gframe.inputbinding.bindingsource;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.lwjgl.glfw.GLFW;

import de.m_marvin.gframe.inputbinding.IBinding;
import de.m_marvin.gframe.inputbinding.UserInput;

/**
 * Provides methods to find mouse keys and add them as argument to the {@link UserInput} bindings.
 * 
 * @author Marvin Köhler
 */
public class MouseSource {
	
	protected static Map<Integer, IBinding> bindingCache = new HashMap<>();
	
	/**
	 * IBinding instance representing a mouse key.
	 * 
	 * @author Marvin Köhler
	 */
	public static record MouseInput(int key) implements IBinding {
		
		@Override
		public boolean isPressed(long window) {
			if (UserInput.isOnUserInputThread()) {
				return GLFW.glfwGetMouseButton(window, this.key) == GLFW.GLFW_PRESS;
			} else {
				return CompletableFuture.supplyAsync(() -> {
					return GLFW.glfwGetMouseButton(window, this.key) == GLFW.GLFW_PRESS;
				}, UserInput.getUserInputExecutor()).join();
			}
		}
		
	}
	
	/**
	 * Return a {@link IBinding} for the given key.
	 * @implNote The IBinding instances get cached, means every key has only one instance.
	 * 
	 * @param key The GLFW key-id
	 * @return A IBinding instance which can be used as parameter for the {@link UserInput}
	 */
	public static IBinding getKey(int key) {
		if (!bindingCache.containsKey(key)) {
			bindingCache.put(key, new MouseInput(key));
		}
		return bindingCache.get(key);
	}
	
}
