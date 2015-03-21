package com.e2g.ecocicle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.e2g.ecocicle.Model.Client;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;


public class Principal extends Activity {
    private SharedPreferences prefMain;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        if (isOnline()) {
            //TELA de SPLASHHH!
            //Recupera login
            prefMain = getSharedPreferences(LoginFragment.URL_PREF, Context.MODE_PRIVATE);
            //editor = prefMain.edit();
            String login = prefMain.getString("login", "");
            String senha = prefMain.getString("senha", "");

            if (!login.equals("") && !senha.equals("")) {
                Log.i("SPLASH", "login: " + login + " - S " + senha);
                new RecuperaLoginSplash(senha, login).execute();
            }else{
                Log.i("SPLASH", "nada ainda");
                new Thread(new Runnable() {
                    public void run() {
                        int t = 0;
                        while (t < 50) {
                            t++;
                            SystemClock.sleep(10L);
                        }
                        //Nova intent...
                        Intent i = new Intent(Principal.this, Mapa.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                        Principal.this.finish();
                    }
                }).start();
            }
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
            alerta.setTitle("No connection!");
            alerta.setMessage("This device not have data connection!");
            alerta.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            System.exit(0);
                        }
                    });
            alerta.show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    class RecuperaLoginSplash extends AsyncTask<Void, Void, Client> {
        private String login;
        private String senha;

        RecuperaLoginSplash(String senha, String login) {
            this.senha = senha;
            this.login = login;
        }

        @Override
        protected Client doInBackground(Void... params) {
            String url = "http://ecocicle.mybluemix.net/api/client/login";
            Client cliente = new Client(senha, login);
            Gson gson = new Gson();
            String clienteJson = gson.toJson(cliente);
            String respota[] = new WebServiceCliente().post(url, clienteJson);
            Client clientPopulace = null;
            if (respota[0].equals("200")) {
                clientPopulace = gson.fromJson(respota[1], Client.class);
            }
            return clientPopulace;
        }

        @Override
        protected void onPostExecute(Client client) {
            if (client != null || client.getIdCliente() != 0) {
                ((Main) getApplication()).setUsuarioNaApp(client);
                Toast.makeText(getApplicationContext(), "Welcome, " + client.getName() + "!", Toast.LENGTH_LONG).show();
                new Thread(new Runnable() {
                    public void run() {
                        int t = 0;
                        while (t < 50) {
                            t++;
                            SystemClock.sleep(15L);
                        }
                        //Nova intent...
                        Intent i = new Intent(Principal.this, Mapa.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(i);
                        Principal.this.finish();
                    }
                }).start();
            }
        }
    }
}
