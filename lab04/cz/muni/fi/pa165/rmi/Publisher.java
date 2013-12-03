package cz.muni.fi.pa165.rmi;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

public class Publisher {
  public static void main(String[] args) throws RemoteException, NotBoundException {
    if(args.length!=2) {
      System.out.println("Usage: java "+Publisher.class.getName()+" host text");
      System.exit(1);
    }
    String host = args[0];
    String text = args[1];

    Registry remregistry = LocateRegistry.getRegistry(host);
    NoticeBoard noticeBoard = (NoticeBoard) remregistry.lookup("board");

    noticeBoard.publish(text);
  }
}
