package fr.istic.evc.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.RemoteException;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class BroadcastUpdates implements Serializable {

	private static final long serialVersionUID = 1L;
	private int portDiffusion ;
	private String nomGroupe ;
	
	public String getNomGroupe () {
		return nomGroupe ;
	}

	private InetAddress adresseDiffusion ;
	private transient MulticastSocket socketDiffusion ;

	public BroadcastUpdates (final String ng, final int portDiffusion)
			throws RemoteException {
		this.portDiffusion = portDiffusion ;
		nomGroupe = ng ;
		System.out.println ("Diffuseur sur le port " + portDiffusion +
				" a destination du groupe " + nomGroupe) ;
		adresseDiffusion = null ;
		socketDiffusion = null ;
		try {
			adresseDiffusion = InetAddress.getByName(nomGroupe) ;
			socketDiffusion = new MulticastSocket () ;
			socketDiffusion.setTimeToLive (16) ;
			socketDiffusion.setLoopbackMode (false) ; // pour des envois d'une machine à une autre
			// si on veut faire des tests avec serveur et clients sur une même machine, il faut écrire : socketDiffusion.setLoopbackMode (false)
		} catch (IOException e) {
			e.printStackTrace () ;
		}
		System.out.println ("socket : " + socketDiffusion.getLocalPort() + " " + socketDiffusion.getInetAddress ()) ;
	}

	public void diffuseMessage (int id, Vector3d p, Quat4d r) {
		System.out.println("SERVEUR : BroadcastUpdates : Envoi message :\nid : "+id+"\nvecteur : "+p+"\nquat4d : "+r);
		ByteArrayOutputStream baos = new ByteArrayOutputStream () ;
		ObjectOutputStream oos ;
		try {
			oos = new ObjectOutputStream (baos) ;
			oos.writeObject (id) ;
			oos.writeObject (p) ;
			oos.writeObject (r) ;
			oos.flush () ;
		} catch (IOException e) {
			e.printStackTrace();
		}
		DatagramPacket paquet = new DatagramPacket (baos.toByteArray (), baos.toByteArray ().length,
				adresseDiffusion,
				portDiffusion) ;
		try {
			socketDiffusion.send (paquet);
		} catch (IOException e) {
			e.printStackTrace () ;
		}
		System.out.println("SERVEUR : BroadcastUpdates : Fin envoi");
	}

	public int getPortDiffusion () throws RemoteException {
		return (portDiffusion) ;
	}

	public InetAddress getAdresseDiffusion () throws RemoteException {
		return (adresseDiffusion) ;
	}

}
