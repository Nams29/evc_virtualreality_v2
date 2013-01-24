package fr.istic.evc.client.controller;

import java.awt.Color;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.media.j3d.Transform3D;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.client.presentation.PVirtualObject;
import fr.istic.evc.server.controller.ICVirtualObjectServer;

public class CVirtualObjectClient {

	private PVirtualObject presentation;

	private int id;

	private ICVirtualObjectServer controlServer;
	private String serverHostName;
	private String serverRMIPort;
	private String sharedWorldName;

	public CVirtualObjectClient(int id, String nom, float size, Color color, Vector3d pos, Quat4d rot) {
		this.id = id;
		// On doit passer un objet Transform3d pour appeler le constructeur de TransformGroup
		Transform3D tr = new Transform3D(rot, pos, 1.0);
		this.presentation = new PVirtualObject(this, size, color, tr);
		
		//identifiant serveur
		serverHostName="127.0.0.1";
		serverRMIPort="10001";
		sharedWorldName=String.valueOf(id);
		
		String nameBind = "//" + serverHostName + ":" + serverRMIPort + "/" + sharedWorldName;
		
		System.out.println("CLIENT : CVirtualObjectClient : Bind sur "+nameBind);
		//Permet de récuperer le controleur du serveur
		try {
			controlServer = (ICVirtualObjectServer)Naming.lookup (nameBind) ;
			controlServer.answer("hello from " + id) ;

		} catch (Exception e) {
			System.out.println ("probleme liaison CentralManager") ;
			e.printStackTrace () ;
			System.exit (1) ;
		}
		
	}

	public void p2cTransform(Transform3D t1) {
		Vector3d v = new Vector3d();
		t1.get(v);
		Quat4d q=new Quat4d();
		t1.get(q);

		try {
			System.out.println("CLIENT : CVirtualObjectClient : envoi message bougeage");
			controlServer.setTransform(v,q);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void setTransform(Vector3d v, Quat4d r) {
		Transform3D t = new Transform3D(r, v, 1);
		
		this.presentation.c2pTransform(t);
	}

	public PVirtualObject getPresentation() {
		return presentation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
