package fr.istic.evc.client.presentation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.j3d.Transform3D;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;

import fr.istic.evc.client.controller.CVirtualViewpointClient;

public class PVirtualViewpoint extends PVirtualObject {

	private CVirtualViewpointClient controller;

	public PVirtualViewpoint(CVirtualViewpointClient controller, float size, Color color, Transform3D pos, String shape) {
		super(controller, size, color, pos, shape);
		this.controller = controller;
		this.createObject(size, color, shape);
	}

	private void createObject(float size, Color color, String shape) {
		if (!shape.isEmpty()) {
			VrmlLoader loader = new VrmlLoader();
			
			try {
				Scene scene = loader.load(
						new URL("http://perso.univ-rennes1.fr/thierry.duval/EVC/SourcesExemples/TestVRMLLoader/coneVert.wrl"));
				
				this.addChild (scene.getSceneGroup ()) ;
			} catch (FileNotFoundException e) {
				e.printStackTrace () ;
			} catch (IncorrectFormatException e) {
				e.printStackTrace();
			} catch (ParsingErrorException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void setTransform(Transform3D t1) {
		System.out.println("CLIENT : PVirtualViewpoint : bougeage d'objet");
		this.controller.p2cTransform(t1);
	}
	
	@Override
	public void c2pTransform(Transform3D t1) {
		super.c2pTransform(t1);
	}
}
