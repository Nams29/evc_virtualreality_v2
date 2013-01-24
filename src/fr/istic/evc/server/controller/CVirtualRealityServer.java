package fr.istic.evc.server.controller;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.BroadcastCreate;
import fr.istic.evc.server.FactoryServer;
import fr.istic.evc.server.abstraction.IVirtualObjectServer;
import fr.istic.evc.server.abstraction.VirtualReality;


public class CVirtualRealityServer extends UnicastRemoteObject implements ICVirtualRealityServer, Remote, Serializable {

	private static final long serialVersionUID = 7148255275646548173L;

	private VirtualReality abstraction;

	private BroadcastCreate broadcast;

	protected CVirtualRealityServer (String sharedWorldName, String serverHostName, int serverRMIPort,
			String nomGroupeUpdate, int portDiffusionUpdate) throws RemoteException {
		broadcast = new BroadcastCreate(nomGroupeUpdate, portDiffusionUpdate);
		
		// !! IMPORTANT !!
		// Ouvre le port 1001 pour les objects 
		LocateRegistry.createRegistry (10001) ;
		
		this.abstraction = new VirtualReality();
		
		try {
			// dans un shell, il faudrait avoir fait : remiregistry `serverRMIPort`,
			// mais on peut avantageusement remplacer cette commande par un "createRegistry"
			LocateRegistry.createRegistry (serverRMIPort) ;
			Naming.rebind ("//" + serverHostName + ":" + serverRMIPort + "/" + sharedWorldName, this) ;
			System.out.println ("pret pour le service") ;
		} catch (Exception e) {
			System.out.println ("pb RMICentralManager") ;
		}
	}

	@Override
	public void c2cAddObject(String nom, float size, Color color, Vector3d pos, Quat4d rot) throws RemoteException {
		System.out.println("SERVEUR : CVirtualReality : Réception message création cube");
		CVirtualObjectServer object = FactoryServer.newVirtualCube(nom, size, color, pos, rot);
		abstraction.addObject(object);

		System.out.println("SERVEUR : CVirtualReality : Création cube terminée");
		
		System.out.println("SERVEUR : CVirtualReality : Broadcast du cube");
		System.out.println("SERVEUR : CVirtualReality : "+object.getId()+ "\n "+ object.getName()+ "\n "+ 
				 object.getSize()+ "\n "+ object.getColor()+"\n "+  object.getPosition()+"\n "+object.getRotation());
		
		this.broadcast.diffuseMessage(object.getId(), object.getName(), 
				object.getSize(), object.getColor(), object.getPosition(), object.getRotation());
	}
	
	@Override
	public void answer(String string) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			CVirtualRealityServer server = new CVirtualRealityServer(
					"controllerServer", "127.0.0.1", 10000, "239.11.7.91", 10000);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<IVirtualObjectServer> getUniverse() throws RemoteException {
		return abstraction.getList();
	}
	
}