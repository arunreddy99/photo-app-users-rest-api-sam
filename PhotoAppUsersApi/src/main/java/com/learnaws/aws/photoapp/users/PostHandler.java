package com.learnaws.aws.photoapp.users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Handler for requests to Lambda function.
 */
public class PostHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    public APIGatewayProxyResponseEvent handleRequest(final APIGatewayProxyRequestEvent input, final Context context) {
    	LambdaLogger logger = context.getLogger();
    	logger.log("Handling  Post request for /users ");
        String request= input.getBody();
        Gson gson= new Gson();
        Map<String, String> userDetails= gson.fromJson(request, Map.class);
        userDetails.put("userId", UUID.randomUUID().toString());
        
//        todo:  process user detsils
        Map returnValue = new HashMap();
        returnValue.put("firstName", userDetails.get("firstName"));
        returnValue.put("lastName", userDetails.get("lastName"));
        returnValue.put("userId", userDetails.get("userId"));
        
        APIGatewayProxyResponseEvent response= new APIGatewayProxyResponseEvent();
        response.withStatusCode(200);
        response.withBody(gson.toJson(returnValue, Map.class));
        
        Map responseHeaders = new HashMap();
        responseHeaders.put("Content-Type", "application/json");
        response.withHeaders(responseHeaders);
            return response;
    }


    
}
