package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
	 
    private boolean isRunning;
    private ServerSocket serverSocket;
 
    public void startServer() {
      isRunning = true;
      start();
    }
 
    public void stopServer() {
      isRunning = false;
      new Thread(new Runnable() {
        @Override
        public void run() {
          try {
            if (serverSocket != null) {
              serverSocket.close();
            }
          } catch(IOException ioException) {
          }
        }
      }).start();
    }
 
    @Override
    public void run() {
      try {
        serverSocket = new ServerSocket(Constants.SERVER_PORT);
        while (isRunning) {
          Socket socket = serverSocket.accept();
          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          String op = in.readLine();
          OpFactory operation= new OpFactory(op, socket);
        }
      } catch (IOException ioException) {
      }
    }
    
    
  }