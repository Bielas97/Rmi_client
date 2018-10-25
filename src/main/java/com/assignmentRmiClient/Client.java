package com.assignmentRmiClient;

import com.assignmentRmiClient.gui.ClientGui;
import com.assignmentRmiServer.RemoteInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * main class
 */
public class Client {
    public static void main(String[] args) {
        try {

            //System.getProperties().put("java.security.policy", "src\\main\\java\\com\\assignmentRmiClient\\policy.all");
            //System.setSecurityManager(new RMISecurityManager());

            //od tej chwili moge wywloywac zdalnie metody na serwerze
            RemoteInterface remoteInterface = (RemoteInterface) Naming.lookup("rmi://localhost:7000/siemka");
            //remoteInterface.printMessage("klieeent");
            System.out.println("Client ready...");

            ClientGui clientGui = new ClientGui();
            clientGui.startClient(remoteInterface);


        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
