package com.example.demo;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser {
	
	public String pageTitle(String args) throws IOException, HttpStatusException {
	
		Connection conn = Jsoup.connect(args).userAgent("Mozilla");
		conn.timeout(9000).ignoreHttpErrors(true).followRedirects(true);
		if (conn.execute().statusCode() == 200) {
			Document doc = conn.get();
			if (doc != null && doc.title() != null) {
				return doc.title();
			}
		}
		return "N";
		
	}

}
