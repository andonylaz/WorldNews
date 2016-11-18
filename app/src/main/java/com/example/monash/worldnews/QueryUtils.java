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
import java.util.ArrayList;

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
            "%2Cthumbnail&from-date=2016-11-14&page-size=6&q=TRUMP%20OR%20TURNBULL%20OR%20PUTIN%20OR%20" +
            "ASSANGE%20OR%20SNOWDEN&api-key=test";

    /**
    public static final String FAKE_JSON_RESPONSE = "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":131,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":14,\"orderBy\":\"relevance\",\"results\":[{\"id" +
            "\":\"australia-news/2016/nov/14/malcolm-turnbull-trump-election-jobs-growth-mantra-right\"" +
            ",\"type\":\"article\",\"sectionId\":\"australia-news\",\"sectionName\":\"Australia news\",\"" +
            "webPublicationDate\":\"2016-11-14T09:28:51Z\",\"webTitle\":\"Malcolm Turnbull: Trump election " +
            "shows 'jobs and growth' mantra was right\",\"webUrl\":\"https://www.theguardian.com/australia-" +
            "news/2016/nov/14/malcolm-turnbull-trump-election-jobs-growth-mantra-right\",\"apiUrl\":\"https" +
            "://content.guardianapis.com/australia-news/2016/nov/14/malcolm-turnbull-trump-election-jobs-" +
            "growth-mantra-right\",\"fields\":{\"headline\":\"Malcolm Turnbull: Trump election shows 'jobs " +
            "and growth' mantra was right\",\"trailText\":\"The prime minister told the ABC the outcome of " +
            "the US election had vindicated his campaign focus on the economy\",\"firstPublicationDate\":\"" +
            "2016-11-14T09:28:51Z\",\"thumbnail\":\"https://media.guim.co.uk/a2b4a5ff0242d8eab690cc4c09abb9" +
            "80f760c21e/0_0_4081_2448/500.jpg\"},\"tags\":[{\"id\":\"profile/gabrielle-chan\",\"type\":\"co" +
            "ntributor\",\"webTitle\":\"Gabrielle Chan\",\"webUrl\":\"https://www.theguardian.com/profile/gabrielle-chan\",\"apiUrl\":\"" +
            "https://content.guardianapis.com/profile/gabrielle-chan\",\"references\":[],\"bio\":\"<p>Gabrielle Ch" +
            "an is a political correspondent for Guardian Australia. She has been a journalist for 30 years</p>\"," +
            "\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2015/5/20/1432098318579/Gabrielle_Chan_140x140.jpg\",\"bylineLargeImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2015/5/20/1432098388381/Gabrielle_Chan_L.png\",\"firstName\":\"gabrielle\",\"lastName\":\"chan\",\"twitterHandle\":\"gabriellechan\"}],\"isHosted\":false},{\"id\":\"us-news/2016/nov/14/vladimir-putin-donald-trump-phone-call\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-11-15T07:27:49Z\",\"webTitle\":\"Putin stresses cooperation in first phone call with president-elect Trump\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/nov/14/vladimir-putin-donald-trump-phone-call\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/nov/14/vladimir-putin-donald-trump-phone-call\",\"fields\":{\"headline\":\"Putin stresses cooperation in first phone call with president-elect Trump\",\"trailText\":\"Trump looking forward to ‘strong and enduring relationship with Russia’ while Kremlin says two leaders share ‘phenomenally similar’ foreign policy outlook\",\"firstPublicationDate\":\"2016-11-14T21:14:04Z\",\"thumbnail\":\"https://media.guim.co.uk/16fb0e5d58367f504d1bd0f5402918b358d1496d/0_0_2560_1536/500.jpg\"},\"tags\":[{\"id\":\"profile/shaun-walker\",\"type\":\"contributor\",\"webTitle\":\"Shaun Walker\",\"webUrl\":\"https://www.theguardian.com/profile/shaun-walker\",\"apiUrl\":\"https://content.guardianapis.com/profile/shaun-walker\",\"references\":[],\"bio\":\"<p>Shaun Walker is Moscow Correspondent for the Guardian. He has lived in Russia for several years and was previously Moscow Correspondent for The Independent.</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2014/3/15/1394900994898/shaunwalkernewbyline.jpg\",\"firstName\":\"shaun\",\"lastName\":\"walker\"},{\"id\":\"profile/julianborger\",\"type\":\"contributor\",\"webTitle\":\"Julian Borger\",\"webUrl\":\"https://www.theguardian.com/profile/julianborger\",\"apiUrl\":\"https://content.guardianapis.com/profile/julianborger\",\"references\":[],\"bio\":\"<p>Julian Borger is the Guardian's world affairs editor. He was previously a correspondent in the US, the Middle East, eastern Europe and the Balkans. His book on the pursuit and capture of the Balkan war criminals, <a href=\\\"https://bookshop.theguardian.com/catalog/product/view/id/359254/?utm_source=editoriallink&amp;utm_medium=merch&amp;utm_campaign=article\\\">The Butcher's Trail</a>, is published by Other Press.</p><p>• <a href=\\\"https://pgp.theguardian.com/PublicKeys/Julian%20Borger.pub.txt\\\">Julian Borger's public key</a></p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/contributor/2016/1/8/1452247825373/Julian-Borger.jpg\",\"bylineLargeImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/contributor/2016/1/8/1452247847854/Julian-Borger-R.png\",\"firstName\":\"\",\"lastName\":\"borger\"}],\"isHosted\":false},{\"id\":\"media/2016/nov/14/julian-assange-to-face-swedish-prosecutors-over-accusation\",\"type\":\"article\",\"sectionId\":\"media\",\"sectionName\":\"Media\",\"webPublicationDate\":\"2016-11-14T13:39:13Z\",\"webTitle\":\"Julian Assange faces Swedish prosecutor in London over rape accusation\",\"webUrl\":\"https://www.theguardian.com/media/2016/nov/14/julian-assange-to-face-swedish-prosecutors-over-accusation\",\"apiUrl\":\"https://content.guardianapis.com/media/2016/nov/14/julian-assange-to-face-swedish-prosecutors-over-accusation\",\"fields\":{\"headline\":\"Julian Assange faces Swedish prosecutor in London over rape accusation\",\"trailText\":\"WikiLeaks founder’s Swedish lawyer says he has been barred from attending interview, which is scheduled to take three days\",\"firstPublicationDate\":\"2016-11-14T07:00:41Z\",\"thumbnail\":\"https://media.guim.co.uk/f9ca6ce75727f80dfd7e70ccd6158a865da23335/0_167_5337_3204/500.jpg\"},\"tags\":[{\"id\":\"profile/estheraddley\",\"type\":\"contributor\",\"webTitle\":\"Esther Addley\",\"webUrl\":\"https://www.theguardian.com/profile/estheraddley\",\"apiUrl\":\"https://content.guardianapis.com/profile/estheraddley\",\"references\":[],\"bio\":\"<p>Esther Addley is senior news writer at the Guardian</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/contributor/2007/09/26/addley_esther140x140.jpg\",\"firstName\":\"esther\",\"lastName\":\"addley\"},{\"id\":\"profile/david-crouch\",\"type\":\"contributor\",\"webTitle\":\"David Crouch\",\"webUrl\":\"https://www.theguardian.com/profile/david-crouch\",\"apiUrl\":\"https://content.guardianapis.com/profile/david-crouch\",\"references\":[],\"firstName\":\"david\",\"lastName\":\"crouch\"}],\"isHosted\":false},{\"id\":\"australia-news/2016/nov/15/malcolm-turnbull-says-shorten-a-rank-opportunist-over-457-visas\",\"type\":\"article\",\"sectionId\":\"australia-news\",\"sectionName\":\"Australia news\",\"webPublicationDate\":\"2016-11-15T04:51:01Z\",\"webTitle\":\"Malcolm Turnbull says Shorten a 'rank opportunist' over 457 visas\",\"webUrl\":\"https://www.theguardian.com/australia-news/2016/nov/15/malcolm-turnbull-says-shorten-a-rank-opportunist-over-457-visas\",\"apiUrl\":\"https://content.guardianapis.com/australia-news/2016/nov/15/malcolm-turnbull-says-shorten-a-rank-opportunist-over-457-visas\",\"fields\":{\"headline\":\"Malcolm Turnbull says Shorten a 'rank opportunist' over 457 visas\",\"trailText\":\"Prime minister says Labor leader’s opposition to higher tax on backpackers on working holiday visas is ‘hypocritical’\",\"firstPublicationDate\":\"2016-11-15T04:51:01Z\",\"thumbnail\":\"https://media.guim.co.uk/03f7c9dfe9c4d3ae3873786dee7b55dd9e962602/0_162_4928_2956/500.jpg\"},\"tags\":[{\"id\":\"profile/gabrielle-chan\",\"type\":\"contributor\",\"webTitle\":\"Gabrielle Chan\",\"webUrl\":\"https://www.theguardian.com/profile/gabrielle-chan\",\"apiUrl\":\"https://content.guardianapis.com/profile/gabrielle-chan\",\"references\":[],\"bio\":\"<p>Gabrielle Chan is a political correspondent for Guardian Australia. She has been a journalist for 30 years</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2015/5/20/1432098318579/Gabrielle_Chan_140x140.jpg\",\"bylineLargeImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2015/5/20/1432098388381/Gabrielle_Chan_L.png\",\"firstName\":\"gabrielle\",\"lastName\":\"chan\",\"twitterHandle\":\"gabriellechan\"}],\"isHosted\":false},{\"id\":\"commentisfree/2016/nov/15/malcolm-turnbull-must-address-the-health-risks-of-climate-change\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2016-11-15T05:36:38Z\",\"webTitle\":\"Malcolm Turnbull must address the health risks of climate change | Michael Marmot\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2016/nov/15/malcolm-turnbull-must-address-the-health-risks-of-climate-change\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2016/nov/15/malcolm-turnbull-must-address-the-health-risks-of-climate-change\",\"fields\":{\"headline\":\"Malcolm Turnbull must address the health risks of climate change\",\"trailText\":\"The public health impacts of climate change are playing out in Australia while politicians ignore the evidence. Two reports out this week should change that<br>\",\"firstPublicationDate\":\"2016-11-15T05:36:38Z\",\"thumbnail\":\"https://media.guim.co.uk/bc21c0b6508e6f112aa3a85946f9a10783af45d2/0_161_4928_2959/500.jpg\"},\"tags\":[{\"id\":\"profile/michael-marmot\",\"type\":\"contributor\",\"webTitle\":\"Michael Marmot\",\"webUrl\":\"https://www.theguardian.com/profile/michael-marmot\",\"apiUrl\":\"https://content.guardianapis.com/profile/michael-marmot\",\"references\":[],\"bio\":\"<p>Michael Marmot is the immediate past-president of the World Medical Association and director of the University College of London's Institute of Health Equity. He is an international public health expert and former president of the British Medical Association.</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2010/8/13/1281715095775/marnot.jpg\",\"firstName\":\"michael\",\"lastName\":\"marmot\"}],\"isHosted\":false},{\"id\":\"commentisfree/2016/nov/14/doanld-trump-foreign-policy-vladimir-putin-nato-middle-east\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2016-11-14T12:59:17Z\",\"webTitle\":\"The greatest unknown yet: Donald Trump’s foreign policy | Jeremy Greenstock\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2016/nov/14/doanld-trump-foreign-policy-vladimir-putin-nato-middle-east\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2016/nov/14/doanld-trump-foreign-policy-vladimir-putin-nato-middle-east\",\"fields\":{\"headline\":\"The greatest unknown yet: Donald Trump’s foreign policy\",\"trailText\":\"Naivety over Vladimir Putin, scepticism on Nato, his stance on the Middle East – Trump is sowing uncertainty among governments around the world\",\"firstPublicationDate\":\"2016-11-14T12:59:17Z\",\"thumbnail\":\"https://media.guim.co.uk/8829c359b492a0c61d391aed59b3760d10d05c92/0_20_3500_2100/500.jpg\"},\"tags\":[{\"id\":\"profile/jeremy-greenstock\",\"type\":\"contributor\",\"webTitle\":\"Jeremy Greenstock\",\"webUrl\":\"https://www.theguardian.com/profile/jeremy-greenstock\",\"apiUrl\":\"https://content.guardianapis.com/profile/jeremy-greenstock\",\"references\":[],\"bio\":\"<p>Jeremy Greenstock, a former British ambassador to the UN, is chairman of Gatehouse Advisory Partners Ltd. He&nbsp;was a British diplomat from 1969-2004, serving in Washington DC, Paris, Dubai and Saudi Arabia. He was UK special envoy for Iraq and a former chair of the United Nations Association-UK. His book Iraq: the Cost of War is published by Heinemann this month</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2013/5/16/1368702552952/jeremy-greenstock.jpg\",\"firstName\":\"jeremy\",\"lastName\":\"greenstock\"}],\"isHosted\":false},{\"id\":\"us-news/live/2016/nov/14/barack-obama-donald-trump-press-conference-pragmatic\",\"type\":\"liveblog\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-11-14T22:59:18Z\",\"webTitle\":\"Obama calls Trump ‘pragmatic’\",\"webUrl\":\"https://www.theguardian.com/us-news/live/2016/nov/14/barack-obama-donald-trump-press-conference-pragmatic\",\"apiUrl\":\"https://content.guardianapis.com/us-news/live/2016/nov/14/barack-obama-donald-trump-press-conference-pragmatic\",\"fields\":{\"headline\":\"Obama calls Trump ‘pragmatic’\",\"trailText\":\"Got a minute? President speaks at length on Trump’s character … outcry over elevation of Breitbart impresario accused of bigotry … and everything else today in US politics. By Tom McCarthy\",\"firstPublicationDate\":\"2016-11-14T22:59:18Z\",\"thumbnail\":\"https://media.guim.co.uk/3a4240b8b6ad6e578fa36476b3879a833090781b/0_358_3630_2178/500.jpg\"},\"tags\":[],\"isHosted\":false},{\"id\":\"world/2016/nov/16/shorten-turnbull-labor-race-ethics-code-coalition\",\"type\":\"article\",\"sectionId\":\"world\",\"sectionName\":\"World news\",\"webPublicationDate\":\"2016-11-15T19:12:51Z\",\"webTitle\":\"Race ethics code: Shorten challenges Turnbull to put Labor proposal to Coalition MPs\",\"webUrl\":\"https://www.theguardian.com/world/2016/nov/16/shorten-turnbull-labor-race-ethics-code-coalition\",\"apiUrl\":\"https://content.guardianapis.com/world/2016/nov/16/shorten-turnbull-labor-race-ethics-code-coalition\",\"fields\":{\"headline\":\"Race ethics code: Shorten challenges Turnbull to put Labor proposal to Coalition MPs\",\"trailText\":\"<strong>Exclusive: </strong>Labor leader writes to PM, saying rise of One Nation and Donald Trump means MPs must commit to truthfulness and tolerance\",\"firstPublicationDate\":\"2016-11-15T19:12:51Z\",\"thumbnail\":\"https://media.guim.co.uk/ca144a6a52d5243d3720c05bc3085ec129f1d9b4/0_78_3000_1800/500.jpg\"},\"tags\":[{\"id\":\"profile/katharine-murphy\",\"type\":\"contributor\",\"webTitle\":\"Katharine Murphy\",\"webUrl\":\"https://www.theguardian.com/profile/katharine-murphy\",\"apiUrl\":\"https://content.guardianapis.com/profile/katharine-murphy\",\"references\":[],\"bio\":\"<p>Katharine Murphy is Guardian Australia's political editor. She has worked in Canberra's parliamentary gallery for 15 years. In 2008, she won the Paul Lyneham award for excellence in press gallery journalism, while in 2012 she was a Walkley award finalist in the best digital journalism category</p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/contributor/2014/7/22/1405984188542/Katharine-Murphy.jpg\",\"bylineLargeImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/contributor/2014/7/22/1405984230717/Katharine-Murphy-R.png\",\"firstName\":\"katharine\",\"lastName\":\"murphy\",\"twitterHandle\":\"murpharoo\"}],\"isHosted\":false},{\"id\":\"us-news/2016/nov/14/confusing-world-of-brexit-and-trump\",\"type\":\"article\",\"sectionId\":\"us-news\",\"sectionName\":\"US news\",\"webPublicationDate\":\"2016-11-14T19:35:23Z\",\"webTitle\":\"Confusing world of Brexit and Trump | Brief letters\",\"webUrl\":\"https://www.theguardian.com/us-news/2016/nov/14/confusing-world-of-brexit-and-trump\",\"apiUrl\":\"https://content.guardianapis.com/us-news/2016/nov/14/confusing-world-of-brexit-and-trump\",\"fields\":{\"headline\":\"Confusing world of Brexit and Trump\",\"trailText\":\"<strong>Brief letters:</strong> Liberalism and Trump | Brexit | Guarantees to Nissan | Global warming | Abel Gance’s Napoleon | Letters masterclass\",\"firstPublicationDate\":\"2016-11-14T19:35:23Z\",\"thumbnail\":\"https://media.guim.co.uk/7790430b3110d6e0bd0803110aa89555d82638a0/0_212_2840_1704/500.jpg\"},\"tags\":[],\"isHosted\":false},{\"id\":\"fashion/2016/nov/15/does-new-balance-really-support-trump-trainers-trade-plans\",\"type\":\"article\",\"sectionId\":\"fashion\",\"sectionName\":\"Fashion\",\"webPublicationDate\":\"2016-11-15T13:14:12Z\",\"webTitle\":\"Does New Balance really support Trump?\",\"webUrl\":\"https://www.theguardian.com/fashion/2016/nov/15/does-new-balance-really-support-trump-trainers-trade-plans\",\"apiUrl\":\"https://content.guardianapis.com/fashion/2016/nov/15/does-new-balance-really-support-trump-trainers-trade-plans\",\"fields\":{\"headline\":\"Does New Balance really support Trump?\",\"trailText\":\"Last week, Democrats took to social media to burn their New Balance trainers after the company appeared to praise Donald Trump’s trade plans. But what did the company really mean – and should you be burning your kicks?\",\"firstPublicationDate\":\"2016-11-15T13:14:12Z\",\"thumbnail\":\"https://media.guim.co.uk/e7365ef504120ce38b6fcb3ef0eb4cf8f3fafb13/0_0_5184_3110/500.jpg\"},\"tags\":[{\"id\":\"profile/hannah-jane-parkinson\",\"type\":\"contributor\",\"webTitle\":\"Hannah Jane Parkinson\",\"webUrl\":\"https://www.theguardian.com/profile/hannah-jane-parkinson\",\"apiUrl\":\"https://content.guardianapis.com/profile/hannah-jane-parkinson\",\"references\":[],\"bio\":\"<p>Hannah Jane Parkinson is a writer on pop culture, music, tech, football, politics and mental health. She lives in London and previously lived in Russia, Oxford and Liverpool. She likes reading, sauvignon blanc, laughing and Liverpool FC. Twitter: <a href=\\\"http://www.twitter.com/ladyhaja\\\">@ladyhaja</a></p>\",\"bylineImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/contributor/2015/1/23/1422019537833/Hannah-Jane-Parkinson.jpg\",\"bylineLargeImageUrl\":\"https://static.guim.co.uk/sys-images/Guardian/Pix/contributor/2015/1/23/1422019551507/Hannah-Jane-Parkinson-L.png\",\"firstName\":\"janeparkinson\",\"lastName\":\"hannah\",\"twitterHandle\":\"ladyhaja\"}],\"isHosted\":false}]}}";
    */

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
        }catch(JSONException e) {
            Log.e(TAG, "Error trying to parse the JSON response");
                e.printStackTrace();
            }


        return newsArticles;
    }
}
