package com.example;

import org.jsoup.Jsoup;

public class Practice {
    public static void main(String[] args) {
        System.out.println("karma");
        try {
            System.out.println(Jsoup.connect("http://www.infomoney.com.br/mercados/bitcoin").ignoreContentType(true).execute().body());
        } catch (Exception e) {

        }
    }
}
