package fr.istic.evc.server.abstraction;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class VirtualObject implements IVirtualObjectServer {
	
	private int id;
	private String name;
	private float size;
	private Color color;
	private Vector3d position;
	private Quat4d rotation;
	
	public VirtualObject(String name, float size, Color c, Vector3d pos, Quat4d rot) {
		this.name = name;
		this.size = size;
		this.color = c;
		this.position = pos;
		this.rotation = rot;
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
	public void setATransform(Vector3d v, Quat4d q) {
		this.setPosition(v);
		this.setRotation(q);
	}
	
}
