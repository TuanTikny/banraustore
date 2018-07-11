package com.tuanbapk.banrau.View.Flagment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tuanbapk.banrau.Model.Tygia;
import com.tuanbapk.banrau.R;
import com.tuanbapk.banrau.View.Adapter.AdapterTygia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by buituan on 2017-12-16.
 */

public class FragmentTygia extends Fragment {

    ListView lvTiGia;
    ArrayList<Tygia> dsTiGia;
    AdapterTygia tiGiaAdapter;
//    private AdRequest adRequest;

    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tygia,container,false);

        // show quang cao banner
//        AdView adView = (AdView) rootView.findViewById(R.id.adView);
//        adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);

        mapped();
        return rootView;
    }

    private void mapped() {

        lvTiGia= (ListView) rootView.findViewById(R.id.lvTiGia);
        dsTiGia=new ArrayList<>();
        tiGiaAdapter=new AdapterTygia(rootView.getContext(),R.layout.item_tygia,dsTiGia);
        lvTiGia.setAdapter(tiGiaAdapter);

        TiGiaTask task=new TiGiaTask();
        task.execute();
    }


    class TiGiaTask extends AsyncTask<Void,Void,ArrayList<Tygia>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tiGiaAdapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<Tygia> tiGias) {
            super.onPostExecute(tiGias);
            tiGiaAdapter.clear();
            tiGiaAdapter.addAll(tiGias);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected ArrayList<Tygia> doInBackground(Void... params) {
            ArrayList<Tygia> ds=new ArrayList<>();
            try
            {
                URL url=new URL("http://dongabank.com.vn/exchange/export");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                connection.setRequestProperty("Accept", "*/*");

                InputStream is= connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is,"UTF-8");
                BufferedReader br=new BufferedReader(isr);
                String line=br.readLine();
                StringBuilder builder=new StringBuilder();

                while (line!=null)
                {
                    builder.append(line);
                    line=br.readLine();
                }

                String json=builder.toString();
                json=json.replace("(", "");
                json=json.replace(")","");

                JSONObject jsonObject=new JSONObject(json);
                JSONArray jsonArray= jsonObject.getJSONArray("items");
                for (int i=0;i<jsonArray.length();i++)
                {
                    JSONObject item=jsonArray.getJSONObject(i);
                    Tygia tiGia=new Tygia();
                    tiGia.setType(item.getString("type"));

                    if(item.has("imageurl")) {
                        tiGia.setImageurl(item.getString("imageurl"));
                        url=new URL(tiGia.getImageurl());
                        connection= (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                        connection.setRequestProperty("Accept", "*/*");
                        Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                        tiGia.setBitmap(bitmap);
                    }

                    tiGia.setMuatienmat(item.getString("muatienmat"));
                    tiGia.setMuack(item.getString("muack"));
                    tiGia.setBantienmat(item.getString("bantienmat"));
                    tiGia.setBanck(item.getString("banck"));
                    ds.add(tiGia);
                }
            }
            catch (Exception ex)
            {
                Log.e("LOI",ex.toString());
            }
            return ds;
        }
    }
}
