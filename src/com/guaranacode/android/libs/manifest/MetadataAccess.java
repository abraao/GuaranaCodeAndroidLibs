package com.guaranacode.android.libs.manifest;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

/**
 * API for working with metadata present in an application's 
 *
 * @author abraao@guaranacode.com
 */
public class MetadataAccess {
    
    /**
     * Returns the string value associated with the metadata with the given name, or null on error.
     * @param applicationContext
     * @return
     */
    public static String getString(Context applicationContext, String metadataName) {
        ApplicationInfo ai;
        
        try {
            ai = applicationContext.getPackageManager().getApplicationInfo(applicationContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            Log.e("Metadata", "Error getting metadata with name " + metadataName, e);
            
            return null;
        }
        
        Bundle bundle = ai.metaData;
         
        return bundle.getString(metadataName);
    }
}
