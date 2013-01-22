package fr.istic.evc.client.controller;

import java.awt.Color;

import javax.media.j3d.Transform3D;

import fr.istic.evc.client.presentation.PVirtualObject;

public class CVirtualObjectClient {
	
	private PVirtualObject presentation;
	
	public CVirtualObjectClient(String nom, float size, Color color, Transform3D pos) {
		this.presentation = new PVirtualObject(this, size, color, pos);
	}

	public void p2cTransform(Transform3D t1) {
		//abstraction.setTransform(t1);
	}
	
	public void a2cTransform(Transform3D t1) {
		this.presentation.c2pTransform(t1);
	}
	
	public PVirtualObject getPresentation() {
		return presentation;
	}
}
