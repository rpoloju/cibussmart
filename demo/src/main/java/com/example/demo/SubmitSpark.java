package com.example.demo;
import java.io.IOException;

import com.example.demo.ReadStream;

public class SubmitSpark {

	public static void main(String[] args) throws IOException, InterruptedException {

		Process proc = null;
		ReadStream s1 = null;
		ReadStream s2 = null;
		//ReadStream s3 = null;

		try {

			String command = "spark-submit --class Recommendation.recom.App --master spark://ec2-54-164-174-166.compute-1.amazonaws.com:7077 /usr/local/cibussmarts310.jar" + " " + args[0];

			//proc = Runtime.getRuntime().exec(command);
			
			System.out.println("the passed argument is " + args[0]);
			System.out.println(command);
			String[] commands = {"/bin/sh", "-c", command,  args[0]};
			for (int i=0;i<commands.length;i++)
				System.out.println(commands[i]);
			
			proc = new ProcessBuilder(commands).start();

			s1 = new ReadStream("stdin", proc.getInputStream());
			s2 = new ReadStream("stderr", proc.getErrorStream());
			//s3 = new ReadStream("stdout", proc.getOutputStream());
			s1.start();
			s2.start();
			//s3.start();
			proc.waitFor();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (proc != null)
				proc.destroy();
		}
	}
}
