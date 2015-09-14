package com.cogivui.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class ControllerUtils {

	public static List<Long> extractCategoryIds(String categoryIds) {
		String[] categoryIdsStr = StringUtils.split(categoryIds, ",");
		List<Long> ids = new ArrayList<Long>(0);
		for (String id : categoryIdsStr) {
			if (!"0".equals(id)) {
				ids.add(Long.valueOf(id));
			}
		}
		return ids;
	}

	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}
	
	public static String html2TextKeepBreakline(String html) {
		String text = Jsoup.parse(html.replaceAll("(?i)<br[^>]*>", "br2n")).text();
		return text.replaceAll("br2n", "\n");
	}

	public static String br2nl(String html) {
		if (StringUtils.isNotBlank(html)) {
			Document document = Jsoup.parse(html);
			document.select("br").append("\\n");

			Elements spans = document.select("span");
			for (Object o : spans.toArray()) {
				Element e = (Element) o;
				if (e.hasText()) {
					e.prepend("\\n");
				}
			}

			Elements ps = document.select("p");
			for (Object o : ps.toArray()) {
				Element e = (Element) o;
				if (e.hasText()) {
					e.prepend("\\n");
				}
			}

			Elements divs = document.select("div");
			for (Object o : divs.toArray()) {
				Element e = (Element) o;
				if (e.hasText()) {
					e.prepend("\\n");
				}
			}
			return document.text().replace("\\n", "\n");
		}
		return "";
	}
}
