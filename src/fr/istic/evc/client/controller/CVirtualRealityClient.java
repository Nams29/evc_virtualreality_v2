package fr.istic.evc.client.controller;

import java.awt.Color;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.client.presentation.PVirtualReality;
import fr.istic.evc.server.controller.ICVirtualRealityServer;

public class CVirtualRealityClient {

	private ICVirtualRealityServer controlServer;
	private String serverHostName;
	private String serverRMIPort;
	private String sharedWorldName;
	private String name;

	private PVirtualReality presentation;

	public CVirtualRealityClient() {
		this.presentation = new PVirtualReality(this);
		
		//identifiant serveur
		serverHostName="127.0.0.1";
		serverRMIPort="10000";
		sharedWorldName="controllerServer";
		
		//Permet de récuperer le controleur du serveur
		try {
			controlServer = (ICVirtualRealityServer)Naming.lookup ("//" + serverHostName + ":" 
					+ serverRMIPort + "/" + sharedWorldName) ;
			controlServer.answer("hello from " + getName()) ;
			
		} catch (Exception e) {
			System.out.println ("probleme liaison CentralManager") ;
			e.printStackTrace () ;
			System.exit (1) ;
		}
	}

	public PVirtualReality getPresentation() {
		return presentation;
	}

	public void p2cMove(float dx, float dy, float dz) {
		this.presentation.c2pMoveRelative(dx, dy, dz);
	}

	public void p2cMoveAbsolute(float dx, float dy, float dz) {
		this.presentation.c2pMoveAbsolute(dx, dy, dz);
	}

	public void p2cRotate(float rx, float ry, float rz) {
		this.presentation.c2pOrientRelative(rx, ry, rz);
	}

	public void p2cRotateAbsolute(float dx, float dy, float dz) {
		this.presentation.c2pOrientAbsolute(dx, dy, dz);
	}

	public void p2cCreateObjet() {
		// TODO Auto-generated method stub
	}

	public void p2cCreateCube(String nom, float size, Color color, Vector3d pos, Quat4d rot) {
		//TODO : envoyer au server
		try {
			controlServer.c2cAddObject(nom, size, color, pos, rot);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void a2cAddObject(CVirtualObjectClient object) {
		presentation.c2pCreateObject(object.getPresentation());
	}

	public String getName(){
		return name;
	}

	public void objectUpdateTransform(String name2, Vector3d pos, Quat4d quat) {

	}
}
