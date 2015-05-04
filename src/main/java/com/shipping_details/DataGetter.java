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
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataGetter {

	public static void main(final String[] args) throws IOException {
		// IEC-0313046506
		// name-bit

		// IEC-3408000590
		// name-art
		getAllIECValues();
	}

	private static void getAllIECValues() throws IOException {
		StringBuffer sb = getValuesFromDGFT();

		FileOutputStream fos = null;
		BufferedWriter writer = null;
		try {
			fos = new FileOutputStream("/Users/subramanya.vl/Documents/jsoup/sample_out"
					+ Calendar.getInstance().getTimeInMillis() + ".csv");
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
			while ((line = reader.readLine()) != null) {
				if (line != null) {
					String[] values = line.split(",");
					Connection.Response res = Jsoup.connect("http://dgft.delhi.nic.in:8100/dgft/IecPrint?show=all")
							.data("iec", values[0], "name", values[1]).userAgent("Mozilla").timeout(6000)
							.method(Method.POST).execute();
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