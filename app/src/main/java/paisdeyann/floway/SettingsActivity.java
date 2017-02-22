package paisdeyann.floway;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

    final String PREFS_FILE = "com.paisdeyann.floway.sharedpreferences.preferences";
    SharedPreferences.Editor mEditor ;
    SharedPreferences mSharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



    }
}