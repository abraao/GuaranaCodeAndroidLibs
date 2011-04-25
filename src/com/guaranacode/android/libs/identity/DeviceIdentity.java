package com.guaranacode.android.libs.identity;

import android.content.Context;
import android.provider.Settings;

/**
 * Code to help identify a specific Android device.
 *
 * @author abraao@guaranacode.com
 */
public class DeviceIdentity {
    private Context mApplicationContext;
    
    public DeviceIdentity(Context applicationContext) {
        mApplicationContext = applicationContext;
    }
    
    /**
     * Gets the android id associated with the device.
     * 
     * @see <a href="http://android-developers.blogspot.com/2011/03/identifying-app-installations.html">Tim Bray's post on identifying app installations</a>
     * for more information on why the return value of this method may be unreliable.
     * @return
     */
    public String getAndroidId() {
        return Settings.Secure.getString(mApplicationContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
