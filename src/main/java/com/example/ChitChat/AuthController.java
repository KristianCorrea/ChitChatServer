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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class AuthController {
    //SETUP .ENV VARIABLE HERE
    private static String CHAT_ENGINE_PROJECT_ID = "";
    private static String CHAT_ENGINE_PRIVATE_KEY = "";

    //Login Endpoint. Make API get request to ChatEngine

    //Signup Endpoint. Make API post request to ChatEngine
}
