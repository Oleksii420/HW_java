import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class B_11_03 extends JFrame {
    public static void main(String[] args) {
        try {
            String baseUrl = "https://godinnik.com/time/";
            String city = "київ";
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
            String targetUrl = baseUrl + encodedCity + "/";

            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                Pattern pattern = Pattern.compile("<noscript>(\\d{2}:\\d{2}:\\d{2})</noscript>");
                Matcher matcher = pattern.matcher(content.toString());

                if (matcher.find()) {
                    String siteTimeStr = matcher.group(1);

                    LocalTime now = LocalTime.now();
                    String localTimeStr = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    System.out.println("Site time: " + siteTimeStr);
                    System.out.println("Local time: " + localTimeStr);

                    if (siteTimeStr.equals(localTimeStr)) {
                        System.out.println("Time matches.");
                    } else {
                        System.out.println("Time does not match.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}