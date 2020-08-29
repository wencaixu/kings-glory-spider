package com.kings.glory.crawler.parser;

import com.kings.glory.crawler.http.HttpCrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 王者荣耀故事解析
 *
 * @author wencai.xu
 */
public class StoryParser implements Parser{

    private String uri;
    private String story;
    private HttpCrawler httpCrawler = HttpCrawler.getInstance();
    private static StoryParser storyParser = new StoryParser();

    @Override
    public void parser() {
        String detailPage = httpCrawler.doGet(uri, null);
        Document parse = Jsoup.parse(detailPage);
        Element heroStory = parse.getElementById("hero-story");
        Element element = heroStory.getElementsByClass("pop-bd").get(0);
        story = element.html();
    }

    public String getStory() { return story; }

    public void setUri(String uri) { this.uri = uri; }

    public static StoryParser getInstance() { return storyParser; }
}
