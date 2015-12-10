package com.salesianostriana.psp.contactsretrofit;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends Activity {

    ListView l;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l = (ListView) findViewById(R.id.listView);

        new GetData().execute();

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetData extends AsyncTask<Void, Void, Contacts> {

        @Override
        protected Contacts doInBackground(Void... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(new Gson()))
                    .baseUrl("http://api.androidhive.info")
                    .build();

            AndroidHiveContactAPI service =
                    retrofit.create(AndroidHiveContactAPI.class);

            Call<Contacts> contacts = service.listContacts();

            Response<Contacts> result = null;

            try {
                result = contacts.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (result != null) {
                return result.body();
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Contacts contacts) {
            //super.onPostExecute(contacts);
            if (contacts != null) {
                l.setAdapter(new ContactAdapter(MainActivity.this, contacts.getContacts()));
            }
        }
    }


    private class ContactAdapter extends BaseAdapter {

        Context mContext;
        List<Contact> data;

        public ContactAdapter(Context _context, List<Contact> _data) {
            mContext = _context;
            data = _data;
        }



        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

            TextView name = (TextView) row.findViewById(R.id.name);
            TextView email  = (TextView) row.findViewById(R.id.email);
            TextView mobile = (TextView) row.findViewById(R.id.mobile);

            Contact item = (Contact) getItem(position);

            name.setText(item.getName());
            email.setText(item.getEmail());
            mobile.setText(mobile.getText()+ " " + item.getPhone().getMobile());

            return row;

        }
    }


}
