package com.example.gpttgbot.telegram;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URI;

@Slf4j
@Service
public class TelegramFileService {
    private DefaultAbsSender telegramSender;
    private final String botToken;

    public TelegramFileService(@Lazy DefaultAbsSender telegramSender,
                               @Value("${bot.token}") String botToken) {
        this.telegramSender = telegramSender;
        this.botToken = botToken;
    }

    @SneakyThrows
    public java.io.File getFile(String field) {
        File file = telegramSender.execute(
                GetFile.builder()
                        .fileId(field)
                        .build());
        var urlToDownloadFile = file.getFileUrl(botToken);

        return getFileFromUrl(urlToDownloadFile);
    }

    @SneakyThrows
    private byte[] getByteArrayFromUrl(String urlString) {
        URL url = new URI(urlString).toURL();
        try(InputStream inputStream = url.openStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer, 0, buffer.length)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            log.error("Error read download file: ", e);
            throw new RuntimeException("error download file: ", e);
        }
    }

    @SneakyThrows
    private java.io.File getFileFromUrl(String urlString) {
        URL url = new URI(urlString).toURL();
        var fileTmp = java.io.File.createTempFile("telegram", ".ogg");

        try(InputStream inputStream = url.openStream();
            FileOutputStream fileOutputStream = new FileOutputStream(fileTmp)
        ) {
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (IOException e) {
            log.error("Error read download file: ", e);
            throw new RuntimeException("error download file: ", e);
        }
        return fileTmp;
    }
}
