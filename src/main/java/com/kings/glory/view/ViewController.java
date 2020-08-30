package com.kings.glory.view;

import com.kings.glory.crawler.WebAppConfig;
import com.kings.glory.crawler.http.HttpCrawler;
import com.kings.glory.crawler.parser.KingParser;
import com.kings.glory.vo.Hero;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewController  extends HttpServlet {

    private static KingParser kingParser = KingParser.getInstance();
    private static HttpCrawler httpCrawler = HttpCrawler.getInstance();

    private static List<Hero> heroList = new ArrayList<>();
    private static Map<String,String> headers = new HashMap<>();

    private static void execute(){
        String page = httpCrawler.doGet(WebAppConfig.uri,headers);
        kingParser.setPage(page);
        heroList = kingParser.getHeros();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        execute();

        // PrintWriter out = resp.getWriter();
        // out.println(heroList);
        req.setAttribute("hero",heroList);
        req.getRequestDispatcher("/view.jsp").forward(req,resp);
        // out.flush();
        // out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
       this.doGet(req,resp);
    }
}
