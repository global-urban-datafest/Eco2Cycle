package com.e2g.ecocicle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.e2g.ecocicle.Model.Pontodetroca;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Mapa extends ActionBarActivity implements SlidingPaneLayout.PanelSlideListener{

    private SlidingPaneLayout mSlidingLayout;
    private ListView mList;
    private ProgressDialog progressDialog;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setUpMapIfNeeded();

//        progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Carregando os pontos de troca!");
//        progressDialog.show();

        //Menu dos icones..
        //=====================================================
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("EcoCycle");
//        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayShowCustomEnabled(true);
        //=====================================================

        mSlidingLayout = (SlidingPaneLayout)
                findViewById(R.id.sliding_pane_layout);
        mSlidingLayout.setPanelSlideListener(this);

        String[] opcoes = null;
        if(((Main)getApplication()).isLogado()){
            opcoes = new String[] {
                    "Profile", "Filter", "QR Read",
                    "My EcoCoins", "EcoExchange" };
        }else{
            opcoes = new String[] {"Login", "Filter"};
        }


        mList = (ListView) findViewById(R.id.left_pane);
        mList.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                opcoes));
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecionaItem(position);
            }
        });
    }

    private void selecionaItem(int posicao){
        Intent myIntent;
        switch (posicao){
            case 0:
                Log.i("Clicado"," 1");
                if (((Main)getApplication()).isLogado()){
                    myIntent = new Intent(getApplicationContext(), Perfil.class);
                    startActivity(myIntent);
                }else{
                    myIntent = new Intent(getApplicationContext(), Login.class);
                    startActivity(myIntent);
                }
                break;
            case 1:
                Log.i("Clicado"," 2");
                myIntent = new Intent(getApplicationContext(), Filter.class);
                startActivity(myIntent);
                break;
            case 2:
                Log.i("Clicado"," 3");
                myIntent = new Intent(getApplicationContext(), LeitorQr.class);
                startActivity(myIntent);
                break;
            case 3:
                Log.i("Clicado"," 4");
                break;
            case 4:
                Log.i("Clicado"," 5");
                break;
            default:
                Log.i("Clicado","OOUTRA COISA");
                break;
        }
    }

    // Evento de clique do botão
    public void abrirMenu(View v){
        // Se estive aberto, feche. Senão abra.
        if (mSlidingLayout.isOpen()){
            mSlidingLayout.closePane();
        } else {
            mSlidingLayout.openPane();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mapa, menu);
        return true;
    }
//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.i("Clickado ", "testando: " + id);
        Intent myIntent;
        switch (id){
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
//        Double latitude = -23.5838238;
//        Double longetude = -46.6524742;
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            mMap.setMyLocationEnabled(true);

//            // Getting LocationManager object from System Service LOCATION_SERVICE
//            LocationManager locationManager =
//                    (LocationManager) getSystemService(LOCATION_SERVICE);
//
//            // Creating a criteria object to retrieve provider
//            Criteria criteria = new Criteria();
//
//            // Getting the name of the best provider
//            String provider = locationManager.getBestProvider(criteria, true);
//
//            // Getting Current Location
//            Location location = locationManager.getLastKnownLocation(provider);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                new RecuperaPontos().execute();
            }
        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelOpened(View panel) {

    }

    @Override
    public void onPanelClosed(View panel) {

    }

    class RecuperaPontos extends AsyncTask<Void, Void, ArrayList<Pontodetroca>>{

        @Override
        protected ArrayList<Pontodetroca> doInBackground(Void... params) {
            String url = "http://ecociclews.mybluemix.net/api/pontodetroca/0";
            //Log.i("URL", " " + url);
            String[] resposta = new WebServiceCliente().get(url);
            ArrayList<Pontodetroca> pontos = null;
            Log.i("resp "," >>" + resposta[0]);
            if (resposta[0].equals("200")) {
                Gson gson = new Gson();
                Type collType = new TypeToken<ArrayList<Pontodetroca>>() {}.getType();
                pontos = gson.fromJson(resposta[1], collType);
            }
            return pontos;
        }

        @Override
        protected void onPostExecute(ArrayList<Pontodetroca> pontos) {
            for(Pontodetroca p : pontos){
                MarkerOptions markerPoint = (new MarkerOptions()
                        .position(new LatLng(p.getLatitude(), p.getLongitude()))
                        .title(p.getDescricao()));
                switch (p.getTipo()){
                    case "0":
                        markerPoint.icon(BitmapDescriptorFactory.fromResource(R.drawable.iconmapsmal));
                        break;
                    case "1":
                        markerPoint.icon(BitmapDescriptorFactory.fromResource(R.drawable.icontroca));
                        break;
                    case "2":
                        markerPoint.icon(BitmapDescriptorFactory.fromResource(R.drawable.icontwoitens));
                        break;
                }
                mMap.addMarker(markerPoint);
            }
        }
    }
}


