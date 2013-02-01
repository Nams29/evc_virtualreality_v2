package fr.istic.evc.server.abstraction;

import java.awt.Color;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class VirtualViewpoint extends VirtualObject {
	
	public VirtualViewpoint(String name, float size, Color c, Vector3d pos, Quat4d rot, String shape) {
		super(name, size, c, pos, rot, shape);
	}
	
}
