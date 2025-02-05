package com.reportportal.ta.services;

import static org.awaitility.Awaitility.await;

import com.reportportal.ta.webdriver.WebDriverWrapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.awaitility.core.ConditionTimeoutException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SelenoidService {

    private static final Charset PROJECT_ENCODING = StandardCharsets.UTF_8;
    private static final int POLL_VIDEO_INTERVAL_MILLIS = 1000;
    private static final int POLL_FILES_INTERVAL_MILLIS = 1000;
    private static final int WAIT_FOR_FILES_MILLIS = 20_000;

    @Value("${com.reportportal.ta.ui.selenoid.url:http://selenoid:4444}")
    private String selenoidUrl;

    @Value("${path.to.download.folder}")
    private String pathToDownloadFolder;

    @Value("${com.reportportal.ta.ui.wait.for.selenoid.video.millis:30000}")
    private long waitForVideoMillis;

    @Autowired
    private WebDriverWrapper driver;

    public URL getVideoUrl(String sessionId) {
        URL url = null;

        try {
            url = new URL(selenoidUrl + "/video/" + sessionId + ".mp4");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return url;
    }

    public InputStream getSelenoidVideoIS(String sessionId) {
        URL videoUrl = getVideoUrl(sessionId);
        log.info("Starting to download video: " + videoUrl.toString());

        log.debug("Awaiting video: " + videoUrl);
        try {
            await()
                .atMost(Duration.ofMillis(waitForVideoMillis))
                .with()
                .pollInterval(Duration.ofMillis(POLL_VIDEO_INTERVAL_MILLIS))
                .until(() -> {
                    URLConnection urlConnection = videoUrl.openConnection();
                    int responseCode = ((HttpURLConnection) urlConnection).getResponseCode();
                    log.debug("Response code: " + responseCode);
                    return responseCode == 200;
                });

            log.debug("Video is ready: " + videoUrl);
        } catch (ConditionTimeoutException e) {
            log.error("Sorry but we couldn't wait for the video from selenoid: " + videoUrl, e);
        } catch (Exception e) {
            log.error("Error while downloading the video from selenoid: " + videoUrl, e);
        }

        try {
            return videoUrl.openStream();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    public File getSelenoidVideoFile(String sessionId) {
        File file =
            new File(System.getProperty("user.dir") + pathToDownloadFolder + "/selenoid_videos/" + sessionId + ".mp4");

        try {
            FileUtils.copyInputStreamToFile(getSelenoidVideoIS(sessionId), file);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return file;
    }

    public List<URL> getSelenoidDownloadedFilesUrls() {
        List<URL> fileUrls = new ArrayList<>();
        String baseUrl = selenoidUrl + "/download/" + driver.getSessionId() + "/";

        try {
            Document document = Jsoup.connect(baseUrl).get();
            for (Element element : document.select("a")) {
                try {
                    URL url = new URL(baseUrl + element.attr("href"));
                    fileUrls.add(url);
                } catch (MalformedURLException e) {
                    log.error(e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return fileUrls;
    }

    public void downloadSelenoidFilesToDownloadDirectory() {
        log.info("Download all Selenoid Files from container.");

        String baseUrl = selenoidUrl + "/download/" + driver.getSessionId() + "/";

        log.info(String.format("Awaiting for Selenoid Downloaded files. SessionID: %s; URL: %s",
            driver.getSessionId(), baseUrl));
        try {
            await()
                .atMost(Duration.ofMillis(WAIT_FOR_FILES_MILLIS))
                .with()
                .pollInterval(Duration.ofMillis(POLL_FILES_INTERVAL_MILLIS))
                .until(() -> !(getSelenoidDownloadedFilesUrls().isEmpty()));

            log.debug("Files are ready: " + baseUrl);
        } catch (ConditionTimeoutException e) {
            log.error("Sorry but we couldn't wait for files from selenoid: " + baseUrl, e);
        }

        List<URL> fileUrls = getSelenoidDownloadedFilesUrls();

        for (URL url : fileUrls) {
            String fileName = java.net.URLDecoder.decode(FilenameUtils.getName(url.getPath()), PROJECT_ENCODING);
            log.info(String.format("Download file %s from url: %s", fileName, url));

            File file = new File(System.getProperty("user.dir") + pathToDownloadFolder + "/" + fileName);
            try {
                FileUtils.copyURLToFile(url, file);

                log.info(String.format("File %s downloaded successfully.", fileName));
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}

