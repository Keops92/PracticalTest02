package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.lang.Thread;

public class OpFactory extends Thread{
	private Socket socket;
	String op;
	public OpFactory(String op, Socket serverSocket) {
		socket = serverSocket;
		this.op = op;
		String[] command = op.split(",");
		int op1;
		int op2; 
		int result;
		op1 = Integer.parseInt(command[1]);
		op2 = Integer.parseInt(command[2]);
		
		try {
			if (command[0].equals("add")) {
				result = op1 + op2;
				
			} else {
				this.sleep(100);
				result = op1 * op2;
			}
			PrintWriter out =
			        new PrintWriter(socket.getOutputStream(), true);
			out.println(result);
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {	
		try {
			PrintWriter out =
			        new PrintWriter(socket.getOutputStream(), true);
			out.println("10");
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
