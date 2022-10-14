package de.m_marvin.unimat.api;

public interface IMatrix4f<M extends IMatrix4f<?>> extends IMatrix {

	public M copy();
	
	public float getField(int x, int y);
	public void setField(int x, int y, int f);
	
	public float m00();
	public float m01();
	public float m02();
	public float m03();
	public float m10();
	public float m11();
	public float m12();
	public float m13();
	public float m20();
	public float m21();
	public float m22();
	public float m23();
	public float m30();
	public float m31();
	public float m32();
	public float m33();
	
}
