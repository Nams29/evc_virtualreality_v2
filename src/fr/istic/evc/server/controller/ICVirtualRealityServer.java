package fr.istic.evc.server.controller;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.abstraction.IVirtualObjectServer;


public interface ICVirtualRealityServer extends Remote {

	void answer(String string) throws RemoteException;
	
	public void c2cAddObject(String nom, float size, 
			Color color, Vector3d pos, Quat4d rot, String shape) throws RemoteException;
	
	public void c2cAddViewpoint(String nom, float size, 
			Color color, Vector3d pos, Quat4d rot, String shape) throws RemoteException;
	
	ArrayList<IVirtualObjectServer> getUniverse() throws RemoteException;
}
