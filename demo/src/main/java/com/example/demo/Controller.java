package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@RequestMapping(value = "/append", method = RequestMethod.POST)
	public String apppend(Payload payload) throws IOException {

		DataParser dp = new DataParser();
		HtmlParser hp = new HtmlParser();
		String[] linksString;
		StringBuffer links = new StringBuffer();

		FileInputStream in = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		int userid;

		// String[] productIds = { "B000ENUC3S", "B007JFMH8M", "B005K4Q4LK",
		// "B000CNB4LE", "B0051COPH6", "B006MONQMC",
		// "B003QNJYXM", "B001RVFERK" };

		String[] productNums = { "8719", "74010", "33401", "30428", "71102", "42826", "36029", "11891", "6687", "63528",
				"17779", "40989", "42282", "30806", "19338", "30430" };

		String[] rating = { payload.getapplepierating(), payload.getbabygourmetrating(),
				payload.getGreniesdogtreatsrating(), payload.getNutivacoconutoilrating(), payload.getcapuccinorating(),
				payload.getcoconutwaterrating(), payload.getHerbaltearating(), payload.getOrangetangarinerating(),
				payload.getenergydrinkrating(), payload.getfruitpunchrating(), payload.getCheddarbunniesrating(),
				payload.getGummybearsrating(), payload.getOatmealcookiesrating(), payload.getpopchipsrating(),
				payload.getPeanutbutterrating(), payload.getPomegranaterating() };

		try {
			File homedir = new File("");
			System.out.println("the path is " + homedir.getPath());
			System.out.println(homedir.exists());
			File file = new File(homedir, "/usr/local/ratings.csv");
			System.out.println(file.getPath());
			System.out.println(file.exists());
			in = new FileInputStream(file.getPath());
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			File file2 = new File(homedir, "/usr/local/ratings2.csv");
			file2.createNewFile();
			fw = new FileWriter(file2.getPath());
			bw = new BufferedWriter(fw);
			
			
			String lastLine = "", currentLine;
			while ((currentLine = br.readLine()) != null) {
				lastLine = currentLine;
				bw.write(lastLine + "\n");
			}
			System.out.println("####################last line############" + lastLine);
			br.close();

			

			String lastLineSplit[] = new String[3];
			lastLineSplit = lastLine.split(",");
			userid = Integer.parseInt(lastLineSplit[0]) + 1;
			String[] sparkArgUserId = { Integer.toString(userid) };
			for (int i = 0; i < 16; i++) {
				if (rating[i] != null) {
					String newData = Integer.toString(userid) + "," + productNums[i] + "," + rating[i];
					bw.write(newData + "\n");
				}
			}

			bw.flush();

			String[] command = { "/bin/sh", "-c", "rm -r /usr/local/ops" };
			Process proc = new ProcessBuilder(command).start();
			proc.waitFor();

			System.out.println("##################argument############" + sparkArgUserId);
			
			
			//calling the spark launcher
			//StartSparkLauncher.main(sparkArgUserId);
			
			//calling thrugh command line
			SubmitSpark.main(sparkArgUserId);
			

			String[] command1 = { "/bin/sh", "-c", "rm -r /usr/local/ratings.csv" };
			Process proc1 = new ProcessBuilder(command1).start();
			proc1.waitFor();
			
			String[] command2 = { "/bin/sh", "-c", "mv /usr/local/ratings2.csv /usr/local/ratings.csv" };
			Process proc2 = new ProcessBuilder(command2).start();
			proc2.waitFor();

		} catch (Exception e) {
			System.out.println("Exception");
		} finally {

			try {
				if (in != null)
					in.close();
				if (fw != null)
					fw.close();
				if (bw != null)
					bw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		linksString = dp.parseData();

		links.append("<html>");
		links.append("<h3>These are the recommended products for you...<h3>");
		links.append("<br/>");

		String title;
		for (int i = 0; i < 10; i++) {
			title = hp.pageTitle(linksString[i]);
			if (title != "N") {
				links.append("<a href=" + linksString[i] + ">" + "  " + title + "</a>");
				links.append("<br/>");
			} else {
				links.append("Product Discontinued on amazon");
				links.append("<br/>");
			}
		}

		links.append("</html>");

		return links.toString();
	}

	/*
	 * @RequestMapping(value = "/spark", method = RequestMethod.GET) public String
	 * sparkExecutor() throws Exception { StartSparkLauncher.main(null); return
	 * "spark"; }
	 */
}
