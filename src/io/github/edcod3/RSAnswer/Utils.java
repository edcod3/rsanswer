package io.github.edcod3.RSAnswer;

//HTTP Libraries
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.URI;

//regex Libraries
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String makeRequest(String uriString) {
        String responseBody;
        URI uri = URI.create(uriString);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseBody = response.body();
        } catch (IOException | InterruptedException e) {
            responseBody = "";
            e.printStackTrace();
            System.exit(-1);
        }
        return responseBody;
    }

    public static String regexFilter(String regex, String input, int groupPosition) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher matches = pattern.matcher(input);
        if (matches.find()) {
            return matches.group(groupPosition);
        } else {
            return "None";
        }
    }
}
