package fr.istic.evc.server;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.server.controller.CVirtualObjectServer;

public class FactoryServer {
	
	public static CVirtualObjectServer newVirtualCube(String nom, float size, Color color, Vector3d pos, Quat4d rot) {
		return new CVirtualObjectServer(nom, size, color, pos, rot);
	}
	
	/*public static CVirtualObject newVirtualSphere(float size, Color color) {
		CVirtualObject obj = new CVirtualObjectServer(size, color);
		return obj;
	}*/
	
}
