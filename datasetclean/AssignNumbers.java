package datasetclean;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AssignNumbers {
	


	public static void main(String[] args) throws IOException {

		FileInputStream javain = null;
		FileInputStream userin = null;
		//FileInputStream productin = null;
		//FileOutputStream out = null;
		File newFile = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			javain = new FileInputStream("E:\\java_output.txt");
			userin = new FileInputStream("E:\\usertable");
			fw = new FileWriter("E:\\addedusernum.txt");
			//out = new FileOutputStream("E:\\output.txt");
			bw = new BufferedWriter(fw);
			BufferedReader brjava = new BufferedReader(new InputStreamReader(javain));
			BufferedReader bruser = new BufferedReader(new InputStreamReader(userin));
			
			Map<String, String> usermap = new HashMap<>();
			String str, strmain;
			String splitter[] = new String[2];
			while((str = bruser.readLine()) != null) {
				splitter = str.split(",");
				usermap.put(splitter[0],splitter[1]);
			}
			
			String splitter1[] = new String[3];
			while((strmain = brjava.readLine()) != null) {
				splitter1 = strmain.split(",");
				bw.write(strmain + "," + usermap.get(splitter1[1]) + "\n");
			}
			
			/*if(!newFile.exists())
				newFile.createNewFile();
			*/
			/*String strLine, productId, userId, rating;
			while ((strLine = br.readLine()) != null)   {
				 bw.write(strLine + "," + br.readLine() + "," + br.readLine() + "\n");

			}*/

		} catch (Exception e) {
			// Do nothing
		}

		finally {
			if (userin != null) {
				userin.close();
			}
			if (javain != null) {
				javain.close();
			}
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}

		}
	}
	


}
