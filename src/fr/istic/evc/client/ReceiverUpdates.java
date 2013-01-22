package fr.istic.evc.client;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

import fr.istic.evc.client.controller.CVirtualRealityClient;

public class ReceiverUpdates extends Thread implements Runnable {

	private transient MulticastSocket socketReception ;
	private CVirtualRealityClient deportedClient ;

	private String name = new String () ;
	private Vector3d pos = new Vector3d () ;
	private Quat4d quat = new Quat4d () ;

	public ReceiverUpdates (final String nomGroupe, final int portDiffusion) {
		socketReception = null ;
		try {
			InetAddress adresseDiffusion = InetAddress.getByName (nomGroupe) ;
			socketReception = new MulticastSocket (portDiffusion) ;
			socketReception.joinGroup (adresseDiffusion) ;
			socketReception.setLoopbackMode (true) ;
			System.out.println ("socket : " + socketReception.getLocalPort() + " " + socketReception.getInetAddress ()) ;

		} catch (Exception e) {
			e.printStackTrace () ;
		}
	}
	
	public void setDeportedClient (CVirtualRealityClient deportedClient) {
		this.deportedClient = deportedClient ;
	}

	public void recevoir () {
		try {
			byte [] message = new byte [1024] ;
			DatagramPacket paquet = new DatagramPacket (message, message.length) ;
			socketReception.receive (paquet) ;
			ByteArrayInputStream bais = new ByteArrayInputStream (paquet.getData ()) ;
			ObjectInputStream ois = new ObjectInputStream (bais) ;
			name = (String)ois.readObject () ;
			pos = (Vector3d)ois.readObject () ;
			quat = (Quat4d)ois.readObject () ;
		} catch (Exception e) {
			e.printStackTrace () ;
		}
	}


	public void run () {
		while (true) {
			recevoir () ;
			deportedClient.objectUpdateTransform(name, pos, quat) ;
		}
	}

}

