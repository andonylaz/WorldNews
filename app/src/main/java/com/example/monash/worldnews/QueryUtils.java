package com.example.monash.worldnews;

/**
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

    public static final String FAKE_JSON_RESPONSE = "";
}
