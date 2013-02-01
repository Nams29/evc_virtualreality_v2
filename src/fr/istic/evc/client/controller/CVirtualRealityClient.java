package fr.istic.evc.client.controller;

import java.awt.Color;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.media.j3d.Transform3D;
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

	private String key;
	private CVirtualViewpointClient viewpoint;

	private String serverHostName;
	private String serverRMIPort;
	private String sharedWorldName;
	private String name;

	private PVirtualReality presentation;

	private Map<Integer, CVirtualObjectClient> listObjects;

	public CVirtualRealityClient() {
		this.presentation = new PVirtualReality(this);
		this.p2cMoveAbsolute(0, 0, 0);
		
		this.listObjects = new HashMap<Integer, CVirtualObjectClient>();

		try {
			this.key = InetAddress.getLocalHost().toString()+":"+(Math.round(Math.random()*1000));
			System.out.println("CLIENT : Clef unique : "+key);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		//identifiant serveur
		serverHostName="127.0.0.1";
		serverRMIPort="10000";
		sharedWorldName="controllerServer";

		//Permet de récuperer le controleur du serveur
		try {
			controlServer = (ICVirtualRealityServer)Naming.lookup ("//" + serverHostName + ":" 
					+ serverRMIPort + "/" + sharedWorldName) ;

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
		 */
		try {
			ArrayList<IVirtualObjectServer> list;
			list = controlServer.getUniverse();
			System.out.println(list.size());
			for(IVirtualObjectServer obj : list) {
				objectCreateTransform(obj.getId(),obj.getName(), obj.getSize(), obj.getColor(), 
						obj.getPosition(), obj.getRotation(), obj.getShape());
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		this.createViewpoint();	

		ReceiverUpdates update = new ReceiverUpdates("239.11.7.91", 10001);
		update.setDeportedClient(this);
		update.start();
	}

	public PVirtualReality getPresentation() {
		return presentation;
	}

	public void p2cMove(float dx, float dy, float dz) {
		Transform3D oldT3D = new Transform3D();
		viewpoint.getPresentation().getTransform(oldT3D);

		Vector3d translate = new Vector3d();
		translate.set(dx, dy, dz);
		
		Transform3D localT3D = new Transform3D();
		localT3D.setTranslation(translate);
		
		Transform3D newT3D = new Transform3D();
		newT3D.mul(oldT3D, localT3D);
		
		//Le client veut bouger son point de vue on notifie le serveur en modifiant l'objet point de vue
		viewpoint.p2cTransform(newT3D);
		
		System.out.println("test");
		
		/*this.presentation.c2pMoveRelative(dx, dy, dz);
		Vector3d v = new Vector3d();
		Quat4d r = new Quat4d();
		this.viewpoint.setTransform(v, r);*/
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

	public void p2cCreateObject(String nom, float size, Color color, Vector3d pos, Quat4d rot, String shape) {
		try {
			System.out.println("CLIENT CVirtualRealityClient : Liaison serveur : création cube");
			controlServer.c2cAddObject(nom, size, color, pos, rot, shape);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void c2cMoveViewpoint(Transform3D t) {		
		System.out.println("CLIENT: BOUGEAGE CAMERA transform3D: "+t.toString());
		//Bougeage 
		this.presentation.c2pMoveViewpoint(t);
	}
	
	public void createViewpoint() {
		try {
			controlServer.c2cAddViewpoint(key, 0.1f, Color.BLUE, new Vector3d(), new Quat4d(), 
					"http://perso.univ-rennes1.fr/thierry.duval/EVC/SourcesExemples/TestVRMLLoader/coneVert.wrl");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public String getName(){
		return name;
	}
	
	public String getKey() {
		return key;
	}

	public void objectCreateTransform(int id, String name, float size, Color c, Vector3d p, Quat4d r, String s) {
		if(!name.equals(key)){
			System.out.println("CLIENT : CVirtualRealityClient : Message reçu, création de l'objet");
			CVirtualObjectClient object = FactoryClient.newVirtualObject(id, name, size, c, p, r, s);
			this.listObjects.put(id, object);
			presentation.c2pCreateObject(object.getPresentation());
		}
		else {
			System.out.println("CLIENT : CVirtualRealityClient : Message reçu, création du point de vue");
			viewpoint = FactoryClient.newVirtualViewpoint(id, name, size, c, p, r, s,this);
			this.listObjects.put(id, viewpoint);
			//presentation.c2pCreateObject(viewpoint.getPresentation());
		}
	}

	public void objectUpdateTransform(int id, Vector3d p, Quat4d r) {
		if (id != viewpoint.getId()) {
			System.out.println("CLIENT : CVirtualRealityClient : Bougeage de l'objet");
			CVirtualObjectClient object = this.listObjects.get(id);
			object.setTransform(p, r);
		}
		else {
			System.out.println("CLIENT : CVirtualRealityClient : Bougeage du point de vue");
			CVirtualViewpointClient object = (CVirtualViewpointClient) this.listObjects.get(id);
			object.setTransform(p, r);
			
		}
	}
}
