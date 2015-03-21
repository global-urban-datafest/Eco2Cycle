package com.e2g.ecocicle;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
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

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by tigrao on 09/03/15.
 */
public class LeitorQrFragment extends Fragment implements View.OnClickListener{
    private final String txtBtnGetCoins = "Get my Coins";
    private final String txtBtnGetProduct = "Get Product";

    private View rootView;

    private TextView editTeste;
    private TextView editPrice;
    private TextView editDescription;

    private Operation operacao = null;

    private Button btnValidar;

    private LinearLayout linearCoins;
    private LinearLayout linearPrice;
    private LinearLayout linearDescription;
    private LinearLayout linearInfomations;
    public LeitorQrFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leitorqr, container, false);
        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK){
                    ((Mapa)getActivity()).changeMenuLateral("");
                    return true;
                }else if(keyCode == KeyEvent.KEYCODE_MENU){
                    ((Mapa)getActivity()).abreFechaMenu();
                    return true;
                }else{
                    return false;
                }
            }
        });

        (rootView.findViewById(R.id.btnReadQr)).setOnClickListener(this);
        editTeste = (TextView) rootView.findViewById(R.id.txtQuantCoins);
        editPrice = (TextView) rootView.findViewById(R.id.txtPriceTot);
        //editDescription = (TextView) rootView.findViewById(R.id.txtDescritpiton);

        btnValidar = (Button) rootView.findViewById(R.id.btnValidar);
        btnValidar.setOnClickListener(this);

        linearCoins = (LinearLayout) rootView.findViewById(R.id.lim_qr_coins);
        linearPrice = (LinearLayout) rootView.findViewById(R.id.lim_qr_price);
        //linearDescription = (LinearLayout) rootView.findViewById(R.id.lineardecription);
        linearInfomations = (LinearLayout) rootView.findViewById(R.id.linear_informacoes);

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
            Log.i("STRING" ,"" + scanContent.toString());
            try {
                //linearCoins.setVisibility(View.VISIBLE);
                //linearPrice.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                operacao = gson.fromJson(scanContent, Operation.class);
                //Log.i("PRICE::", "" + operacao.getPrice());
                //if (operacao.getBuy()){ //Compra
                //    Log.i("COMPRa", "metodo de compra por qrcode");
                    //Exibir informacao do produto
                    //new BuscarProduto(operacao.getProductPointidProdutoPonto().getIdProdutoPonto()).execute();
                    //new BuscarSaldo().execute();
                    //editTeste.setText(""+operacao.getEcoCoin());
                    //editPrice.setText("Free");
                //}else{ //Entrega de produto
                linearCoins.setVisibility(View.VISIBLE);
                linearPrice.setVisibility(View.VISIBLE);
                linearInfomations.setVisibility(View.VISIBLE);

                editTeste.setText(String.format("%.2f", operacao.getEcoCoin()));
                editPrice.setText(String.format("%.2f", operacao.getPrice()));
                btnValidar.setText(txtBtnGetCoins);
                btnValidar.setVisibility(View.VISIBLE);
                //}
            }catch (Exception e){
                Log.i("Erro", "GSON " + e.getMessage());
            }
        }else{
            Toast.makeText(getActivity(),
                    "Nada recebido!!", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    class BuscarSaldo extends AsyncTask<Void, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            Long idCliente = ((Main)getActivity().getApplication()).getUsuarioNaApp().getIdCliente();
            String url = "http://ecocicle.mybluemix.net/api/client/coins/" + idCliente.toString();
            String respotas[] = new WebServiceCliente().get(url);
            if(respotas[0].equals("200")){
                try {
                    JSONObject objeto = new JSONObject(respotas[1]);
                    Double valor = objeto.getDouble("coins");
                    Double valorGasto = objeto.getDouble("xp");
                    Double saldo = valor - valorGasto;
                    Log.i("VALOR",">>" + saldo);
                    Double custo = Double.parseDouble(editTeste.getText().toString());
                    if(saldo >= custo){
                        return true;
                    }else{
                        return false;
                    }
                } catch (JSONException e) {
                    Log.i("Erro: ", " >>" + e.getMessage());
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b){
                Toast.makeText(getActivity(), "You have EcoCoins to get this product :D", Toast.LENGTH_LONG).show();
                btnValidar.setText(txtBtnGetProduct);
                linearDescription.setVisibility(View.VISIBLE);
                linearCoins.setVisibility(View.VISIBLE);
                btnValidar.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getActivity(), "You don't have EcoCoins to get this product!", Toast.LENGTH_LONG).show();
            }
        }
    }

    */
    /*
    class BuscarProduto extends AsyncTask<Void, Void, String>{
        private Long codigoProduto;

        BuscarProduto(Long codigoProduto) {
            this.codigoProduto = codigoProduto;
        }

        @Override
        protected String doInBackground(Void... params) {
            try{
                String url = "http://ecocicle.mybluemix.net/api/product/" + codigoProduto.toString();
                String resposta[] = new WebServiceCliente().get(url);
                if(resposta[0].equals("200")){
                    return resposta[1];
                }
            } catch (Exception e){
                Log.i("Erro...", "" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject objeto = new JSONObject(s);
                String nomeProduto = objeto.getString("product");
                editDescription.setText(nomeProduto);
            } catch (Exception e){
                Log.i("Erro Json", " > " + e.getMessage());
            }
        }
    }
    */
    class ValidaQr extends AsyncTask<Void, Void, Boolean> {
        private Operation opt;

        ValidaQr(Operation opt) {
            this.opt = opt;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            operacao.setClientidCliente(((Main)getActivity().getApplication()).getUsuarioNaApp());
            try {
                //VALIDAR OPERACAO... credito ou debito...
                String url = "http://ecocicle.mybluemix.net/api/operation/";
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
                Toast.makeText(getActivity(), "EcoCoins credited in your account.", Toast.LENGTH_LONG).show();
                //linearDescription.setVisibility(View.INVISIBLE);
                btnValidar.setVisibility(View.INVISIBLE);
                linearCoins.setVisibility(View.INVISIBLE);
                linearPrice.setVisibility(View.INVISIBLE);
                linearInfomations.setVisibility(View.INVISIBLE);
            }
        }
    }
}
