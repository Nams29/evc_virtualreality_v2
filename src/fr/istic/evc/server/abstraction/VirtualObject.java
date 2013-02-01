package fr.istic.evc.server.abstraction;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class VirtualObject implements IVirtualObjectServer {
	
	protected int id;
	protected String name;
	protected float size;
	protected Color color;
	protected Vector3d position;
	protected Quat4d rotation;
	protected String shape;
	
	public VirtualObject(String name, float size, Color c, Vector3d pos, Quat4d rot, String shape) {
		this.name = name;
		this.size = size;
		this.color = c;
		this.position = pos;
		this.rotation = rot;
		this.shape = shape;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getSize() {
		return size;
	}

	@Override
	public void setSize(float size) {
		this.size = size;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public Vector3d getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector3d position) {
		this.position = position;
	}
	
	@Override
	public Quat4d getRotation() {
		return rotation;
	}

	@Override
	public void setRotation(Quat4d rotation) {
		this.rotation = rotation;
	}

	@Override
	public String getShape() {
		return shape;
	}

	@Override
	public void setShape(String shape) {
		this.shape = shape;
	}

	@Override
	public void setATransform(Vector3d v, Quat4d q) {
		this.setPosition(v);
		this.setRotation(q);
	}
	
}
