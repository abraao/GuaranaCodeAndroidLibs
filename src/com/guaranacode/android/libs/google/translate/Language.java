package com.guaranacode.android.libs.google.translate;

/**
 * Languages available from Google Translate.
 * See http://code.google.com/apis/language/translate/v2/using_rest.html#language-params
 *
 * @author abraao@guaranacode.com
 */
public enum Language {
    Afrikaans("af"),
    Albanian("sq"),
    Arabic("ar"),
    Belarusian("be"),
    Bulgarian("bg"),
    Catalan("ca"),
    ChineseSimplified("zh-CN"),
    ChineseTraditional("zh-TW"),
    Croatian("hr"),
    Czech("cs"),
    Danish("da"),
    Dutch("nl"),
    English("en"),
    Estonian("et"),
    Filipino("tl"),
    Finnish("fi"),
    French("fr"),
    Galician("gl"),
    German("de"),
    Greek("el"),
    HaitianCreole("ht"),
    Hebrew("iw"),
    Hindi("hi"),
    Hungarian("hu"),
    Icelandic("is"),
    Indonesian("id"),
    Irish("ga"),
    Italian("it"),
    Japanese("ja"),
    Latvian("lv"),
    Lithuanian("lt"),
    Macedonian("mk"),
    Malay("ms"),
    Maltese("mt"),
    Norwegian("no"),
    Persian("fa"),
    Polish("pl"),
    Portuguese("pt"),
    Romanian("ro"),
    Russian("ru"),
    Serbian("sr"),
    Slovak("sk"),
    Slovenian("sl"),
    Spanish("es"),
    Swahili("sw"),
    Swedish("sv"),
    Thai("th"),
    Turkish("tr"),
    Ukrainian("uk"),
    Vietnamese("vi"),
    Welsh("cy"),
    Yiddish("yi");
    
    private final String mLanguageCode;
    
    private Language(String languageCode) {
        mLanguageCode = languageCode;
    }
    
    public String getLanguageCode() {
        return mLanguageCode;
    }
}
