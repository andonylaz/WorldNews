package com.example.monash.worldnews;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * This class purely consists of helper methods, which add functionality to the app.
 *
 *
 *
 * Created by monash on 11/15/2016.
 */

public class QueryUtils {

    /**
     * Class has a private constructor so nobody can ever create an instance
     */
    private QueryUtils(){

    }

    // guardian url query to get the proper json response
    public static final String GUARDIAN_URL_QUERY = "http://content.guardianapis.com/search?" +
            "show-tags=contributor&show-fields=headline%2CtrailText%2Cheadline%2CfirstPublicationDate" +
            "%2Cthumbnail&from-date="+getDate()+"&page-size=6&q=TRUMP%20OR%20TURNBULL%20OR%20PUTIN%20OR%20" +
            "ASSANGE%20OR%20SNOWDEN&api-key=test";


    public static String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        return dateFormat.format(cal.getTime()).toString();
    }



    public static ArrayList<News> fetchNewsArticleData(String urlString){
        String jsonResponse = null;
        // create the url first
        URL url = createURL(urlString);

        // create the http request and read in the data
        jsonResponse = createHttpRequest(url);

        // extract the data and save in ArrayList
        ArrayList<News> newsArticles = extractJsonResponse(jsonResponse);

        return newsArticles;
    }





    /**
     *
     * @param url a string that will be converted to a URL object
     * @return a URL object for a passed string
     */
    public static URL createURL(String url){

        URL createdUrl = null;

        // if no string has been passed
        if (url == null | url == ""){
            return null;
        }

        try {
            createdUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "Error trying to create URL object");
        }

        return createdUrl;
    }







    /**
     *
     * @param url th destination with proper parameters to get the guardian articles
     * @return jsonResponse - a string that contains the entire response from server
     */
    public static String createHttpRequest(URL url){
        InputStream inputStream = null;
        String jsonResponse = null;


        try {
            // Returns a URLConnection instance that represents a
            // connection to the remote object referred to by the URL.
            HttpURLConnection guardianConn = (HttpURLConnection) url.openConnection();
            guardianConn.setRequestMethod("GET");
            // establish connection
            guardianConn.connect();

            // get response code
            int responseCode = guardianConn.getResponseCode();
            if (responseCode == 200){
                // .getInputStream() returns an input stream that reads from this open connection
                inputStream = guardianConn.getInputStream();
                jsonResponse = getDataFromStream(inputStream);
            }
        } catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "Error trying to establish connection");
        }

        return jsonResponse;
    }





    /**
     *
     * @param inputStream the input stream that reads from the open connection
     * @return jsonResponse string that contains all of the requested JSON
     */
    public static String getDataFromStream(InputStream inputStream){
        String jsonResponse = null;
        StringBuilder progressString = new StringBuilder();


        try{
        // inputStream is the stream that is connected to the HTTP connection
        // inputStreamReader grabs all the ones and zeros and decodes them into the specified charset
        // bufferedReader doesn't store any data it only reads it
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        // read in data
            String line = bufferedReader.readLine();
            while (line != null){
                Log.i(TAG, line + "<----- Current line");
                progressString.append(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e){
            e.printStackTrace();
            Log.e(TAG, "problem reading in data from stream -- inside getDatafromStream()");
        }

        jsonResponse = progressString.toString();
        return jsonResponse;
    }




    /**
     *
     * @param jsonResponse a string with the jsonResponse from The Guardian
     * @return newsArticles ArrayList containing key data from Articles
     * @throws JSONException will throw if the response cannot be parsed into JSON, caller
     *                       must handle the response.
     */
    public static ArrayList<News> extractJsonResponse(String jsonResponse) {

        // if string is blank or null return null
        //if (jsonResponse == null | jsonResponse ==  ""){
        //    return null;
        //}

        // Create a list of news objects
        ArrayList<News> newsArticles = new ArrayList<>();

        try {

            // create a JSON object from jsonResponse
            JSONObject rootNewsReponse = new JSONObject(jsonResponse);
            Log.i(TAG, "created root response object");

            // get the JSON 'response' object
            JSONObject responseObject = rootNewsReponse.optJSONObject("response");

            // find the JSON Array with the key "results"
            JSONArray responseResultsArray = responseObject.optJSONArray("results");
            Log.i(TAG, "created results array successfully");
            // loop through each array and extract the data
            for (int i = 0; i < responseResultsArray.length(); i++) {

                // create the JSON object for that array element
                JSONObject currentElement = responseResultsArray.optJSONObject(i);
                Log.i(TAG, "created object in results array");

                // determine whether the object is an article or blog, article is needed.
                // so get the key value for 'type'
                String isArticle = currentElement.optString("type");
                Log.i("is Article", "The follow is the key valye for type:  " + isArticle);

                // determine whether the data is an article
                if ("article".equals(isArticle)){

                    // extract the url of the article
                    String articleUrl = currentElement.optString("webUrl");
                    Log.i(TAG, "retrieved webURL");
                    // get the JSON object that contains all of the 'fields' data
                    JSONObject fieldsDataObject = currentElement.optJSONObject("fields");
                    Log.i(TAG, "retrieved fields object");
                    // get fields data: Title, Description(trailText), data, image URL
                    String articleTitle = fieldsDataObject.optString("headline");
                    Log.i(TAG, "retrieved headline");
                    String articleDescription = fieldsDataObject.optString("trailText");
                    Log.i(TAG, "retrieved trailtext");
                    String articleDate = fieldsDataObject.optString("firstPublicationDate");
                    Log.i(TAG, "retrieved date");
                    String articleImageUrl = fieldsDataObject.optString("thumbnail");
                    Log.i(TAG, "retrieved thumbnail");
                    // now go into 'tags' Array and retrieve Author(s) of article
                    JSONArray tagsArray = currentElement.optJSONArray("tags");
                    Log.i(TAG, "retrieved retrieved tags array");
                    // Gets the "webTitle" data which contains the authors name

                    // get the first object in tags
                    JSONObject insideTagsArrayObject = tagsArray.getJSONObject(0);

                    // now get the author from inside that object
                    String articleAuthor = insideTagsArrayObject.optString("webTitle");
                    Log.i(TAG, "retrieved author author is:   " + articleAuthor);
                    // create new News object
                    newsArticles.add(new News(articleUrl, articleImageUrl, articleDate, articleAuthor,
                            articleTitle, articleDescription));
                }
            }
        }catch(JSONException e) {
            Log.e(TAG, "Error trying to parse the JSON response");
                e.printStackTrace();
            }


        return newsArticles;
    }
}
