package com.shipping_details;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
	public static void main(final String[] args) throws IOException {
		// Document doc = Jsoup.connect("http://www.hascode.com/")
		// Document doc = Jsoup.connect("")
		// .userAgent("Mozilla").timeout(6000).get();
		// String title = doc.title(); // parsing the page's title
		// System.out.println("The title of www.hascode.com is: " + title);
		// Elements heading = doc.select("h2 > a"); // parsing the latest
		// article's
		// // heading
		// System.out.println("The latest article is: " + heading.text());
		// System.out.println("The article's URL is: " + heading.attr("href"));
		// Elements editorial = doc.select("div.BlockContent-body small");
		// System.out.println("The was created: " + editorial.text());
		// String title = doc.title();
		// System.out.println("The document details: " + title);

		// IEC-0313046506
		// name-bit

		// IEC-3408000590
		// name-art

		/*FileInputStream fis = null;
		BufferedReader reader = null;
		String line = null;
		try {
			fis = new FileInputStream("/Users/subramanya.vl/Documents/jsoup/sample.csv");
			reader = new BufferedReader(new InputStreamReader(fis));
			System.out.println("Reading File line by line using BufferedReader");

			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] values = line.split(",");
					getValuesForIEC(values[0], values[1]);
				}
				// System.out.println(line);
			}

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}*/
		getAllIECValues();

	}

	private static void getAllIECValues() throws IOException {
		StringBuffer sb = getValuesFromDGFT();
		
		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			fos = new FileOutputStream("/Users/subramanya.vl/Documents/jsoup/sample_out"+Calendar.getInstance().getTimeInMillis()+  ".csv");
			writer = new BufferedWriter(new OutputStreamWriter(fos));
			writer.write(sb.toString());

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				writer.close();
				fos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		
	}

	private static StringBuffer getValuesFromDGFT() throws IOException {
		StringBuffer sb = new StringBuffer();
		
		FileInputStream fis = null;
		BufferedReader reader = null;
		String line = null;
		try {
			fis = new FileInputStream("/Users/subramanya.vl/Documents/jsoup/sample.csv");
			reader = new BufferedReader(new InputStreamReader(fis));
			System.out.println("Reading File line by line using BufferedReader");
			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] values = line.split(",");
					Connection.Response res = Jsoup.connect("http://dgft.delhi.nic.in:8100/dgft/IecPrint?show=all")
							.data("iec", values[0], "name", values[1]).userAgent("Mozilla").timeout(6000).method(Method.POST).execute();
					Document doc = res.parse();
					// System.out.println("doc--"+doc);
					for (Element table : doc.select("table")) {
						for (Element row : table.select("tr")) {
							Elements tds = row.select("td");
							if (tds != null && tds.size() > 0) {
								for (Element tdEach : tds) {
									System.out.print(tdEach.text());
									if (tdEach.text().trim().equals(":")) {
										sb.append(",");
									} else {
										sb.append(tdEach.text());
									}
								}
								// System.out.println(tds.get(2).text());
							}
							System.out.println();
							sb.append("\n");
						}
					}
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
			
			
		return sb;
	}
}
