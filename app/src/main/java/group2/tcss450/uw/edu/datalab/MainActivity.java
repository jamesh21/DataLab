package group2.tcss450.uw.edu.datalab;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.OutputStreamWriter;
import java.io.Serializable;

import group2.tcss450.uw.edu.datalab.data.ColorDB;

public class MainActivity extends AppCompatActivity implements ColorFragment.OnFragmentInteractionListener {

    private ColorFragment mColorFragment;
    private SharedPreferences mPrefs;
    private ColorDB mColorDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPrefs = getSharedPreferences(getString(R.string.SHARED_PREFS), Context.MODE_PRIVATE);
        findViewById(R.id.content_main).
                setBackgroundColor(mPrefs.getInt(getString(R.string.COLOR), Color.RED));
        int pos = mPrefs.getInt(getString(R.string.POSITION), 0);
        if (savedInstanceState == null) {
            if (findViewById(R.id.content_main) != null) {
                System.out.println("HEOOLFDSF!!!!!!!!!!!!!!!");
                mColorFragment = new ColorFragment();
                if (pos != 0) {
                    System.out.println("!!!!!!!!!!!!!!!HEOOLFDSF");
                    Bundle args = new Bundle();
                    System.out.println("!!!!!!!!!!!!!!! In here" + pos);
                    args.putInt(getString(R.string.POSITION), pos);
                    mColorFragment.setArguments(args);
                }

                //mColorFragment = new ColorFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.content_main, mColorFragment)
                        .commit();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.file_menu_item) {
            mColorFragment.getArguments().putInt(getString(R.string.POSITION),
                    mPrefs.getInt(getString(R.string.POSITION), 0));

            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, new FileFragment())
                    .addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        } else if (id == R.id.db_menu_item) {
            if (mColorDB == null) {
                mColorDB = new ColorDB(this);
            }
            mColorFragment.getArguments().putInt(getString(R.string.POSITION),
                    mPrefs.getInt(getString(R.string.POSITION), 0));
            DBFragment dbf = new DBFragment();
            Bundle args = new Bundle();
            args.putSerializable(getString(R.string.DB_NAME),
                    (Serializable) mColorDB.getColors());
            dbf.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, dbf)
                    .addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveToSharedPrefs(int color, int pos) {
        mPrefs.edit().putInt(getString(R.string.COLOR), color).apply();
        mPrefs.edit().putInt(getString(R.string.POSITION), pos).apply();
    }

    private void saveToFile(int color, int pos) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    openFileOutput(getString(R.string.COLOR_SELECTIONS)
                            , Context.MODE_APPEND));
            outputStreamWriter.append("color = r:" );
            outputStreamWriter.append(Color.red(color) + ", g:");
            outputStreamWriter.append(Color.green(color) + ", b:");
            outputStreamWriter.append(Color.blue(color) + ", a:");
            outputStreamWriter.append(Color.alpha(color) + "\n");
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToSqlite(int color) {
        if (mColorDB == null) {
            mColorDB = new ColorDB(this);
        }
        mColorDB.insertColor(System.currentTimeMillis(), color);
    }

    @Override
    public void onFragmentInteraction(int color, int position) {
        //mColorFragment.getView().setBackgroundColor(color);
        //findViewById(R.id.testing).setBackgroundColor(color);
        findViewById(R.id.content_main).setBackgroundColor(color);
        saveToSharedPrefs(color, position);
        saveToFile(color, position);
        saveToSqlite(color);
//        GradientDrawable bg = (GradientDrawable) findViewById(R.id.color_fragment).getBackground();
//        bg.setColor(color);
    }
}
