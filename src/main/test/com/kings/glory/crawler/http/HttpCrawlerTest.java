package com.kings.glory.crawler.http;

import com.kings.glory.crawler.parser.KingParser;
import org.junit.Test;

import java.util.concurrent.ExecutionException;


public class HttpCrawlerTest {

    @Test
    private void testHttpCrawler() throws ExecutionException, InterruptedException {
            HttpCrawler crawler = new HttpCrawler();
            String s = crawler.doGet("https://pvp.qq.com/web201605/herolist.shtml", null);

            KingParser parser = new KingParser();
            parser.setPage(s);
            parser.parser();
    }

}