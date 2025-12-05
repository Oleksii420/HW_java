import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class B_11_07 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name in English (e.g., Kyiv): ");
        String city = scanner.nextLine().trim();

        if (city.isEmpty()) {
            System.out.println("City name cannot be empty.");
            return;
        }

        String targetUrl = "https://www.meteoprog.com/ua/weather/" + city + "/";

        try {
            URL url = new URL(targetUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            connection.setRequestMethod("GET");
            connection.setInstanceFollowRedirects(true);

            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error: Connection failed. Response Code: " + responseCode);
                return;
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            String html = content.toString();

            List<String> maxTemps = new ArrayList<>();
            List<String> minTemps = new ArrayList<>();

            Pattern maxPattern = Pattern.compile("class=\"temperature-max\"[^>]*?>\\s*(?:<[^>]+>\\s*)*([+-]?\\d+)");
            Matcher maxMatcher = maxPattern.matcher(html);
            while (maxMatcher.find()) {
                maxTemps.add(maxMatcher.group(1));
            }

            Pattern minPattern = Pattern.compile("class=\"temperature-min\"[^>]*?>\\s*(?:<[^>]+>\\s*)*([+-]?\\d+)");
            Matcher minMatcher = minPattern.matcher(html);
            while (minMatcher.find()) {
                minTemps.add(minMatcher.group(1));
            }

            System.out.println("Current Date: " + LocalDate.now());

            if (minTemps.isEmpty() || maxTemps.isEmpty()) {
                System.out.println("WARNING: Weather data not found in HTML. Check the city name or website structure.");
            } else {
                int count = 0;
                for (int i = 1; i < minTemps.size() && count < 4; i++) {
                    if (i < maxTemps.size()) {
                        System.out.println("Day " + (count + 1) + ": Min " + minTemps.get(i) + "° / Max " + maxTemps.get(i) + "°");
                        count++;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}