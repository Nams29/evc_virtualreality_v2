package fr.istic.evc.server.controller;

import java.awt.Color;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.BroadcastUpdates;
import fr.istic.evc.server.abstraction.IVirtualObjectServer;
import fr.istic.evc.server.abstraction.VirtualViewpoint;

public class CVirtualViewpointServer extends CVirtualObjectServer {
	
	private static final long serialVersionUID = -576878545016454955L;
	
	private IVirtualObjectServer abstraction;
		
	public CVirtualViewpointServer(String name, float size, Color c, Vector3d pos, Quat4d rot, String shape) throws RemoteException {
		super(name, size, c, pos, rot, shape);
		this.abstraction = new VirtualViewpoint(name, size, c, pos, rot, shape);
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
	public void setTransform(Vector3d v, Quat4d q) throws RemoteException {
		q.normalize();
		this.abstraction.setATransform(v, q);
		this.broadcast.diffuseMessage(this.getId(), v,q);
	}

	@Override
	public String getShape() throws RemoteException {
		return this.abstraction.getShape();
	}

	@Override
	public void setShape(String shape) throws RemoteException {
		this.abstraction.setShape(shape);
	}

	@Override
	public void setATransform(Vector3d v, Quat4d q) throws RemoteException {
		this.abstraction.setATransform(v, q);
	}
	
}
