package com.iberianmotorsports.service.utils;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

public class Utils {

    @SneakyThrows
    public static byte[] loadContent(String path) {
        return new ClassPathResource("content/" + path).getInputStream().readAllBytes();
    }

    @SneakyThrows
    public static String loadContentString(String path) {
        byte[] content = new ClassPathResource("content/" + path).getInputStream().readAllBytes();
        return new String(content, StandardCharsets.UTF_8);
    }

}
