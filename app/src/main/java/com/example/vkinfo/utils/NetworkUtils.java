package com.example.vkinfo.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NetworkUtils {
    private static final String urlBace = "https://api.vk.com";
    private static final String urlMethod = "/method/users.get";
    private static final String userID = "user_ids";
    private static final String paramVersion = "v";
    private static final String token = "access_token";
    private static final String tokenN = "22fa4e3c22fa4e3c22fa4e3c892295a1e3222fa22fa4e3c7cac2949aafcec7398d5b6a5";

    public static URL generateURL(String userId) {

        Uri builUri = Uri.parse(urlBace + urlMethod)
                .buildUpon()
                .appendQueryParameter(userID, userId)
                .appendQueryParameter(paramVersion, "5.89")
                .appendQueryParameter(token, tokenN)
                .build();
        URL url = null;
        try {
            url = new URL(builUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getUrlResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } catch (UnknownHostException e) {
            return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}
////https://api.vk.com/method/users.get?user_ids=5958535&v=5.89&access_token=22fa4e3c22fa4e3c22fa4e3c892295a1e3222fa22fa4e3c7cac2949aafcec7398d5b6a5