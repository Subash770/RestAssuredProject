package com.example.RestAssuredProject.Api;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;


public class TestPost {
    @Test
    public void test1_post() throws JSONException {
       //step 1//create a map to store the variables
//        Map<String,Object> map=new HashMap<String,Object>();
//       map.put("name","subash");
//       map.put("job","Software Engineer");
//        System.out.println(map);

        //covert it into JSON data using json parser from simple.json dependency
       // JSONObject request =new JSONObject(map);
        JSONObject request =new JSONObject();
         request.put("name","John");
         request.put("job","Teacher");

        System.out.println(request);
       // System.out.println(request.toString());
    //   post("https://reqres.in/api/users")

    given()
            .body(request.toString()).
            when().post("https://reqres.in/api/users").then().statusCode(201);
//201-created
    }
    @Test
    public void test2_put() throws JSONException {
        //step 1//create a map to store the variables
//        Map<String,Object> map=new HashMap<String,Object>();
//       map.put("name","subash");
//       map.put("job","Software Engineer");
//        System.out.println(map);

        //covert it into JSON data using json parser from simple.json dependency
        // JSONObject request =new JSONObject(map);
        JSONObject request =new JSONObject();
        request.put("name","John");
        request.put("job","Teacher");

        System.out.println(request);
        // System.out.println(request.toString());
        //   post("https://reqres.in/api/users")

        given()
                .body(request.toString()).
                when().put("https://reqres.in/api/users/2").then().
                statusCode(200)
                .log().all();

    }

    @Test
    public void test3_patch() throws JSONException {
        //step 1//create a map to store the variables
//        Map<String,Object> map=new HashMap<String,Object>();
//       map.put("name","subash");
//       map.put("job","Software Engineer");
//        System.out.println(map);

        //covert it into JSON data using json parser from simple.json dependency
        // JSONObject request =new JSONObject(map);
        JSONObject request =new JSONObject();
        request.put("name","John");
        request.put("job","Teacher");

        System.out.println(request);
        // System.out.println(request.toString());
        //   post("https://reqres.in/api/users")

        given()
                .body(request.toString()).
                when().patch("https://reqres.in/api/users/2").then().
                statusCode(200)
                .log().all();
//200-success-ok
    }

    @Test
    public void test4_delete() throws JSONException {
//204-no content
          when().delete("https://reqres.in/api/users/2").then().
                statusCode(204)
                .log().all();

    }

}
