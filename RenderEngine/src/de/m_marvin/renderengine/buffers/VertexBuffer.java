package de.m_marvin.renderengine.buffers;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL33;

import de.m_marvin.renderengine.GLStateManager;
import de.m_marvin.renderengine.buffers.BufferBuilder.DrawState;
import de.m_marvin.renderengine.vertecies.VertexFormat.VertexElement;

public class VertexBuffer {
	
	protected int arrayObjectId;
	protected int vertexBufferId;
	protected int indexBufferId;
	protected int indecies;
	protected int vertecies;
	
	public VertexBuffer() {
		GLStateManager.genVertexArray((id) -> this.arrayObjectId = id);
		GLStateManager.genBufferObject((id) -> this.indexBufferId = id);
		GLStateManager.genBufferObject((id) -> this.vertexBufferId = id);
	}
	
	public void discard() {
		GLStateManager.deleteVertexArray(this.vertexBufferId);
		GLStateManager.deleteBufferObject(vertexBufferId);
		GLStateManager.deleteBufferObject(indexBufferId);
	}
	
	public void upload(BufferBuilder bufferBuilder) {
		GLStateManager.assertOnRenderThread();
		BufferBuilder.BufferPair pair = bufferBuilder.popNext();
		ByteBuffer buffer = pair.buffer();
		DrawState drawState = pair.drawState();
		buffer.clear();
		buffer.limit(drawState.vertecies() * drawState.format().getSize());
		GLStateManager.bindVertexArray(arrayObjectId);
		GLStateManager.bindBufferObject(GL33.GL_ARRAY_BUFFER, this.vertexBufferId);
		GLStateManager.bufferData(GL33.GL_ARRAY_BUFFER, buffer, GL33.GL_STATIC_DRAW);
		for (VertexElement element : drawState.format().getElements()) {
			GLStateManager.attributePointer(element.index(), element.size(), element.position(), element.format().gltype(), element.normalize(), 0);
		}
		buffer.position(buffer.limit());
		buffer.limit(buffer.limit() + drawState.indecies() * Integer.BYTES);
		GLStateManager.bindBufferObject(GL33.GL_ARRAY_BUFFER, indexBufferId);
		GLStateManager.bufferData(GL33.GL_ARRAY_BUFFER, buffer, GL33.GL_STATIC_DRAW);
		GLStateManager.bindBufferObject(GL33.GL_ARRAY_BUFFER, 0);
		GLStateManager.bindVertexArray(0);	
	}
	
}
