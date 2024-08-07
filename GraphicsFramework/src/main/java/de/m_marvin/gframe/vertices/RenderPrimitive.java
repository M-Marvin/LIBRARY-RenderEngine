package de.m_marvin.gframe.vertices;

import java.util.function.BiConsumer;
import java.util.function.IntConsumer;

import org.lwjgl.opengl.GL33;

import de.m_marvin.gframe.buffers.VertexBuffer;

/**
 * Represents the different primitives supported by this render engine.
 * @implNote The quads implementation just provides a special index-algorithm to convert the vertex data to triangle primitives, so it does not mater if {@link QUADS} or {@link TRIANGLES} is passes to the drawing function of {@link VertexBuffer#drawAll(RenderPrimitive)}.
 * 
 * @author Marvin Köhler
 *
 */
public enum RenderPrimitive {
	
	POINTS(GL33.GL_POINTS, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i++) indexconsumer.accept(i);
	}),
	LINES_LOOP(GL33.GL_LINE_LOOP, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i++) indexconsumer.accept(i);
	}),
	LINES_STRIP(GL33.GL_LINE_STRIP, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i++) indexconsumer.accept(i);
	}),
	LINES(GL33.GL_LINES, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i++) indexconsumer.accept(i);
	}),
	TRIANGLES(GL33.GL_TRIANGLES, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i++) indexconsumer.accept(i);
	}),
	TRIANGLES_STRIP(GL33.GL_TRIANGLE_STRIP, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i++) indexconsumer.accept(i);
	}),
	TRIANGLES_FAN(GL33.GL_TRIANGLE_FAN, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i++) indexconsumer.accept(i);
	}),
	QUADS(GL33.GL_TRIANGLES, (vertices, indexconsumer) -> {
		for (int i = 0; i < vertices; i += 4) {
			indexconsumer.accept(i + 0);
			indexconsumer.accept(i + 1);
			indexconsumer.accept(i + 2);
			indexconsumer.accept(i + 2);
			indexconsumer.accept(i + 3);
			indexconsumer.accept(i + 0);
		}
	});
	
	private final int glType;
	private final BiConsumer<Integer, IntConsumer> defaultIndexBuilder;
	
	private RenderPrimitive(int glType, BiConsumer<Integer, IntConsumer> defaultIndexBuilder) {
		this.glType = glType;
		this.defaultIndexBuilder = defaultIndexBuilder;
	}
	
	public int getgltype() {
		return glType;
	}
	
	/**
	 * Builds the default indecies required for rendering geometry with this primitive type.
	 * This method can be used to auto generate the indecies if the used vertex data is in the correct standard order for this primitive.
	 * 
	 * @param vertexCount The number of vertices to draw
	 * @param indexconsumer An consumer to receive the index values
	 */
	public void buildDefaultIndecies(int vertexCount, IntConsumer indexconsumer) {
		this.defaultIndexBuilder.accept(vertexCount, indexconsumer);
	}
	
}
