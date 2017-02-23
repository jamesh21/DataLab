package group2.tcss450.uw.edu.datalab;


import android.content.DialogInterface;
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FileFragment extends Fragment {
    private Menu mMenu;
    List<TextView> mTextViewList;

    public FileFragment() {
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
        mMenu.findItem(R.id.trash_menu_item_db).setVisible(false);
        mMenu.findItem(R.id.trash_menu_item).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trash_menu_item_db:
                // Not implemented here
                return false;
            case R.id.trash_menu_item:
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
                        clearFileContents();
                        deletingTextViews();
                        mMenu.findItem(R.id.trash_menu_item).setVisible(false);
                    }
                });

                alertDialog.show();
                return true;
            case R.id.file_menu_item:
                return false;
            case R.id.db_menu_item:
                return false;
            default:
                break;
        }

        return false;
    }

    private void clearFileContents() {
        System.out.println("checking if it went in here");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(getActivity().openFileOutput(getString(R.string.COLOR_SELECTIONS), 0));
            outputStreamWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deletingTextViews() {
        for(int i = 0; i < mTextViewList.size(); i++) {
            mTextViewList.get(i).setText("");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = (LinearLayout) inflater.inflate(
                R.layout.fragment_file, container, false);
        mTextViewList = new ArrayList<>();
        //getActivity().findViewById(R.id.trash_menu_item);
        try {
            InputStream inputStream = getActivity().openFileInput(
                    getString(R.string.COLOR_SELECTIONS));
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    TextView tv = new TextView(getActivity());
                    mTextViewList.add(tv);
                    tv.setText(receiveString);
                    view.addView(tv);
                }
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

}
