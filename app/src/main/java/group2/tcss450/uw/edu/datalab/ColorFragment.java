package group2.tcss450.uw.edu.datalab;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColorFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ColorFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private OnFragmentInteractionListener mListener;
    private Spinner mSpinner;
    public ColorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            int position = getArguments().getInt(getString(R.string.POSITION));
            System.out.println("!!!!!!!!!! Fragment" + position);
            mSpinner.setSelection(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_color, container, false);
        final String[] COLORS = getResources().getStringArray(R.array.auto_complete_colors);
        mSpinner = (Spinner) view.findViewById(R.id.spinner);
        ArrayAdapter<String> colorAdapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_dropdown_item_1line, COLORS);
       // colorAdapter.setDropDownViewResource(android);
        mSpinner.setAdapter(colorAdapter);
        mSpinner.setOnItemSelectedListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int color, int position) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(color, position);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mListener != null) {
            String color = (String)parent.getAdapter().getItem(position);
            int colorInt = Color.parseColor(color);
            mListener.onFragmentInteraction(colorInt, position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int color, int position);
    }
}
