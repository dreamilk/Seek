package cn.shanghq.seek.DataSet;

/**
 * Created by 17634 on 2018/2/5.
 */

public class NewsData {

    private String title;
    private String time;
    private String src;
    private String category;
    private String pic;
    private String content;
    private String url;

    public NewsData(String title,String time,String src,String category,String pic,String content,String url){
        this.category=category;
        this.content=content;
        this.pic=pic;
        this.src=src;
        this.time=time;
        this.title=title;
        this.url=url;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getSrc() {
        return src;
    }

    public String getCategory() {
        return category;
    }

    public String getPic() {
        return pic;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
