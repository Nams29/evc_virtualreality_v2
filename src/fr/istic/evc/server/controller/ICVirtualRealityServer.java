package fr.istic.evc.server.controller;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;


public interface ICVirtualRealityServer extends Remote {

	public void answer(String string) throws RemoteException ;

	public void c2cAddObject(String nom, float size, Color color, Vector3d pos, Quat4d rot) throws RemoteException;
}
