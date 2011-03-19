package com.guaranacode.android.libs.google.translate;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.guaranacode.android.libs.http.HttpClient;
import com.guaranacode.android.libs.string.StringUtil;

/**
 * Interface for Google Translate API. See
 * http://code.google.com/apis/language/translate/v2/using_rest.html
 * 
 * @author abraao@guaranacode.com
 */
public class LanguageTranslator {
    /*
     * The URL to use when contacting the Google Translate REST API. The source text querystring pair must be appended
     * to the end of the URL.
     * 
     * 1 - API key
     * 2 - Source language's code
     * 3 - Target language's code
     */
    private static final String API_URL_FORMAT = "https://www.googleapis.com/language/translate/v2?format=text&key=%s&source=%s&target=%s";

    /**
     * The API key used with Google's services.
     */
    private String mApiKey;
    
    public LanguageTranslator(String apiKey) {
        mApiKey = apiKey;
    }

    /**
     * Translate the source text from the source language to the target language.
     * @param sourceLanguage
     * @param targetLanguage
     * @param sourceText
     * @return
     */
    public String translate(Language sourceLanguage, Language targetLanguage, String sourceText) {
        if(StringUtil.isNullOrEmpty(sourceText)) {
            return null;
        }
        
        final Map<String, String> paramMap = new HashMap<String, String>(1);
        paramMap.put("q", sourceText);
        
        final String output = HttpClient.get(getApiUrl(mApiKey, sourceLanguage, targetLanguage), paramMap, null);

        // TODO: Handle error in output.
        if (StringUtil.isNullOrEmpty(output)) {
            return null;
        }

        try {
            final JSONObject jsonResponse = new JSONObject(output);
            Log.d("LanguageTranslator", "JSON response " + jsonResponse);
            
            final JSONArray translations = jsonResponse.getJSONObject("data").getJSONArray("translations");
            final String translatedText = translations.getJSONObject(0).getString("translatedText");
            
            return HttpClient.decodeText(translatedText);
        } catch (JSONException e) {
            Log.e("LanguageTranslator",
                    String.format(
                        "%s->%s, error parsing JSON: %s",
                        sourceLanguage.getLanguageCode(),
                        targetLanguage.getLanguageCode(), output),
                    e);
            
            return null;
        }
    }

    /**
     * Returns a properly formatted URL to contact Google Translate.
     * 
     * @param apiKey
     * @param sourceLanguage
     * @param targetLanguage
     * @return
     */
    private String getApiUrl(String apiKey, Language sourceLanguage, Language targetLanguage) {
        return String.format(API_URL_FORMAT, mApiKey, sourceLanguage.getLanguageCode(), targetLanguage.getLanguageCode());
    }
}
