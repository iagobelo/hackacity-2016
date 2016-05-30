package com.around.around.parser;

import android.util.Log;

import com.around.around.model.People;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by IagoBelo on 28/05/2016.
 */
public class JSONHandler {
    private People mPeople;
    private ArrayList<People> mList;

    public JSONHandler() {
    }

    /**
     * @param jsonString
     * @return Retorna um arraylist com todos os objetos obtidos através da string passada
     * @throws JSONException
     */
    public ArrayList<People> parsePeopleJSON(String jsonString) throws JSONException {
        if (jsonString != null) {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("contextResponses");

            mList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                mPeople = new People();
                if (jsonArray.getJSONObject(i).getJSONObject("contextElement").getString("type").equals("People")) {
                    mPeople.setLatitude(jsonArray.getJSONObject(i).getJSONObject("contextElement").getJSONArray("attributes").getJSONObject(0).getInt("value"));
                    mPeople.setLongitude(jsonArray.getJSONObject(i).getJSONObject("contextElement").getJSONArray("attributes").getJSONObject(1).getInt("value"));
                    mPeople.setName(jsonArray.getJSONObject(i).getJSONObject("contextElement").getJSONArray("attributes").getJSONObject(2).getString("value"));

                    mList.add(mPeople);
                    //Log.i("JSON_DEBUG", "posicao: " + i + ", nome: " + mList.get(i).getName());
                }
            }
            Log.i("JSON", String.valueOf(mList.size()));
            for (int i = 0; i < mList.size(); i++) {
                Log.i("JSON", mList.get(i).getName());
            }
            return mList;
        } else {
            return null;
        }
    }
}
