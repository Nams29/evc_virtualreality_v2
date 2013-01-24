package fr.istic.evc.server.controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.abstraction.IVirtualObjectServer;

public interface ICVirtualObjectServer extends Remote, IVirtualObjectServer {

	void answer(String string) throws RemoteException;
	
	public void setTransform(Vector3d v, Quat4d q) throws RemoteException;
	
}
