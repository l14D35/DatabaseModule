package com.example.databasemodule.Views.emulator;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;
import androidx.fragment.app.FragmentTransaction;
import com.example.databasemodule.Bluetooth.bluetoothfragment.ListFragment;
import com.example.databasemodule.Bluetooth.common.activities.SampleActivityBase;
import com.example.databasemodule.Bluetooth.common.logger.Log;
import com.example.databasemodule.Bluetooth.common.logger.LogFragment;
import com.example.databasemodule.Bluetooth.common.logger.LogWrapper;
import com.example.databasemodule.Bluetooth.common.logger.MessageOnlyLogFilter;
import com.example.databasemodule.R;

public class ListActivity extends SampleActivityBase {

    public static final String TAG = "ListActivity";

    private boolean mLogShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emulator);

        if (savedInstanceState == null) {
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            final ListFragment fragment = new ListFragment();
            transaction.replace(R.id.list_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.list_log_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = findViewById(R.id.list_log_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initializeLogging() {
        final LogWrapper logWrapper = new LogWrapper();

        Log.setLogNode(logWrapper);

        final MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        final LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.list_log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }
}