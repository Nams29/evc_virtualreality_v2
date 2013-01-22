package fr.istic.evc.client;

import java.awt.Color;

import javax.media.j3d.Transform3D;

import fr.istic.evc.client.controller.CVirtualObjectClient;

public class FactoryClient {
	
	public static CVirtualObjectClient newVirtualCube(String nom, float size, Color color, Transform3D pos) {
		return new CVirtualObjectClient(nom, size, color, pos);
	}
	
}
