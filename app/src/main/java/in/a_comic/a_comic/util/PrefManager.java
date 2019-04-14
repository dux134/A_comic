package in.a_comic.a_comic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by root on 1/19/18.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "key notes";
    //PreferenceManager.getDefaultSharedPreferences()

    private final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private final String RATE_US_LINK = "RateUsLink";
    private final String SHARE_LINK = "ShareLink";
    private final String ADMOB_ID = "id";
    private final String ADMOB_ID_DUX = "dux_id";

    public PrefManager(Context context) {
        this._context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(_context);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void setAdmobId(String id) {
        editor.putString(ADMOB_ID,id);
        editor.commit();
    }
    public void setAdmobIdDux(String id) {
        editor.putString(ADMOB_ID_DUX,id);
        editor.commit();
    }

    public void setRATE_US_LINK(String id) {
        editor.putString(RATE_US_LINK,id);
        editor.commit();
    }
    public void setSHARE_LINK(String id) {
        editor.putString(SHARE_LINK,id);
        editor.commit();
    }

    public String getAdmobId() {
        return pref.getString(ADMOB_ID,"id");
    }
    public String getAdmobIdDux() {
        return pref.getString(ADMOB_ID_DUX,"id");
    }

    public String getRATE_US_LINK() {
        return pref.getString(RATE_US_LINK,"id");
    }
    public String getSHARE_LINK() {
        return pref.getString(SHARE_LINK,"id");
    }

}