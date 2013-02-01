package fr.istic.evc.server.abstraction;

import java.awt.Color;
import java.rmi.RemoteException;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public interface IVirtualObjectServer {

	public abstract int getId() throws RemoteException;

	public abstract void setId(int id) throws RemoteException;

	public abstract String getName() throws RemoteException;

	public abstract void setName(String name) throws RemoteException;

	public abstract float getSize() throws RemoteException;

	public abstract void setSize(float size) throws RemoteException;

	public abstract Color getColor() throws RemoteException;

	public abstract void setColor(Color color) throws RemoteException;

	public abstract Vector3d getPosition() throws RemoteException;

	public abstract void setPosition(Vector3d position) throws RemoteException;

	public abstract Quat4d getRotation() throws RemoteException;

	public abstract void setRotation(Quat4d rotation) throws RemoteException;
	
	public String getShape() throws RemoteException;
	
	public void setShape(String shape) throws RemoteException;
	
	public abstract void setATransform(Vector3d v, Quat4d q) throws RemoteException;

}