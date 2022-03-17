package javastudy.utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
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
public class WebpageImageUtils {
    // HTML文本中匹配图片地址的正则表达式
    private static final Pattern IMG_PATTERN = Pattern.compile(
            "[<]\\s*[iI][mM][gG]\\s*[^>]*[sS][rR][cC]\\s*[=]\\s*['\"]([^'\"]*)['\"][^>]*[>]");
    // 处理任务的线程池
    private ExecutorService executor = Executors.newCachedThreadPool();
    private URL urlToProcess;
    // 图片保存的目录
    private String directory;
    // 图片文件后缀名(格式)
    private String formatName;
    // 要保存文件的数量限制
    private int countLimit;

    private WebpageImageUtils() {
    }

    private WebpageImageUtils(String directory, String formatName) {
        this.directory = directory;
        this.formatName = formatName;
    }

    /**
     * 读取Web页面的HTML内容
     *
     * @return 存有该页面HTML内容的 CompletableFuture<String> 对象
     */
    private CompletableFuture<String> readPage(URL url) {
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
    private List<URL> getImageURLs(String webpage) {
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
    private CompletableFuture<List<BufferedImage>> getImages(List<URL> urls) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                int count = 0;
                // TODO: 2022/1/17 保存的图片上限
                var result = new ArrayList<BufferedImage>();
                for (URL url : urls) {
                    result.add(ImageIO.read(url));
                    if (++count >= countLimit) break;
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
    private void saveImages(List<BufferedImage> images) {
        System.out.println("Saving " + images.size() + " images");
        try {
            for (int i = 0; i < images.size(); i++) {
                String filename = directory + "image" + (i + 1) + "." + formatName;
                ImageIO.write(images.get(i), formatName.toUpperCase(), new File(filename));
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        executor.shutdown();
    }

    private void run(URL url) throws IOException, InterruptedException {
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

    /**
     * 读取一个Web页面，扫描页面得到其中的图像并保存到本地
     *
     * @param url        网页地址
     * @param directory  要保存的文件目录
     * @param countLimit 图片保存的数量限制
     * @param formatName 图片格式名
     */
    public static void readWebpageImageToLocal(String url, String directory, int countLimit, String formatName) {
        WebpageImageUtils handler = new WebpageImageUtils(directory, formatName);
        handler.countLimit = countLimit;
        try {
            handler.run(new URL(url));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取一个Web页面，扫描页面得到其中的图像并保存到本地，该方法会保存所有读取到的图片
     *
     * @param url        网页地址
     * @param directory  要保存的文件目录
     * @param formatName 图片格式名
     */
    public static void readWebpageImageToLocal(String url, String directory, String formatName) {
        readWebpageImageToLocal(url, directory, Integer.MAX_VALUE, formatName);
    }
}
