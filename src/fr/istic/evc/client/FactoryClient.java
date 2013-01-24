package fr.istic.evc.client;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.client.controller.CVirtualObjectClient;

public class FactoryClient {
	
	public static CVirtualObjectClient newVirtualCube(int id, String nom, 
			float size, Color color, Vector3d pos, Quat4d rot) {
		return new CVirtualObjectClient(id, nom, size, color, pos, rot);
	}
	
}
