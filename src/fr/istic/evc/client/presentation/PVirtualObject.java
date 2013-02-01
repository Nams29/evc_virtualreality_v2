package fr.istic.evc.client.presentation;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.media.j3d.Appearance;
import javax.media.j3d.BadTransformException;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;

import org.jdesktop.j3d.loaders.vrml97.VrmlLoader;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.geometry.Box;

import fr.istic.evc.client.controller.CVirtualObjectClient;

public class PVirtualObject extends TransformGroup {

	private CVirtualObjectClient controller;

	public PVirtualObject(CVirtualObjectClient controller, float size, Color color, Transform3D pos, String shape) {
		super(pos);
		this.controller = controller;
		this.createObject(size, color, shape);
	}

	private void createObject(float size, Color color, String shape) {
		// SHAPE
		if (!shape.isEmpty()) {
			VrmlLoader loader = new VrmlLoader();
			
			try {
				Scene scene = loader.load(new URL(shape)) ;
				
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
		else {
			// COLOR
			Appearance app = new Appearance();
			ColoringAttributes colorAttr = new ColoringAttributes();
			colorAttr.setColor(new Color3f(color));
			app.setColoringAttributes(colorAttr);
			
			this.addChild(new Box(size, size, size, app));
		}

	}

	@Override
	public void setTransform(Transform3D t1) {
		System.out.println("CLIENT : PVirtualObject : bougeage d'objet");
		this.controller.p2cTransform(t1);
	}

	public void c2pTransform(Transform3D t1) {
		/* !! ERREUR de temps en temps !! 
		 * Exception in thread "Thread-2" javax.media.j3d.BadTransformException: TransformGroup: non-affine transform
		 * at javax.media.j3d.TransformGroup.setTransform(TransformGroup.java:137)
		 * at fr.istic.evc.client.presentation.PVirtualObject.c2pTransform(PVirtualObject.java:41)
		 * at fr.istic.evc.client.controller.CVirtualObjectClient.setTransform(CVirtualObjectClient.java:69)
		 * at fr.istic.evc.client.controller.CVirtualRealityClient.objectUpdateTransform(CVirtualRealityClient.java:106)
		 * at fr.istic.evc.client.ReceiverUpdates.run(ReceiverUpdates.java:62)
		 * 
		 * Valeur de t1 :
		 * NaN, NaN, NaN, 0.03999999999999976
		 * NaN, NaN, NaN, -2.279999999999999
		 * NaN, NaN, NaN, 0.0
		 * 0.0, 0.0, 0.0, 1.0
		 */
		try { 
			super.setTransform(t1);
		}
		catch (BadTransformException e) {
			System.out.println("\n\n\nERREEEEUUUUUUUUR !!");
			System.out.println(t1);
			System.out.println("\n\n\n\n");
		}
	}
}
