package com.shipping_details;

/**
 * Hello world!
 *
 */
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class App {
	public static void main(String[] args) {
		String htmlFragment = "<div class=\"breadcrumb\">";
		htmlFragment += "<ul><li><a href=\"/\">Home</a></li>";
		htmlFragment += "<li><a href=\"#cat1\">Category 1</a></li>";
		htmlFragment += "</ul></div>";
		Document doc = Jsoup.parseBodyFragment(htmlFragment);
		Element div = doc.body().select("div").first();
		Element a1 = div.select("ul a").first();
		Element a2 = div.select("ul a").get(1);
		System.out.println(String.format("The div has the class '%s'", div.attr("class")));
		System.out.println(String.format("The first link in the breadcrum has the text '%s' and links to '%s'.",
				a1.text(), a1.attr("href")));
		System.out.println(String.format("The second link in the breadcrumb has the text '%s' and links to '%s'",
				a2.text(), a2.attr("href")));

	}
}
