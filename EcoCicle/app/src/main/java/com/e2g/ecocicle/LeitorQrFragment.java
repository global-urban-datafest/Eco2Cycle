package com.e2g.ecocicle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e2g.ecocicle.Model.Client;
import com.e2g.ecocicle.Model.Operation;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.e2g.ecocicle.barcode.IntentIntegrator;
import com.e2g.ecocicle.barcode.IntentResult;
import com.google.gson.Gson;


/**
 * Created by tigrao on 09/03/15.
 */
public class LeitorQrFragment extends Fragment implements View.OnClickListener{

    private View rootView;

    private TextView editTeste;
    private TextView editPrice;

    private Operation operacao = null;

    private Button btnValidar;

    private LinearLayout linearCoins;
    private LinearLayout linearPrice;

    public LeitorQrFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leitorqr, container, false);
        (rootView.findViewById(R.id.btnReadQr)).setOnClickListener(this);
        editTeste = (TextView) rootView.findViewById(R.id.txtQuantCoins);
        editPrice = (TextView) rootView.findViewById(R.id.txtPriceTot);

        btnValidar = (Button) rootView.findViewById(R.id.btnValidar);
        btnValidar.setOnClickListener(this);

        linearCoins = (LinearLayout) rootView.findViewById(R.id.lim_qr_coins);
        linearPrice = (LinearLayout) rootView.findViewById(R.id.lim_qr_price);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReadQr:
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
            case R.id.btnValidar:
                if (operacao != null){
                    new ValidaQr(operacao).execute();
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        String scanContent = scanningResult.getContents();
        if (scanContent != null) {
            try {
                linearCoins.setVisibility(View.VISIBLE);
                linearPrice.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                operacao = gson.fromJson(scanContent, Operation.class);
                editTeste.setText(""+operacao.getEcoCoin());
                editPrice.setText(""+operacao.getPrice());
                btnValidar.setVisibility(View.VISIBLE);
            }catch (Exception e){
                Log.i("Erro", "GSON " + e.getMessage());
            }
        }else{
            Toast.makeText(getActivity(),
                    "Nada recebido!!", Toast.LENGTH_SHORT).show();
        }
    }

    class ValidaQr extends AsyncTask<Void, Void, Boolean> {
        private Operation opt;

        ValidaQr(Operation opt) {
            this.opt = opt;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            operacao.setClientidCliente(((Main)getActivity().getApplication()).getUsuarioNaApp());
            try {
                String url = "http://ecociclews.mybluemix.net/api/operation/";
                Gson gson = new Gson();
                String operacaoJson = gson.toJson(opt);
                new WebServiceCliente().post(url, operacaoJson);
                return true;
            } catch (Exception e){
                Log.i("Erro enviar", "ERRO");
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                Toast.makeText(getActivity(), "Pontos creditados", Toast.LENGTH_LONG).show();
                btnValidar.setVisibility(View.INVISIBLE);
                linearCoins.setVisibility(View.INVISIBLE);
                linearPrice.setVisibility(View.INVISIBLE);
            }
        }
    }
}
