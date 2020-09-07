## 王者荣耀故事会

> 因为需要，所以创造。 ——某开源社区

[![](https://my-wechat.mdnice.com/dance.gif)](https://mp.weixin.qq.com/s/lM808MxUu6tp8zU8SBu3sg)
喜欢玩手游的朋友们应该都玩过某讯的王者农药，鄙人作为一个手游渣渣也玩过几次，常用英雄为亚瑟、安琪拉、鲁班......。玩几局就被其中每个英雄唯美的UI设计所吸引（但是最常玩还是射击类游戏，有喜欢玩的可以关注私聊我呦）,但是对所有的英雄的荣耀并不太了解。所以为了了解每个英雄的典故，我从昨天10点到次日2点，撸代码撸出了这个开源程序（**因为需要，所以创造**）

### 说说这个程序
##### 模块和技术栈
首先这个程序主要包括两个部分，分别是**数据抓取和处理**、**数据展示**。主要使用的技术栈为：
- Java8
- Okhttp (应用层)
- Jsoup (数据解析)
- JSP+CSS（界面有点丑，哈哈）
##### 看一下实际效果(哈哈，真丑)

![image-20200907210755536](https://github.com/wencaixu/Kings-Glory-Story-Club/blob/master/src/main/test/image-20200907210755536.png)

##### 看看实现核心代码吧
接口
```java
// 解析
public interface Parser {
    void parser() throws ExecutionException, InterruptedException;
}
// 抓取
public interface Crawler<T,R> {
   String doGet(String uri, Map<T,R> headers);
   default void setHttpHeaders(Request.Builder builder, Map<T,R> headers){
      if(headers == null || headers.isEmpty()){
         return ;
      }
      for(Map.Entry<T,R> entry : headers.entrySet()){
         builder.addHeader(String.valueOf(entry.getKey()),String.valueOf(entry.getValue()));
      }
   }
}
```
抓取公共方法
```java
public class HttpCrawler implements Crawler<String,String> {
    private OkHttpClient httpClient = new OkHttpClient();
    private static HttpCrawler instance = new HttpCrawler();
    @Override
    public String doGet(String uri, Map<String,String> headers) {
        assert uri != null;
        Request.Builder httpBuilder = new Request.Builder();
        // 设置请求头部
        setHttpHeaders(httpBuilder,headers);
        Request request = httpBuilder.url(uri).build();
        Response response;
        String page = "";
        try{
            response = httpClient.newCall(request).execute();
            if(!response.isSuccessful()){
                throw new HttpStatusException(http_error.getMsg(),response.code(),uri);
            }
            ResponseBody responseBody = response.body();
            if(Objects.nonNull(responseBody)){
                byte[] bytes = responseBody.bytes();
                page = new String(bytes,Charsets.GB2312.name());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }
    public static HttpCrawler getInstance() {
        return instance;
    }
}
```
解析英雄
```java
public class KingParser implements Parser {
    private static KingParser kingParser = new KingParser();
    private StoryParser storyParser = StoryParser.getInstance();
    private String page;
    private List<Hero> heros = new ArrayList<>();

    private ExecutorService executors = Executors.newCachedThreadPool(new ThreadFactory() {
        AtomicInteger integer = new AtomicInteger();
        @Override
        public Thread newThread(@NotNull Runnable r) {
            return new Thread(r,"parser-thread-"+integer.getAndIncrement());
        }
    });
    @Override
    public void parser() throws ExecutionException, InterruptedException {
        Document document = Jsoup.parse(page);
        if(document == null || StringUtils.isEmpty(document.body().html())){
            return;
        }
        Elements heroBox = document.getElementsByClass(WebAppConfig.kingClassName);
        Elements heroLists = heroBox.get(0).getElementsByTag(li.name());
        long start;
        System.out.println("开始时间==="+(start=System.currentTimeMillis()));
        AtomicInteger count = new AtomicInteger();
        for(Element element : heroLists){
            Hero hero = new Hero();
            count.getAndIncrement();
            Future<Object> submit = executors.submit(() -> {
                Elements aTag = element.getElementsByTag(a.name());
                String uri = WebAppConfig.baseUri + aTag.attr(href.name());
                hero.setDetail(parserStory(uri));
                hero.setHero(aTag.get(0).getElementsByTag(img.name()).get(0).attr(alt.name()));
                hero.setPicture("http:" + aTag.get(0).getElementsByTag(img.name()).get(0).attr(src.name()));
                return hero;
            });
            heros.add((Hero) submit.get());
        }
        //4922
        System.out.println("结束时间==="+(System.currentTimeMillis()-start));
        System.out.println("共抓取:"+count.get());
    }
    public static KingParser getInstance(){
        return kingParser;
    }
    private String parserStory(String uri){
        storyParser.setUri(uri);
        storyParser.parser();
        return storyParser.getStory();
    }
    public void setPage(String page) {
        this.page = page;
    }
    public List<Hero> getHeros(){
        if(CollectionUtils.isEmpty(heros)){
            try {
                parser();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return heros;
    }
}
```
解析故事
```java
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
```
##### 代码还有需要优化的地方
- 缓存：每次处理都需要多次请求解析，可以使用缓存替代。
- 界面：界面不够美观，可以使用Javascript和CSS3进行页面动态化。

### Github欢迎提issue
[Github地址](https://github.com/wencaixu/Kings-Glory-Story-Club)
### 关注我
![](https://imgkr2.cn-bj.ufileos.com/ff383301-db27-473b-97ff-caa746faa000.png?UCloudPublicKey=TOKEN_8d8b72be-579a-4e83-bfd0-5f6ce1546f13&Signature=A1zoPSKqyoiRjFYl%252Fq2%252FoGZu0K4%253D&Expires=1598847241)