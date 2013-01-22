package fr.istic.evc.client.presentation;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Box;

import fr.istic.evc.client.controller.CVirtualObjectClient;

public class PVirtualObject extends TransformGroup {
	
	private CVirtualObjectClient controller;
	
	public PVirtualObject(CVirtualObjectClient controller, float size, Color color, Transform3D t3d) {
		super(t3d);
		this.controller = controller;
		this.createObject(size, color);
	}
	
	private void createObject(float size, Color color) {
		Appearance app = new Appearance();
		ColoringAttributes colorAttr = new ColoringAttributes();
		colorAttr.setColor(new Color3f(color));
		app.setColoringAttributes(colorAttr);
		
		this.addChild(new Box(size, size, size, app));
	}
	
	@Override
	public void setTransform(Transform3D t1) {
		this.controller.p2cTransform(t1);
	}
	
	public void c2pTransform(Transform3D t1) {
		super.setTransform(t1);
	}
}
