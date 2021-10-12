package matryca_led_gui;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WiFi {
    WiFi(){

    }

    void sendRequest(String json_data){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://192.168.0.190"))
                .POST(HttpRequest.BodyPublishers.ofString(json_data))
                .build();
        try {
           client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> { System.out.println(response.statusCode());
                        return response; } )
                    .thenApply(HttpResponse::body)
                    .thenAccept(System.out::println);
        }catch(Exception ex){};

    }
}
