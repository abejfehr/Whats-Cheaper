package com.abefehr.whatscheaper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.gms.ads.*;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private ArrayList<Item> itemList = new ArrayList<Item>();

        private EditText priceText, numUnitsText;
        private Button addButton, clearButton;
        private ListView listView;
        private TextView emptyListText;

        private CrazyAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            priceText = (EditText)rootView.findViewById(R.id.priceText);
            numUnitsText = (EditText)rootView.findViewById(R.id.numUnitsText);

            addButton = (Button)rootView.findViewById(R.id.addButton);
            clearButton = (Button)rootView.findViewById(R.id.clearButton);

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String pString = priceText.getText().toString();
                    String nString = numUnitsText.getText().toString();

                    double price, numUnits;

                    try { price = Double.parseDouble(pString); }
                    catch(Exception ex) {
                        (Toast.makeText(getActivity(), getResources().getString(R.string.blank_price_error), Toast.LENGTH_SHORT)).show();
                        return;
                    }

                    try { numUnits = Double.parseDouble(nString); }
                    catch(Exception ex) {
                        (Toast.makeText(getActivity(), getResources().getString(R.string.blank_units_error), Toast.LENGTH_SHORT)).show();
                        return;
                    }

                    Item item = new Item(price, numUnits);
                    addItemToList(item);

                    priceText.setText("");
                    numUnitsText.setText("");

                    update();
                }
            });

            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemList.clear();
                    priceText.setText("");
                    numUnitsText.setText("");

                    (Toast.makeText(getActivity(), getResources().getString(R.string.successfully_cleared_list), Toast.LENGTH_SHORT)).show();

                    update();
                }
            });

            adapter = new CrazyAdapter(getActivity(), itemList);
            listView = (ListView) rootView.findViewById(R.id.listView);
            listView.setAdapter(adapter);

            emptyListText = (TextView) rootView.findViewById(R.id.emptyListTextView);

            AdView adView = (AdView)rootView.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            return rootView;
        }

        /*
         * Function: addItemToList
         * Input: Item i - the item that we'll be adding to the list
         * Purpose: adds an item(i) to the list
         */
        private void addItemToList(Item i) {
            adapter.add(i);
            priceText.requestFocus();
        }


        /*
         * Function: update
         * Purpose: Updates the user interface
         */
        private void update() {
            if(itemList.size() > 0)
                emptyListText.setVisibility(View.INVISIBLE);
            else
                emptyListText.setVisibility(View.VISIBLE);
        }
    }
}
