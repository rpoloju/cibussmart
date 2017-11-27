package datasetclean;

import java.io.*;

public class DataSet {

	public static void main(String[] args) throws IOException {

		FileInputStream in = null;
		//FileOutputStream out = null;
		File newFile = null;
		FileWriter fw = null;
		BufferedWriter bw = null;

		try {
			in = new FileInputStream("E:\\pig_output.txt");
			fw = new FileWriter("E:\\java_output.txt");
			//out = new FileOutputStream("E:\\output.txt");
			bw = new BufferedWriter(fw);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			/*if(!newFile.exists())
				newFile.createNewFile();
			*/
			String strLine, productId, userId, rating;
			while ((strLine = br.readLine()) != null)   {
				 bw.write(strLine + "," + br.readLine() + "," + br.readLine() + "\n");

			}

		} catch (Exception e) {
			// Do nothing
		}

		finally {
			if (in != null) {
				in.close();
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
