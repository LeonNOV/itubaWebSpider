package net.Aituba.HtmlUtils;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DowloadImag {

    /**
     * 通过Image链接获取原图
     *
     * @param ImagUrl Imag链接
     * @param title 文件夹名称
     * @return 返回原图的存在状态（是否下载成功）
     */
    public boolean getImage(String ImagUrl, String title) {
        File mainfile = new File("D:\\Aituba");

        //判断在盘符中是否存在Aituba主文件夹
        if (!mainfile.exists()) {
            mainfile.mkdirs();
        }

        String imageName = getImageName(ImagUrl);
        String imageType = getImageType(ImagUrl);

        //判断在Aituba文件夹中是否存在子文件夹（存放每一个套图的文件夹）
        File folder = new File("D:\\Aituba\\" + title);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(ImagUrl);

        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Safari/537.36");

        CloseableHttpResponse response;

        try {
            response = httpClient.execute(httpGet);

            if (response.getCode() == 200) {
                if (response.getEntity() != null) {
                    FileOutputStream fos = new FileOutputStream("D:\\Aituba\\" + title + "\\" + imageName + imageType);
                    response.getEntity().writeTo(fos);
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解析链接获取文件名
     *
     * @param ImageUrl Imag链接
     * @return 返回解析到的文件名
     */
    private String getImageName(String ImageUrl) {
        // http://222.186.12.239:20011/uploads/2020/0416/20200416030311312.jpg
        String name = ImageUrl.substring(ImageUrl.lastIndexOf("/")).split("\\.")[0];

        return name;
    }

    /**
     * 解析Imag链接获取文件扩展名
     *
     * @param ImageUrl Imag链接
     * @return 返回解析到的文件扩展名
     */
    private String getImageType(String ImageUrl) {
        // //t1.ituba.cc/uploads/2020/0416/20200416030311312.jpg
        String type = ImageUrl.substring(ImageUrl.lastIndexOf("."));

        return type;
    }
}
