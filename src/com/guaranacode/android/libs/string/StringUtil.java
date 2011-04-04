package com.guaranacode.android.libs.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;

public final class StringUtil {
    public static final String EMPTY = "";
    
    public static boolean isNullOrEmpty(String string) {
        return (null == string) || (0 == string.trim().length());
    }

    /**
     * Read the whole stream into a string. The connection is not closed by this method.
     * @param stream
     * @return
     */
    public static String fromStream(InputStream stream) {
        if(null == stream) {
            return null;
        }
        
        final StringBuilder sb = new StringBuilder();
        final BufferedReader br = new BufferedReader(new InputStreamReader(stream), 256);
        String line;
        
        while(true) {
            try {
                line = br.readLine();
            } catch(IOException e) {
                Log.e("StringUtil", "Error reading stream in HTTP get", e);
                break;
            }
            
            if(null == line) {
                break;
            }
            
            sb.append(line);
        }

        return sb.toString();
    }
}
