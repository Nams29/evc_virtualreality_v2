package fr.istic.evc.server.abstraction;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class VirtualReality implements IVirtualRealityServer {
	
	private ArrayList<IVirtualObjectServer> listObjects;
	
	public VirtualReality() {
		this.listObjects = new ArrayList<IVirtualObjectServer>();
	}
	
	@Override
	public void addObject(IVirtualObjectServer object) {
		try {
			System.out.println("SERVEUR : VirtualReality : Set Id objet : "+listObjects.size());
			object.setId(listObjects.size());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		this.listObjects.add(object);
	}
	
	public ArrayList<IVirtualObjectServer> getList() {
		return listObjects;
	}
}
