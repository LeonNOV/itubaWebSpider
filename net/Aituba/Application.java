package net.Aituba;

import net.Aituba.HtmlUtils.HtmlUtils;
import net.Aituba.ParseUtils.ParsePicList;

import java.util.ArrayList;

public class Application {

    /**
     * 主调方法
     */
    public void RUN() {
        HtmlUtils htmlUtils = new HtmlUtils();
        //获取页面的html源码
        String html = htmlUtils.getHtml("http://www.ituba.cc/siwa/");

        ParsePicList parsePicList = new ParsePicList();

        //获取页面中的每一个套图的url
        ArrayList<String> picListUrlList = parsePicList.getPicListUrl(html);

        //获取页面中的每一个套图的标题
        ArrayList<String> picListTitleList = parsePicList.getPicListTitle(html);

        //获取原图
        parsePicList.ParsePic(picListUrlList, picListTitleList);
    }
}
