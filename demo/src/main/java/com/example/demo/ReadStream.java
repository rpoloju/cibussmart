package com.example.demo;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ReadStream implements Runnable {
	String name;
	InputStream is;
	OutputStream os;
	Thread thread;

	public ReadStream(String name, InputStream is) {
		this.name = name;
		this.is = is;
	}
	
	public ReadStream(String name, OutputStream os) {
		this.name = name;
		this.os = os;
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			while (true) {
				String s = br.readLine();
				if (s == null)
					break;
				System.out.println("[" + name + "] " + s);
			}
			is.close();
		} catch (Exception ex) {
			System.out.println("Problem reading stream " + name + "... :" + ex);
			ex.printStackTrace();
		}
	}
}