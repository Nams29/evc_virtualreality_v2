package fr.istic.evc.client;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.client.controller.CVirtualObjectClient;
import fr.istic.evc.client.controller.CVirtualRealityClient;
import fr.istic.evc.client.controller.CVirtualViewpointClient;

public class FactoryClient {
	
	public static CVirtualObjectClient newVirtualObject(int id, String nom, 
			float size, Color color, Vector3d pos, Quat4d rot, String shape) {
		return new CVirtualObjectClient(id, nom, size, color, pos, rot, shape);
	}
	
	public static CVirtualViewpointClient newVirtualViewpoint(int id, String nom, 
			float size, Color color, Vector3d pos, Quat4d rot, String shape, CVirtualRealityClient c) {
		return new CVirtualViewpointClient(id, nom, size, color, pos, rot, shape,c);
	}
	
	
}
