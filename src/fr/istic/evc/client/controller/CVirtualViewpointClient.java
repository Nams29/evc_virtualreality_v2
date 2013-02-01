package fr.istic.evc.client.controller;

import java.awt.Color;
import java.rmi.Naming;

import javax.media.j3d.Transform3D;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.client.presentation.PVirtualObject;
import fr.istic.evc.client.presentation.PVirtualViewpoint;
import fr.istic.evc.server.controller.ICVirtualObjectServer;

public class CVirtualViewpointClient extends CVirtualObjectClient {

	private PVirtualViewpoint presentation;
	private CVirtualRealityClient universe;
	
	public CVirtualViewpointClient(int id, String nom, float size, Color color, Vector3d pos, Quat4d rot, String shape, CVirtualRealityClient c) {
		super(id, nom, size, color, pos, rot, shape);
		
		this.universe = c;
		
		// On doit passer un objet Transform3d pour appeler le constructeur de TransformGroup
		Transform3D tr = new Transform3D(rot, pos, 1.0);
		this.presentation = new PVirtualViewpoint(this, size, color, tr, shape);
		
		//identifiant serveur
		serverHostName="127.0.0.1";
		serverRMIPort="10001";
		sharedWorldName=String.valueOf(id);
		
		String nameBind = "//" + serverHostName + ":" + serverRMIPort + "/" + sharedWorldName;
		
		//Permet de récuperer le controleur du serveur
		try {
			controlServer = (ICVirtualObjectServer)Naming.lookup (nameBind) ;
		} catch (Exception e) {
			System.out.println ("probleme liaison CentralManager") ;
			e.printStackTrace () ;
			System.exit (1) ;
		}
	}

	public void p2cTransform(Transform3D t1) {
		super.p2cTransform(t1);
	}
	
	public void setTransform(Vector3d v, Quat4d r) {
		Transform3D t = new Transform3D(r, v, 1);
		
		System.out.println("CLIENT: BOUGEAGE CAMERA transform3D: "+t.toString());
		System.out.println("CLIENT: BOUGEAGE CAMERA vector3d: "+v.toString());
		System.out.println("CLIENT: BOUGEAGE CAMERA quad4D: "+r.toString());
		
		universe.c2cMoveViewpoint(t);
		
		this.presentation.c2pTransform(t);
	}

	public PVirtualObject getPresentation() {
		return presentation;
	}

}
