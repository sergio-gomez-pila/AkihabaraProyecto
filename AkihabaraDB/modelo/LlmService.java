package modelo;

import java.io.FileInputStream;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import com.google.gson.*;

public class LlmService {

    // URL de la API de OpenRouter
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    // Modelo que vamos a usar para las respuestas (gratuito)
    private static final String MODELO = "mistralai/mistral-7b-instruct:free";

    // Aquí guardamos la clave de la API, que se carga desde el archivo de propiedades
    private static String apiKey;

    // Bloque estático que se ejecuta una vez al cargar la clase
    // Lee el archivo config.properties y guarda la clave en apiKey
    static {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("config.properties")); // Carga el archivo externo
            apiKey = props.getProperty("openrouter.key"); // Saca la clave con la etiqueta
        } catch (Exception e) {
            System.out.println(" Error cargando la API Key: " + e.getMessage());
            apiKey = ""; // Por si acaso, dejamos apiKey como string vacío
        }
    }

    // Este método manda un prompt a la IA y devuelve la respuesta que genera
    public String preguntarLLM(String prompt) {
        try {
            // Creamos el cuerpo JSON con el modelo y el mensaje del usuario
            JsonObject root = new JsonObject();
            root.addProperty("model", MODELO);

            JsonArray messages = new JsonArray();
            JsonObject msg = new JsonObject();
            msg.addProperty("role", "user");
            msg.addProperty("content", prompt);
            messages.add(msg);
            root.add("messages", messages);

            // Preparamos la petición HTTP con los headers y el JSON
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(root.toString(), StandardCharsets.UTF_8))
                .build();

            // Mandamos la petición y recogemos la respuesta
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Parseamos la respuesta JSON para sacar el texto generado por la IA
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            return json
                .getAsJsonArray("choices")
                .get(0).getAsJsonObject()
                .getAsJsonObject("message")
                .get("content").getAsString()
                .trim(); // quitamos espacios por si acaso

        } catch (Exception e) {
            return "ERROR al conectar con la IA: " + e.getMessage();
        }
    }
}


