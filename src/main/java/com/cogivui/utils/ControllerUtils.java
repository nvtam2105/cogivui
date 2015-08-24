package com.cogivui.utils;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ControllerUtils {

	public static String br2nl(String html) {
		if (StringUtils.isNotBlank(html)) {
			Document document = Jsoup.parse(html);
			document.select("br").append("\\n");

			Elements spans = document.select("span");
			for (Object o : spans.toArray()) {
				Element e = (Element) o;
				if (e.hasText()) {
					e.prepend("\\n\\n");
				}
			}

			Elements ps = document.select("p");
			for (Object o : ps.toArray()) {
				Element e = (Element) o;
				if (e.hasText()) {
					e.prepend("\\n\\n");
				}
			}

			Elements divs = document.select("div");
			for (Object o : divs.toArray()) {
				Element e = (Element) o;
				if (e.hasText()) {
					e.prepend("\\n\\n");
				}
			}
			return document.text().replace("\\n", "\n");
		}
		return "";
	}
}
