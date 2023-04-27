package com.example.ChitChat;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.github.cdimascio.dotenv.Dotenv;

@RestController
public class AuthController {
    static Dotenv dotenv = Dotenv.load();

    private static String CHAT_ENGINE_PROJECT_ID = dotenv.get("CHAT_ENGINE_PROJECT_ID");
    private static String CHAT_ENGINE_PRIVATE_KEY = dotenv.get("CHAT_ENGINE_PRIVATE_KEY");

    //Login Endpoint. Make API get request to ChatEngine
    @CrossOrigin
    @RequestMapping(path="/login", method = RequestMethod.POST)
    public ResponseEntity getLogin(@RequestBody HashMap<String, String> request){
        HttpURLConnection connection = null;
        try {
            //Create GET request
            URL url = new URL("https://api.chatengine.io/users/me");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Request Headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            System.out.println(CHAT_ENGINE_PROJECT_ID);
            connection.setRequestProperty("Project-ID", CHAT_ENGINE_PROJECT_ID);
            connection.setRequestProperty("User-Name", request.get("username"));
            connection.setRequestProperty("User-Secret", request.get("secret"));
            // Generate response String
            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    responseStr.append(responseLine.trim());
                }
            }
            // Jsonify + return response
            Map<String, Object> response = new Gson().fromJson(
                    responseStr.toString(), new TypeToken<HashMap<String, Object>>() {
                    }.getType());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    //Signup Endpoint. Make API post request to ChatEngine
    @CrossOrigin
    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ResponseEntity newSignup(@RequestBody HashMap<String, String> request) {
        HttpURLConnection connection = null;
        try {
            // Create POST request
            URL url = new URL("https://api.chatengine.io/users");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            // Set headers
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Private-Key", CHAT_ENGINE_PRIVATE_KEY);
            // Add request body
            connection.setDoOutput(true);
            Map<String, String> body = new HashMap<String, String>();
            body.put("username", request.get("username"));
            body.put("secret", request.get("secret"));
            body.put("email", request.get("email"));
            body.put("first_name", request.get("first_name"));
            body.put("last_name", request.get("last_name"));
            String jsonInputString = new JSONObject(body).toString();
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            // Generate response String
            StringBuilder responseStr = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    responseStr.append(responseLine.trim());
                }
            }
            // Jsonify + return response
            Map<String, Object> response = new Gson().fromJson(
                    responseStr.toString(), new TypeToken<HashMap<String, Object>>() {
                    }.getType());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
