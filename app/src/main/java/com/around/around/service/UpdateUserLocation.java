package com.around.around.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.around.around.activitys.IOnResult;
import com.around.around.model.People;
import com.around.around.parser.JSONHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by IagoBelo on 28/05/2016.
 */
public class UpdateUserLocation {
    private static UpdateUserLocation instance;
    private static Context mContext;
    private static RequestQueue mRequestQueue;
    private static JSONHandler mJsonHandler;
    private final String URL_UPDATE = "http://130.206.119.17:1026/v1/updateContext";
    private final String URL_APPEND = "http://130.206.119.17:1026/v1/updateContext";
    private final String URL_QUERY = "http://130.206.119.17:1026/v1/queryContext";

    public void getLocation(final IOnResult iOnResult) throws JSONException {
        ArrayList<People> peoples;
        JSONObject jsonObject = new JSONObject("{\n" +
                "    \"entities\": [\n" +
                "        {\n" +
                "            \"type\": \"People\",\n" +
                "            \"isPattern\": \"true\",\n" +
                "            \"id\": \"People.*\"\n" +
                "        }] ,\n" +
                "        \"attributes\": [\n" +
                "            \"latitude\",\n" +
                "            \"longitude\",\n" +
                "            \"name\",\n" +
                "            \"id\"\n" +
                "        ]\n" +
                "}");

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, "http://130.206.119.17:1026/v1/contextEntities/?=*", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("UPDATE_LOG", response.toString());
                        try {
                            iOnResult.onResult(mJsonHandler.parsePeopleJSON(response.toString()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("UPDATE_LOG", "ERRRO");
                    }
                });
        mRequestQueue.add(jsonRequest);
    }

    public static UpdateUserLocation getInstance(Context context) {
        if (instance == null) {
            instance = new UpdateUserLocation();
            mJsonHandler = new JSONHandler();
        }
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
        return instance;
    }

    public void postFirstLocation(String id, double latitude, double longitude, String name) throws JSONException {
        JSONObject jsonObject = new JSONObject("{\n" +
                "    \"contextElements\": [\n" +
                "        {\n" +
                "            \"type\": \"People\",\n" +
                "            \"isPattern\": \"false\",\n" +
                "            \"id\":" + id +
                "            \"attributes\": [\n" +
                "            {\n" +
                "                \"name\": \"latitude\",\n" +
                "                \"type\": \"double\",\n" +
                "                \"value\": " + latitude +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"longitude\",\n" +
                "                \"type\": \"double\",\n" +
                "                \"value\": " + longitude +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"name\",\n" +
                "                \"type\": \"String\",\n" +
                "                \"value\": " + name +
                "            }\n" +
                "\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"updateAction\": \"APPEND\"\n" +
                "}");

        final String[] serverResponse = {null};
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, URL_APPEND, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("LOGGGG", response.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("LOGGG", "ERRRO");
                    }
                });
        mRequestQueue.add(jsonRequest);
    }

    public void updateLocation(String id, double latitude, double longitude, String name) throws JSONException {
        JSONObject jsonObject = new JSONObject("{\n" +
                "    \"contextElements\": [\n" +
                "        {\n" +
                "            \"type\": \"People\",\n" +
                "            \"isPattern\": \"false\",\n" +
                "            \"id\":" + id + "," +
                "            \"attributes\": [\n" +
                "            {\n" +
                "                \"name\": \"latitude\",\n" +
                "                \"type\": \"double\",\n" +
                "                \"value\": " + latitude +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"longitude\",\n" +
                "                \"type\": \"double\",\n" +
                "                \"value\": " + longitude +
                "            },\n" +
                "            {\n" +
                "                \"name\": \"name\",\n" +
                "                \"type\": \"String\",\n" +
                "                \"value\": " + name +
                "            }\n" +
                "\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"updateAction\": \"UPDATE\"\n" +
                "}");

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, URL_UPDATE, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("LOGG", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.i("LOGG", error.toString());
                    }
                });
        mRequestQueue.add(jsonRequest);
    }

    /*public String getCorrectUrl() {
        return correctUrl;
    }*/
}
