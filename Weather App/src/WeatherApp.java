import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherApp7Day {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter city latitude: ");
        String lat = scanner.nextLine();

        System.out.print("Enter city longitude: ");
        String lon = scanner.nextLine();

        System.out.print("Enter number of days (1-7): ");
        String cnt = scanner.nextLine();

        String apiKey = "YOUR_API_KEY"; // replace with your API key
        String urlString = "https://api.openweathermap.org/data/2.5/forecast/daily?lat="
                + lat + "&lon=" + lon + "&cnt=" + cnt + "&appid=" + apiKey + "&units=metric";

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonArray forecastArray = jsonObject.getAsJsonArray("list");

            System.out.println("\n7-Day Forecast:");
            for (int i = 0; i < forecastArray.size(); i++) {
                JsonObject day = forecastArray.get(i).getAsJsonObject();
                double temp = day.getAsJsonObject("temp").get("day").getAsDouble();
                int humidity = day.get("humidity").getAsInt();
                String description = day.getAsJsonArray("weather")
                        .get(0).getAsJsonObject()
                        .get("description").getAsString();
                System.out.println("Day " + (i+1) + ": " + temp + "°C, Humidity: " + humidity + "%, " + description);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
