package cz.muni.fi.pa165.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

public class NoticeBoardImpl implements NoticeBoard {
  synchronized public void publish(String text) throws RemoteException {
    try {
      System.out.println(UnicastRemoteObject.getClientHost() + ": " + text);
    } catch (ServerNotActiveException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws RemoteException {
    System.out.println("starting noticeboard ...");
    NoticeBoard stub = (NoticeBoard) UnicastRemoteObject.exportObject(
        new NoticeBoardImpl(), 0);
    LocateRegistry.getRegistry().rebind("board", stub);
  }
}
