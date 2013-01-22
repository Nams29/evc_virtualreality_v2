package fr.istic.evc.server.abstraction;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public interface IVirtualObjectServer {

	public abstract int getId();

	public abstract void setId(int id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract float getSize();

	public abstract void setSize(float size);

	public abstract Color getColor();

	public abstract void setColor(Color color);

	public abstract Vector3d getPosition();

	public abstract void setPosition(Vector3d position);

	public abstract Quat4d getRotation();

	public abstract void setRotation(Quat4d rotation);

}