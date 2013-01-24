package fr.istic.evc.client.controller;

import java.awt.Color;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.client.FactoryClient;
import fr.istic.evc.client.ReceiverCreate;
import fr.istic.evc.client.ReceiverUpdates;
import fr.istic.evc.client.presentation.PVirtualReality;
import fr.istic.evc.server.abstraction.IVirtualObjectServer;
import fr.istic.evc.server.controller.ICVirtualRealityServer;

public class CVirtualRealityClient {

	private ICVirtualRealityServer controlServer;
	private String serverHostName;
	private String serverRMIPort;
	private String sharedWorldName;
	private String name;

	private PVirtualReality presentation;
	
	private Map<Integer, CVirtualObjectClient> listObjects;

	public CVirtualRealityClient() {
		this.presentation = new PVirtualReality(this);
		
		this.listObjects = new HashMap<Integer, CVirtualObjectClient>();
		
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
		
		ReceiverCreate create = new ReceiverCreate("239.11.7.91", 10000);
		create.setDeportedClient(this);
		create.start();
		
		/*
		 * RECUPERTAION DE L'UNIVERS EXISTANT
		 * Sale très très sale :D
		 */
		try {
			ArrayList<IVirtualObjectServer> list;
			list = controlServer.getUniverse();
			System.out.println(list.size());
			for(IVirtualObjectServer obj : list) {
				objectCreateTransform(obj.getId(),obj.getName(), obj.getSize(), obj.getColor(), 
										obj.getPosition(), obj.getRotation());
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		ReceiverUpdates update = new ReceiverUpdates("239.11.7.91", 10001);
		update.setDeportedClient(this);
		update.start();
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

	public void p2cCreateCube(String nom, float size, Color color, Vector3d pos, Quat4d rot) {
		try {
			System.out.println("CLIENT CVirtualRealityClient : Liaison serveur : création cube");
			controlServer.c2cAddObject(nom, size, color, pos, rot);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public String getName(){
		return name;
	}
	
	public void objectCreateTransform(int id, String name, float size, Color c, Vector3d p, Quat4d r) {
		System.out.println("CLIENT : CVirtualRealityClient : Message reçu, création du cube");
		CVirtualObjectClient object = FactoryClient.newVirtualCube(id, name, size, c, p, r);
		
		this.listObjects.put(id, object);
		
		presentation.c2pCreateObject(object.getPresentation());
	}
	
	public void objectUpdateTransform(int id, Vector3d p, Quat4d r) {
		System.out.println("CLIENT : CVirtualRealityClient : Bougeage du cube");
		CVirtualObjectClient object = this.listObjects.get(id);
		object.setTransform(p, r);
	}
}
