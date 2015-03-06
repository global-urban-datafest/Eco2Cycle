package com.e2g.ecocicle;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.e2g.ecocicle.barcode.IntentIntegrator;
import com.e2g.ecocicle.barcode.IntentResult;


public class LeitorQr extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitor_qr);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_leitor_qr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickButton(View view){
        int id = view.getId();
        switch (id){
            case R.id.btnReadQr:
                Log.i("btn", "SCAN");
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            Toast.makeText(getApplicationContext(),
                    "QR READ " + ((Main)getApplication()).getUsuarioNaApp().getLogin(), Toast.LENGTH_SHORT).show();
            TextView lbUser = (TextView) findViewById(R.id.textUser);
            lbUser.setText(((Main)getApplication()).getUsuarioNaApp().getLogin().toString());

            //String scanContent = scanningResult.getContents();
            //contentTxt.setText(scanContent);

            //scanBtn.setEnabled(false);
            //new RecuperaVoucher(scanContent.toString()).execute();
        }else{
            Toast.makeText(getApplicationContext(),
                    "Nada recebido!!", Toast.LENGTH_SHORT).show();
        }
    }
}
