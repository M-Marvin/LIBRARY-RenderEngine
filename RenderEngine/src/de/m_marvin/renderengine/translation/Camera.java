package de.m_marvin.renderengine.translation;

import de.m_marvin.unimat.impl.Matrix4f;
import de.m_marvin.unimat.impl.Quaternion;
import de.m_marvin.univec.impl.Vec3f;

public class Camera {
	
	protected Matrix4f viewMatrix = new Matrix4f();
	protected Vec3f position;
	protected Vec3f rotation;
	
	public Camera(Vec3f position, Vec3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public Camera() {
		this(new Vec3f(0F, 0F, 0F), new Vec3f(0F, 0F, 0F));
	}
	
	public void offset(Vec3f linearMotion) {
		this.position.addI(linearMotion);
	}
	public void move(Vec3f relativeMovement) {
		Quaternion orientation = Quaternion.fromXYZDegrees(this.rotation);
		Vec3f offsetMovement = relativeMovement.transform(orientation);
		offset(offsetMovement);
	}
	public void rotate(Vec3f axialMotion) {
		this.rotation.addI(axialMotion);
	}
	
	public void upadteViewMatrix() {
		this.viewMatrix.identity();
		this.viewMatrix.mul(Matrix4f.createTranslateMatrix(this.position.x(), this.position.y(), this.position.z()));
		this.viewMatrix.mul(Quaternion.fromXYZDegrees(rotation));
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
}