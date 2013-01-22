package fr.istic.evc.server.abstraction;

import java.util.ArrayList;

public class VirtualReality implements IVirtualRealityServer {
	
	private ArrayList<IVirtualObjectServer> listObjects;
	
	public VirtualReality() {
		this.listObjects = new ArrayList<IVirtualObjectServer>();
	}
	
	@Override
	public void addObject(IVirtualObjectServer object) {
		object.setId(listObjects.size());
		this.listObjects.add(object);
	}

}
