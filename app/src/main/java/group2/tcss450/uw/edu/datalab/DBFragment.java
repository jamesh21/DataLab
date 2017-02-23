package group2.tcss450.uw.edu.datalab;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import group2.tcss450.uw.edu.datalab.data.ColorEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class DBFragment extends Fragment {

    private LinearLayout mLayout;
    private Menu mMenu;
    private List<TextView> mTextViewList;
    public DBFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
        mMenu = menu;
        mMenu.findItem(R.id.trash_menu_item).setVisible(false);
        mMenu.findItem(R.id.trash_menu_item_db).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trash_menu_item_db:
                System.out.println("HELLO");
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are you sure you would like to delete this file?");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        clearFileContents();
                        removeTextView();
                        mMenu.findItem(R.id.trash_menu_item_db).setVisible(false);
                    }
                });

                alertDialog.show();
                return true;
            case R.id.trash_menu_item:
                return false;

            case R.id.file_menu_item:
                return false;

            case R.id.db_menu_item:
                return false;
            default:
                break;
        }

        return false;
    }

    private void removeTextView() {
        for (int i = 0; i < mTextViewList.size(); i++) {
            mTextViewList.get(i).setText("");
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            ArrayList<ColorEntry> colors =
                    (ArrayList<ColorEntry>) getArguments().getSerializable(
                            getString(R.string.DB_NAME));
            mTextViewList = new ArrayList<>();
            for (ColorEntry c : colors) {
                TextView tv = new TextView(getContext());
                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis(c.getTimeInMillies());
                tv.setText(
                        new SimpleDateFormat(getString(R.string.date_format)).
                                format(cal.getTime())
                                + "\n\t" + colorToString(c.getColor()));
                mTextViewList.add(tv);
                mLayout.addView(tv);
            }
        }
    }

    private String colorToString(int color) {
        return " r:" +
                Color.red(color) + ", g:" +
                Color.green(color) + ", b:" +
                Color.blue(color) + ", a:" +
                Color.alpha(color);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //return inflater.inflate(R.layout.fragment_db, container, false);
        mLayout = (LinearLayout) inflater.inflate(R.layout.fragment_db, container, false);
        return mLayout;
    }

}
