package com.e2g.ecocicle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.os.Bundle;


public class Principal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        if(isOnline()){
            //TELA de SPLASHHH!
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
        }else{
            AlertDialog.Builder alerta = new AlertDialog.Builder(Principal.this);
            alerta.setTitle("Sem conexão");
            alerta.setMessage("Este dispositivo não possui conexao!");
            alerta.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener(){
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
        //return true;
    }
}
