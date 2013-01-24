package fr.istic.evc.server.controller;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.BroadcastUpdates;
import fr.istic.evc.server.abstraction.IVirtualObjectServer;
import fr.istic.evc.server.abstraction.VirtualObject;

public class CVirtualObjectServer extends UnicastRemoteObject 
	implements ICVirtualObjectServer, Remote, Serializable {
	
	private static final long serialVersionUID = -576878545016454955L;
	
	private IVirtualObjectServer abstraction;

	private BroadcastUpdates broadcast;
	
	private String sharedObjectName;
	private String serverHostName = "127.0.0.1";
	private int serverRMIPort = 10001;
	private String nomGroupeUpdate = "239.11.7.91";
	private int portDiffusionUpdate = 10001;
	
	public CVirtualObjectServer(String name, float size, Color c, Vector3d pos, Quat4d rot) throws RemoteException {
		System.out.println("SERVEUR : CVirtualObjectServer : Création cube");
		this.abstraction = new VirtualObject(name, size, c, pos, rot);
	}
	
	@Override
	public int getId() throws RemoteException {
		return this.abstraction.getId();
	}
	
	@Override
	public String getName() throws RemoteException {
		return this.abstraction.getName();
	}
	
	@Override
	public Color getColor() throws RemoteException {
		return this.abstraction.getColor();
	}
	
	@Override
	public float getSize() throws RemoteException {
		return this.abstraction.getSize();
	}
	
	@Override
	public Vector3d getPosition() throws RemoteException {
		return this.abstraction.getPosition();
	}
	
	@Override
	public Quat4d getRotation() throws RemoteException {
		return this.abstraction.getRotation();
	}

	@Override
	public void setId(int id) throws RemoteException {
		this.abstraction.setId(id);
		
		String nameBind = "//" + serverHostName + ":" + serverRMIPort + "/" + id;
		
		System.out.println("SERVEUR : CVirtualObjectServer : Bind sur "+nameBind);
		this.broadcast = new BroadcastUpdates(nomGroupeUpdate, portDiffusionUpdate);
		
		try {
			// dans un shell, il faudrait avoir fait : remiregistry `serverRMIPort`,
			// mais on peut avantageusement remplacer cette commande par un "createRegistry"
			//LocateRegistry.createRegistry (serverRMIPort) ;
			Naming.rebind (nameBind, this) ;
			System.out.println ("pret pour le service") ;
		} catch (Exception e) {
			System.out.println ("pb RMICentralManager : "+e.getClass()+" : "+e.getMessage());
		}
		System.out.println("SERVEUR : CVirtualObjectServer : Fin bind");
	}
	
	@Override
	public void setName(String name) throws RemoteException {
		this.abstraction.setName(name);
	}

	@Override
	public void setSize(float size) throws RemoteException {
		this.abstraction.setSize(size);
	}

	@Override
	public void setColor(Color color) throws RemoteException {
		this.abstraction.setColor(color);
	}

	@Override
	public void setPosition(Vector3d position) throws RemoteException {
		this.abstraction.setPosition(position);
	}

	@Override
	public void setRotation(Quat4d rotation) throws RemoteException {
		this.abstraction.setRotation(rotation);
	}

	@Override
	public void answer(String string) throws RemoteException {
		
	}
	
	@Override
	public void setTransform(Vector3d v, Quat4d q) throws RemoteException {
		System.out.println("SERVEUR : CVirtualObjectServer : Réception message bougeage");
		this.abstraction.setATransform(v, q);
		this.broadcast.diffuseMessage(this.getId(), v,q);
	}

	@Override
	public void setATransform(Vector3d v, Quat4d q) throws RemoteException {
		this.abstraction.setATransform(v, q);
	}
	
}
