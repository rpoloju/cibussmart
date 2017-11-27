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

public class UserIdCounts {

	public static void main(String[] args) throws IOException {

		FileInputStream in = null;
		// FileOutputStream out = null;
		File newFile = null;
		FileWriter fw = null;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		BufferedWriter bw = null;

		try {
			in = new FileInputStream("E:\\useridcounts");
			fw = new FileWriter("E:\\countstop.txt");
			// out = new FileOutputStream("E:\\output.txt");
			bw = new BufferedWriter(fw);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			/*
			 * if(!newFile.exists()) newFile.createNewFile();
			 */
			String strLine, line[];
			while ((strLine = br.readLine()) != null) {
				line = strLine.split("\t");
				map.put(line[0], Integer.parseInt(line[1]));

			}

			Map<String, Integer> sortedMap = Test.sortByValue(map);

			for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
				//System.out.println("Item is:" + entry.getKey() + " with value:" + entry.getValue());
				bw.write(entry.getKey() + " rated by " + entry.getValue() + "\n");
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
