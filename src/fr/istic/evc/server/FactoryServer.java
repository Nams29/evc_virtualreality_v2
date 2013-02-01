package fr.istic.evc.server;

import java.awt.Color;
import java.rmi.RemoteException;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.controller.CVirtualObjectServer;
import fr.istic.evc.server.controller.CVirtualViewpointServer;

public class FactoryServer {
	
	public static CVirtualObjectServer newVirtualCube(String nom, float size, 
			Color color, Vector3d pos, Quat4d rot, String shape) {
		CVirtualObjectServer object = null;
		
		try {
			System.out.println("SERVEUR : FactoryServer : Création cube");
			object = new CVirtualObjectServer(nom, size, color, pos, rot, shape);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
	public static CVirtualViewpointServer newVirtualViewpoint (String nom, float size, 
			Color color, Vector3d pos, Quat4d rot, String shape) {
		CVirtualViewpointServer object = null;
		
		try {
			System.out.println("SERVEUR : FactoryServer : Création viewpoint");
			object = new CVirtualViewpointServer(nom, size, color, pos, rot, shape);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
}
