package xyz.u999.creations.numer;

import com.google.android.gms.ads.AdRequest;

/**
 * Created by umang on 1/7/16.
 */
public class BannerAd {

    public static AdRequest getBannerAd() {

        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("A34110A923EBD2913ADCF85066EE637A")
                .build();

        // return new AdRequest.Builder().build();
    }

}
