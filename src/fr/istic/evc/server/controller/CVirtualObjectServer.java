package fr.istic.evc.server.controller;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.abstraction.IVirtualObjectServer;
import fr.istic.evc.server.abstraction.VirtualObject;

public class CVirtualObjectServer implements IVirtualObjectServer {
	
	private IVirtualObjectServer abstraction;
	
	public CVirtualObjectServer(String name, float size, Color c, Vector3d pos, Quat4d rot) {
		this.abstraction = new VirtualObject(name, size, c, pos, rot);
	}
	
	public int getId() {
		return this.abstraction.getId();
	}
	
	public String getName() {
		return this.abstraction.getName();
	}
	
	public Color getColor() {
		return this.abstraction.getColor();
	}
	
	public float getSize() {
		return this.abstraction.getSize();
	}
	
	public Vector3d getPosition() {
		return this.abstraction.getPosition();
	}
	
	public Quat4d getRotation() {
		return this.abstraction.getRotation();
	}

	@Override
	public void setId(int id) {
		this.abstraction.setId(id);
	}

	@Override
	public void setName(String name) {
		this.abstraction.setName(name);
	}

	@Override
	public void setSize(float size) {
		this.abstraction.setSize(size);
	}

	@Override
	public void setColor(Color color) {
		this.abstraction.setColor(color);
	}

	@Override
	public void setPosition(Vector3d position) {
		this.abstraction.setPosition(position);
	}

	@Override
	public void setRotation(Quat4d rotation) {
		this.abstraction.setRotation(rotation);
	}
	
}
