import com.kings.glory.crawler.http.HttpCrawler;
import com.kings.glory.crawler.parser.KingParser;
import com.kings.glory.vo.Hero;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static KingParser kingParser = KingParser.getInstance();
    private static HttpCrawler httpCrawler = HttpCrawler.getInstance();

    private static List<Hero> heroList = new ArrayList<>();

    private static void execute(){
        String page = httpCrawler.doGet("https://pvp.qq.com/web201605/herolist.shtml", null);
        kingParser.setPage(page);
        heroList = kingParser.getHeros();
    }

    public static void main(String[] args) {
        execute();
        for(Hero hero : heroList){
            System.out.println(hero.toString());
        }
    }
}
