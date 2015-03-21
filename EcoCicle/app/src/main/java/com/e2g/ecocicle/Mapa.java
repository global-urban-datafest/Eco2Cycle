package com.e2g.ecocicle;

//import android.app.FragmentManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.e2g.ecocicle.Model.PontoTroca;
import com.e2g.ecocicle.Model.ProductPoint;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Mapa extends ActionBarActivity{
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mPlanetTitles;

    private ActionBarDrawerToggle mDrawerToggle;

    private SharedPreferences prefMain;
    private SharedPreferences.Editor editor;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setUpMapIfNeeded();

        prefMain = getSharedPreferences(LoginFragment.URL_PREF, Context.MODE_PRIVATE);
        String login = prefMain.getString("login", "");

        //itens do menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.backgroudAPPDOIS)));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.back_spash));
        getSupportActionBar().setTitle("Eco 2 Cycle");

        //MENU LATERAL
        if (logado()){
            mPlanetTitles = getResources().getStringArray(R.array.options_array);
            mPlanetTitles[1] = ((Main)getApplication()).getUsuarioNaApp().getName().toString();
        }else{
            mPlanetTitles = getResources().getStringArray(R.array.options_array_no_user);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //Efeito lateral
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mPlanetTitles));

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecionaItem(position);
            }
        });

        if (savedInstanceState == null){
            selecionaItem(0);
        }
    }

    public void changeMenuLateral(String name){
        //MENU LATERAL
        if (logado()){
            mPlanetTitles = getResources().getStringArray(R.array.options_array);
        }else{
            mPlanetTitles = getResources().getStringArray(R.array.options_array_no_user);
        }
        if(!name.equals("")){
            mPlanetTitles[1] = name;
        }
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mPlanetTitles));

        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selecionaItem(position);
            }
        });

        selecionaItem(0);
    }

    public void abreFechaMenu(){
        if (mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            mDrawerLayout.openDrawer(mDrawerList);
        }
    }
    public void selecionaItem(int posicao){
        //update no menu..
        mDrawerList.setItemChecked(posicao, true);
        mDrawerLayout.closeDrawer(mDrawerList);

        //Fragmentos
        Fragment fragment = new Fragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        String s;
        if(!logado()){
            s = getResources().getStringArray(R.array.options_array_no_user)[posicao];
        }else{
            s = getResources().getStringArray(R.array.options_array)[posicao];
        }
        switch (s){
            case "Map":
                break;
            case "Login":
                if (logado()){
                    fragment = new PerfilFragment();
                }else{
                    fragment = new LoginFragment();
                }
                break;
            case "Filter":
                fragment = new FilterFragment();
                break;
            case "QR Read":
                if (logado()){
                    fragment = new LeitorQrFragment();
                }else{
                    exibeAlerta();
                }
                break;
            case "My EcoCoins":
                if(logado()){
                    fragment = new MyEcoCoinsFragment();
                }else{
                    exibeAlerta();
                }
                break;
            case "EcoCoins Store":
                if (logado()){
                    fragment = new EcoEnchangeFragment();
                }else{
                    exibeAlerta();
                }
                break;
            case "Simulation":
                fragment = new SimulationFragment();
                break;
            case "Logout":
                AlertDialog.Builder alerta = new AlertDialog.Builder(this);
                alerta.setTitle("Logout!");
                alerta.setMessage("Are you sure?");
                alerta.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                ((Main)getApplication()).setUsuarioNaApp(null);
                                editor = prefMain.edit();
                                editor.clear().commit();
                                changeMenuLateral("");
                                dialog.dismiss();
                            }
                        });
                alerta.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alerta.show();
                break;
            default:
                if (logado()){
                    fragment = new PerfilFragment();
                }
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private boolean logado(){
        if (((Main)getApplication()).getUsuarioNaApp() != null){
            return true;
        }else {
            return false;
        }
    }

    private void exibeAlerta(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("LOGIN!");
        alerta.setMessage("PRIMEIRO O LOGIN!");
        alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alerta.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Click do menu
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
//            case R.id.action_settings:
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame))
                    .getMap();
            mMap.setMyLocationEnabled(true);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                new RecuperaPontos().execute();
                Location minhaLoc = getMyLocation();
                if (minhaLoc != null){
                    LatLng currentCoordinates = new LatLng(
                            minhaLoc.getLatitude(),
                            minhaLoc.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 9));
                }else{
                    Log.i("MAP", "pegou o else");
                    Toast.makeText(this, "Activate the GPS to show your position!", Toast.LENGTH_LONG).show();
                    LatLng currentCoordinates = new LatLng(
                            -23.5724765,
                            -46.6493565);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 7));
                }
            }
        }
    }

    private Location getMyLocation() {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }
        return myLocation;
    }

    class RecuperaPontos extends AsyncTask<Void, Void, ArrayList<PontoTroca>>{
        @Override
        protected ArrayList<PontoTroca> doInBackground(Void... params) {
            ArrayList<PontoTroca> pontos = null;
            try{
                String url = "http://ecocicle.mybluemix.net/api/point/0";
                String[] resposta = new WebServiceCliente().get(url);
                if (resposta[0].equals("200")) {
                    Gson gson = new Gson();
                    Type collType = new TypeToken<ArrayList<PontoTroca>>() {}.getType();
                    pontos = gson.fromJson(resposta[1], collType);
                }else if (resposta[0].equals("404")){
                    //Erro de servidor nao respondendo;
                    Log.i("SAIU", " akii");
                    super.onCancelled();
                }
            }catch (Exception e){
                Log.i("Erro: ","" + e.getMessage());
            }
            return pontos;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(ArrayList<PontoTroca> pontos) {
            if(pontos != null) {
                for (PontoTroca p : pontos) {
                    MarkerOptions markerPoint = (new MarkerOptions()
                            .position(new LatLng(p.getLatitude(), p.getLongitude()))
                            .title(p.getDescricao()));
                    switch (p.getTipo()) {
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
}


