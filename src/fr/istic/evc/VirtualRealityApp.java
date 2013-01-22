package fr.istic.evc;

import fr.istic.evc.client.controller.CVirtualRealityClient;

public class VirtualRealityApp {
	
	public static void main(String[] args) {
		VirtualRealityApp vrApp = new VirtualRealityApp();
		vrApp.init();
	}

	public void init() {
		CVirtualRealityClient cvr = new CVirtualRealityClient();
		cvr.getPresentation().setVisible(true);
	}
	
}
