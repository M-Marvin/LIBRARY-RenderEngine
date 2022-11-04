
package de.m_marvin.renderengine.resources.locationtemplates;

import java.util.Objects;

import de.m_marvin.renderengine.resources.IResourceProvider;

/**
 * One of the two default implementations of {@link IResourceProvider}.
 * Consists of a namespace and a path.
 * The resulting path of a namespace path combination is ".../namespace/path" (path is placed inside the namaspace-folder).
 * 
 * @author Marvin Köhler
 *
 */
public class ResourceLocation implements IResourceProvider<ResourceLocation> {
	
	private final String namespace;
	private final String path;
	
	/**
	 * Creates a new ResourceLocation from a namespace and a path string.
	 * @param namespace The namespace string
	 * @param path The path string
	 */
	public ResourceLocation(String namespace, String path) {
		this.namespace = namespace;
		this.path = path;
	}
	
	/**
	 * Creates a new ResourceLocation from a combined path and namesapace string with the format "namespace:path"
	 * @param namespaceAndPath The combined namespace and path string
	 * @throws IllegalArgumentException if the string has not the correct format
	 */
	public ResourceLocation(String namespaceAndPath) {
		String[] parts = namespaceAndPath.split(":");
		if (parts.length != 2) throw new IllegalArgumentException("The namespace and path must be seperated by one ':' to be a valid format!");
		this.namespace = parts[0];
		this.path = parts[1];
	}
	
	@Override
	public String getNamespace() {
		return namespace;
	}
	
	@Override
	public String getPath() {
		return path;
	}

	@Override
	public ResourceLocation locationOfFile(String fileName) {
		return new ResourceLocation(this.namespace, (this.path.isEmpty() ? this.path : this.path + "/") + fileName);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(namespace, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceLocation other = (ResourceLocation) obj;
		return Objects.equals(namespace, other.namespace) && Objects.equals(path, other.path);
	}
	
	@Override
	public String toString() {
		return "Resource{" + this.namespace + ":" + this.path + "}";
	}
		
}
