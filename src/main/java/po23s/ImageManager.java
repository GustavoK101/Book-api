package po23s;

import po23s.http.Callback;
import po23s.http.ClienteHttp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageManager {
    private Image defaultImage;

    private static final ImageManager instance = new ImageManager();
    private final ExecutorService executor = Executors.newFixedThreadPool(4);


    private ImageManager() {
        try {
            defaultImage = ImageIO.read(ImageManager.class.getResource("/image.png")).getScaledInstance(120, 80, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static ImageManager getInstance() {
        return instance;
    }

    public Image getImage(String url) {
        if (url == null || url.isEmpty()) {
            return defaultImage;
        }


        Image image = getImageFromFileCache(url);// check file cache
        if (image != null) {
            return image;
        }

        ClienteHttp http = new ClienteHttp();
        byte[] raw = http.getRaw(url);
        saveImageToCache(url, raw);
        return Toolkit.getDefaultToolkit().createImage(raw);
    }


    private static String getUrlSha1(String url) {
        try {
            if (url == null || url.isEmpty()) {
                return "null";
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(url.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCacheDir() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        String cacheDir = tmpDir + FileSystems.getDefault().getSeparator() + "books-api-cache";
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
        return cacheDir;
    }

    public Image getImageFromFileCache(String url) {
        String sha1 = getUrlSha1(url);
        String cacheFile = getCacheDir() + "/" + sha1;

        // check if file exists
        if (Files.exists(Paths.get(cacheFile))) {
            try {
                return ImageIO.read(new File(cacheFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void saveImageToCache(String url, byte[] raw) {
        String sha1 = getUrlSha1(url);
        String cacheFile = getCacheDir() + "/" + sha1;
        try {
            Files.write(Paths.get(cacheFile), raw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getImageAsync(String url, Callback<Image> callback) {
        executor.submit(() -> {
            try {
                callback.onDone(getImage(url), null);
            } catch (Exception e) {
                callback.onDone(null, e);
            }
        });
    }

    public Image getDefaultImage() {
        return defaultImage;
    }


}