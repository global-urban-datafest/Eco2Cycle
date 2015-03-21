package com.e2g.ecocicle;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.e2g.ecocicle.Model.Operation;
import com.e2g.ecocicle.Model.OperationControl;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEcoCoinsFragment extends Fragment {
    private final String URL_DEBITO = "http://ecocicle.mybluemix.net/api/client/operations/buy/";
    private final String URL_CREDITO = "http://ecocicle.mybluemix.net/api/client/operations/sell/";

    private View rootView;

    private TextView txtSaldoTotal;

    private ListView listaOperacoes;
    private OperacoesAdpter operacoesAdpter;

    private ProgressDialog progressDialog;
    public MyEcoCoinsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_eco_coins, container, false);

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

         /*
        Iniciando o Dialog de espera...
         */
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading your extract!");
        progressDialog.show();

        txtSaldoTotal = (TextView) rootView.findViewById(R.id.txtSaldoCaboco);
        new BuscarSaldo().execute();
        new BuscaCreditos().execute();

        return rootView;
    }

    class BuscaCreditos extends AsyncTask<Void, Void, ArrayList<OperationControl>>{

        @Override
        protected ArrayList<OperationControl> doInBackground(Void... params) {
            ArrayList<OperationControl> operacoes = null;
            ArrayList<OperationControl> operacoesDebito = null;
            String idUsuario =  ((Main)getActivity().getApplication()).getUsuarioNaApp().getIdCliente().toString();
            Gson gson = new Gson();
            try{
                String[] resposta = new WebServiceCliente().get(URL_CREDITO+idUsuario);
                String[] respostaDebitos = new WebServiceCliente().get(URL_DEBITO+idUsuario);
                if (resposta[0].equals("200") && respostaDebitos[0].equals("200")){
                    Type collType = new TypeToken<ArrayList<OperationControl>>() {}.getType();
                    operacoes = gson.fromJson(resposta[1], collType);
                    operacoesDebito = gson.fromJson(respostaDebitos[1], collType);
                    operacoes.addAll(operacoesDebito);
                }
            } catch (Exception e){
                Log.i("Erro Creditos", ">>" + e.getMessage());
            }
            return operacoes;
        }

        @Override
        protected void onPostExecute(ArrayList<OperationControl> operationControls) {
            if(operationControls != null){
                listaOperacoes = (ListView) rootView.findViewById(R.id.detalhesOperacoes);
                operacoesAdpter = new OperacoesAdpter(getActivity(), operationControls);
                listaOperacoes.setAdapter(operacoesAdpter);

                progressDialog.dismiss();
            }
        }
    }
    class BuscarSaldo extends AsyncTask<Void, Void, Double> {

        @Override
        protected Double doInBackground(Void... params) {
            Long idCliente = ((Main)getActivity().getApplication()).getUsuarioNaApp().getIdCliente();
            String url = "http://ecocicle.mybluemix.net/api/client/coins/" + idCliente.toString();
            String respotas[] = new WebServiceCliente().get(url);
            if(respotas[0].equals("200")){
                try {
                    JSONObject objeto = new JSONObject(respotas[1]);
                    Double valor = objeto.getDouble("coins");
                    Double valorGasto = objeto.getDouble("xp");
                    Double saldo = valor - valorGasto;
                    Log.i("VALOR", ">>" + saldo);
                    return saldo;
                } catch (JSONException e) {
                    Log.i("Erro: ", " >>" + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Double valor) {
            if (valor != null) {
                String result = String.format("%.2f", valor);
                txtSaldoTotal.setText("Balance Eco$: " + result);
            }else{
                txtSaldoTotal.setText("Balance Eco$: - - -");
            }
        }
    }

    class OperacoesAdpter extends BaseAdapter {
        private Context context;
        private List<OperationControl> operacoes;

        OperacoesAdpter(Context context, List<OperationControl> operacoes) {
            this.context = context;
            this.operacoes = operacoes;
        }

        @Override
        public int getCount() {
            return operacoes.size();
        }

        @Override
        public Object getItem(int position) {
            return operacoes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.row_extract, null);

            TextView txtPointExt = (TextView) layout.findViewById(R.id.txtExtPoint);
            String pointString = operacoes.get(position).getPoint();

            TextView txtEcoCoin = (TextView) layout.findViewById(R.id.extEcoCoin);
            String resultado = String.format("%.2f", operacoes.get(position).getEcocoin());
            txtEcoCoin.setText(resultado);

            ImageView img = (ImageView) layout.findViewById(R.id.imgRow);

            TextView txtDataOpe = (TextView) layout.findViewById(R.id.txtData);
            String[] splitData = operacoes.get(position).getDate().split(" ");
            txtDataOpe.setText(splitData[0]);

            LinearLayout fundo = (LinearLayout) layout.findViewById(R.id.fundoCor);
            if (operacoes.get(position).getBuy()){
                //fundo.setBackgroundColor(getResources().getColor(R.color.debito));
                img.setImageResource(R.drawable.debito);
                pointString += "   (Debit)";
            }else{
                //fundo.setBackgroundColor(getResources().getColor(R.color.credito));
                img.setImageResource(R.drawable.folha);
                pointString += "   (Credit)";
            }
            txtPointExt.setText(pointString);
            return layout;
        }
    }
}
