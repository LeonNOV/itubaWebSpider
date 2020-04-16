package net.Aituba.ParseUtils;

import net.Aituba.HtmlUtils.DowloadImag;
import net.Aituba.HtmlUtils.HtmlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ParsePicList {
    /**
     * 获取页面上的每一个套图的url
     *
     * @param html 页面源码
     * @return 返回一个含有页面上所有套图的url的集合
     */
    public ArrayList<String> getPicListUrl(String html) {
        ArrayList<String> picUrlList = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements id = document.select("#NewList");
        for (Element element : id) {
            Elements pictxt = element.select("[class=PicTxt]");
            for (Element element1 : pictxt) {
                String href = element1.select("[href]").toString().split("\"")[1];
                picUrlList.add(href);
            }
        }
        return picUrlList;
    }

    /**
     * 获取页面上每一个套图的标题
     *
     * @param html 页面源码
     * @return 返回一个含有页面上所有套图的标题的集合
     */
    public ArrayList<String> getPicListTitle(String html) {
        ArrayList<String> picTitleList = new ArrayList<>();
        Document document = Jsoup.parse(html);

        Elements id = document.select("#NewList");
        for (Element element : id) {
            Elements pictxt = element.select("[class=PicTxt]");
            for (Element element1 : pictxt) {
                String title = element1.select("[title]").toString().split("\"")[3];
                picTitleList.add(title);
            }
        }
        return picTitleList;
    }

    /**
     * 获取每一个套图的中每一个原图的url，通过url来下载原图
     *
     * @param picUrlList 存放每一个套图的url的集合
     * @param picTitleList 存放每个套图的标题
     */
    public void ParsePic(ArrayList<String> picUrlList, ArrayList<String> picTitleList) {
        DowloadImag dowloadImag = new DowloadImag();

        int count = 0;
        for (String url : picUrlList) {

            //先要获取每一个套图的第一张原图的url
            String firstImagUrl = getImagUrl(url);
            boolean firststate = dowloadImag.getImage(firstImagUrl, picTitleList.get(count));
            if (firststate) {
                System.out.println(firstImagUrl + "获取成功");
            } else {
                System.out.println(firstImagUrl + "获取失败");
            }

            //获取每一个套图的
            int pageMaxNum = getPageMaxNum(url);

            for (int i = 2; i <= pageMaxNum; i++) {
                try {
                    Thread.sleep(1000);
                    //http://www.ituba.cc/sexy/69259_+页码.html
                    String PicUrl =url.split(".html")[0] + "_" + i + ".html";

                    //获取套图中的每一张原图的url
                    String imagUrl = getImagUrl(PicUrl);

                    boolean state = dowloadImag.getImage(imagUrl, picTitleList.get(count));
                    if (state) {
                        System.out.println(imagUrl + "获取成功");
                    } else {
                        System.out.println(imagUrl + "获取失败");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            count++;
        }
    }

    /**
     * 获取套图中的最大图片数
     *
     * @param FirstUrl 套图的链接
     * @return 返回套图中的最大图片数
     */
    private int getPageMaxNum(String FirstUrl) {
        HtmlUtils htmlUtils = new HtmlUtils();

        String firstHtml = htmlUtils.getHtml(FirstUrl);

        Document document = Jsoup.parse(firstHtml);
        Elements pagenum = document.select("[class=page-en]");
        String text = pagenum.get(6).text();

        return Integer.parseInt(text);
    }

    /**
     * 获取套图中所有的原图url
     *
     * @param Picurl 套图链接
     * @return 返回获取到的原图url
     */
    private String getImagUrl(String Picurl) {
        HtmlUtils htmlUtils = new HtmlUtils();

        String picHtml = htmlUtils.getHtml(Picurl);

        Document document = Jsoup.parse(picHtml);

        Elements pages = document.select("[style=text-align: center;]");
        String ImagUrl = pages.get(0).toString().split("src=")[1].split("\"")[1];

        //这里必须要加http:否则会抛出ClientProtocolException
        return "http:" + ImagUrl;
    }
}
