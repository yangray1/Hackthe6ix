package com.example.photogallarytest;

// // This sample uses the Apache HTTP client from HTTP Components (http://hc.apache.org/httpcomponents-client-ga/)
import android.os.AsyncTask;

import java.lang.reflect.Array;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JavaSample
{
    String url;
    String percentTotalled = "";
    public JavaSample(String url){
        this.url = url;
        call();
    }
    public void call()
    {
        CallHTTP callHTTP = new CallHTTP(url);
        callHTTP.execute();
        while (callHTTP.getStatus() != AsyncTask.Status.FINISHED){
        }
    }

    class CallHTTP extends AsyncTask<String, Void, String> {

        private String url = "";
        public CallHTTP(String incomingUrl) {
            url = "{Url : '" + incomingUrl + "'}";
        }

        protected String doInBackground(String... urls) {

            HttpClient httpclient = new DefaultHttpClient();
            try {
                URIBuilder builder = new URIBuilder("https://eastus.api.cognitive.microsoft.com/customvision/v1.1/Prediction/afc04c3d-0519-417b-8e78-c5c3810d48a9/url");

                builder.setParameter("iterationId", "e29695b8-486c-43ab-ae1d-169c6fb6a5ec");

                URI uri = builder.build();
                HttpPost request = new HttpPost(uri);
                request.setHeader("Content-Type", "application/json");
                request.setHeader("Prediction-key", "43b8892e95fa486e8606f2f4a4ff0aee");

                // Request body
                StringEntity reqEntity = new StringEntity(url); // Need to pass in a json string.
                request.setEntity(reqEntity);

                HttpResponse response = httpclient.execute(request);

                HttpEntity entity = response.getEntity();
                if (entity != null)
                {
                    // Parse json
                    JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
                    String test = jsonObject.getString("Predictions");

                    test = test.substring(test.indexOf("{"), test.indexOf("}") + 1);
                    jsonObject = new JSONObject(test);

                    String out =  jsonObject.getString("Probability");
                    return out;
//                    return "";
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }

            return null;
        }

        protected void onPostExecute(String percentTotaled) {
            storeValue(percentTotaled);
        }
    }
    private String parsePercentageTotaled(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        jsonObject=new JSONObject(jsonObject.getString("Predictions"));
        String out =  jsonObject.getString("Probability");
        return out;
    }
    private void storeValue(String valueOfTotaled) {
        //handle value
        this.percentTotalled = valueOfTotaled;
    }

    public String getPercentTotalled(){
        return percentTotalled;
    }

}