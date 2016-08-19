package cn.itcast.news;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.sax.StartElementListener;
import android.util.Xml;

public class xmlprase {
	public static List<News> Parser(InputStream in) throws Exception {
		List<News> newslists = null;
		News news = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(in, "gb2312");
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if ("channel".equals(parser.getName())) {
					newslists = new ArrayList<News>();
				} else if ("item".equals(parser.getName())) {
					news = new News();

				} else if ("title".equals(parser.getName())) {

					news.setTitle(parser.nextText());

				} else if ("description".equals(parser.getName())) {
					news.setDescription(parser.nextText());
				}
				else if ("image".equals(parser.getName())) {
					news.setImage(parser.nextText());
				}
				else if ("type".equals(parser.getName())) {
					news.setType(parser.nextText());
				}
				else if ("comment".equals(parser.getName())) {
					news.setComment(parser.nextText());
				}

				break;

			case XmlPullParser.END_TAG:
				if ("item".equals(parser.getName())) {
					newslists.add(news);
					
				}

				break;
			}
			type = parser.next();
		}

		return newslists;

	}

}
