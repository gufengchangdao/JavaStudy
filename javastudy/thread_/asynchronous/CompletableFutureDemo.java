package javastudy.thread_.asynchronous;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 读取一个Web页面，扫描页面得到其中的图像并保存到本地
 */
public class CompletableFutureDemo {
    // HTML文本中匹配图片地址的正则表达式
    private static final Pattern IMG_PATTERN = Pattern.compile(
            "[<]\\s*[iI][mM][gG]\\s*[^>]*[sS][rR][cC]\\s*[=]\\s*['\"]([^'\"]*)['\"][^>]*[>]");
    // 处理任务的线程池
    private ExecutorService executor = Executors.newCachedThreadPool();
    private URL urlToProcess;

    /**
     * 读取Web页面的HTML内容
     *
     * @return 存有该页面HTML内容的 CompletableFuture<String> 对象
     */
    public CompletableFuture<String> readPage(URL url) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                String contents = new String(url.openStream().readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Read page from " + url);
                return contents;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }, executor);
    }

    /**
     * 获取HTML内容中的图片地址
     *
     * @param webpage HTML内容
     * @return 图片地址组成的集合
     */
    public List<URL> getImageURLs(String webpage) {
        try {
            var result = new ArrayList<URL>();
            Matcher matcher = IMG_PATTERN.matcher(webpage);
            while (matcher.find()) {
                var url = new URL(urlToProcess, matcher.group(1));
                result.add(url);
            }
            System.out.println("Found URLs: " + result);
            return result;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * 读取图片URL中的图片数据
     *
     * @param urls 图片URL集合
     * @return BufferedImage集合
     */
    public CompletableFuture<List<BufferedImage>> getImages(List<URL> urls) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                int count = 0;
                // TODO: 2022/1/17 保存的图片上限
                int limit = 10;
                var result = new ArrayList<BufferedImage>();
                for (URL url : urls) {
                    result.add(ImageIO.read(url));
                    if (++count >= limit) break;
                }
                for (BufferedImage image : result) {
                    System.out.println(image);
                }
                return result;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }, executor);
    }

    /**
     * 保存图片到本地
     *
     * @param images 要保存的图片集合
     */
    public void saveImages(List<BufferedImage> images) {
        String directory = "images/";
        System.out.println("Saving " + images.size() + " images");
        try {
            for (int i = 0; i < images.size(); i++) {
                String filename = directory + "image" + (i + 1) + ".png";
                ImageIO.write(images.get(i), "PNG", new File(filename));
            }
            System.out.println("成功");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        executor.shutdown();
    }

    public void run(URL url) throws IOException, InterruptedException {
        urlToProcess = url;
        CompletableFuture.completedFuture(url)
                .thenComposeAsync(this::readPage, executor)
                .thenApply(this::getImageURLs)
                .thenCompose(this::getImages)
                .thenAccept(this::saveImages);
         
      /*
      // or use the experimental HTTP client:
       
      HttpClient client = HttpClient.newBuilder().executor(executor).build();
      HttpRequest request = HttpRequest.newBuilder(urlToProcess.toURI()).GET()
         .build();
      client.sendAsync(request, BodyProcessor.asString())
         .thenApply(HttpResponse::body).thenApply(this::getImageURLs)
         .thenCompose(this::getImages).thenAccept(this::saveImages);
      */
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // new CompletableFutureDemo().run(new URL("http://horstmann.com/index.html")); //这个网页保存不了图片
        new CompletableFutureDemo().run(new URL("https://mobile.zol.com.cn/455/4556333_all.html"));
    }
}
