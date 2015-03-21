package com.e2g.ecocicle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e2g.ecocicle.Model.Product;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by tigrao on 09/03/15.
 */
public class SimulationFragment extends Fragment implements View.OnClickListener{
    private CharSequence[] itensMeterial = {};
    private ArrayList<Product> todosProdutos;
    private View rootView;

    private Button btnType;
    private Button btnTypeTwo;
    private Button btnCalculate;

    private TextView txtPrice;
    private TextView txtEco;

    private EditText quantidade;

    private LinearLayout typeTwo;

    public SimulationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new RecuperaProductos().execute();
        rootView = inflater.inflate(R.layout.fragment_simulation, container, false);

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

        btnType = (Button) rootView.findViewById(R.id.btnTypeElement);
        btnTypeTwo = (Button) rootView.findViewById(R.id.btnTypeMat);
        btnCalculate = (Button) rootView.findViewById(R.id.btnCalculate);

        typeTwo = (LinearLayout) rootView.findViewById(R.id.typeElementTwo);
        btnType.setOnClickListener(this);
        btnTypeTwo.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);

        quantidade = (EditText) rootView.findViewById(R.id.editQuantidade);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnTypeElement:
                disparaLista();
                break;
            case R.id.btnTypeMat:
                disparaListaDois();
                break;
            case R.id.btnCalculate:
                calculaValores();
                break;
        }
    }

    private void calculaValores(){
        //DecimalFormat formatter = new DecimalFormat("0.00");
        if(quantidade.getText().toString().equals("") || quantidade.getText().toString().equals("0")
                || quantidade.getText().toString().equals(" ")){
            Toast.makeText(getActivity(), "Enter a valid amount!", Toast.LENGTH_SHORT).show();
        }else {
            LinearLayout layoutValores = (LinearLayout) rootView.findViewById(R.id.sim_lin_resp);
            layoutValores.setVisibility(View.VISIBLE);
            Double doubQuantidade = Double.parseDouble(quantidade.getText().toString());

            Float doubTotPrice = Float.parseFloat(txtPrice.getText().toString());
            Float doubTotEco = Float.parseFloat(txtEco.getText().toString());

            TextView totEco = (TextView) rootView.findViewById(R.id.txtTotEco);
            String result = String.format("%.2f", (doubQuantidade * doubTotEco));
            totEco.setText(result);


            TextView totPrice = (TextView) rootView.findViewById(R.id.txtTotPrice);
            result = String.format("%.2f", (doubQuantidade * doubTotPrice));
            totPrice.setText(result);
        }
    }

    private void disparaLista(){
        //Alert com os tipos de material
        final AlertDialog.Builder alerBuilder = new AlertDialog.Builder(getActivity());
        alerBuilder.setTitle("Types");
        alerBuilder.setItems(itensMeterial, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnType.setText(itensMeterial[which]);
                typeTwo.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });
        alerBuilder.show();
    }

    private void disparaListaDois(){
        final AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
        alerta.setTitle("Materials");
        List<CharSequence> charSequences = new ArrayList<>();
        for (Product p : todosProdutos){
            if (p.getMaterial().equals(btnType.getText().toString())){
                charSequences.add(p.getProduct());
            }
        }
        final CharSequence[] itensMat = charSequences.toArray(
                new CharSequence[charSequences.size()]);
        alerta.setItems(itensMat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnTypeTwo.setText(itensMat[which]);
                exibeInfomacoes();
                dialog.dismiss();
            }
        });
        alerta.show();
    }

    private void exibeInfomacoes(){
        Product product = null;
        for (Product p : todosProdutos){
            if (p.getProduct().equals(btnTypeTwo.getText().toString())){
                product = p;
                break;
            }
        }
        if (product != null) {
            txtPrice = (TextView) rootView.findViewById(R.id.txtPrice);
            Log.i("DOUBLE", "" + product.getPrice());
            //DecimalFormat formatter = new DecimalFormat("0.00");
            //txtPrice.setText(formatter.format(product.getPrice()));

            txtPrice.setText(product.getPrice().toString());

            txtEco = (TextView) rootView.findViewById(R.id.txtEco);
            //Log.i("DOUBLE", "" + product.getEcocoin());
            //txtEco.setText(formatter.format(product.getEcocoin()));
            txtEco.setText(product.getEcocoin().toString());

            TextView txtUnity = (TextView) rootView.findViewById(R.id.txtUnity);
            txtUnity.setText(product.getUnity().toString());
            LinearLayout layoutPrice = (LinearLayout) rootView.findViewById(R.id.sim_lin_price);
            layoutPrice.setVisibility(View.VISIBLE);
            LinearLayout layoutEco = (LinearLayout) rootView.findViewById(R.id.sim_lin_ecocoins);
            layoutEco.setVisibility(View.VISIBLE);
            LinearLayout layoutUnity = (LinearLayout) rootView.findViewById(R.id.sim_lin_unity);
            layoutUnity.setVisibility(View.VISIBLE);
            LinearLayout layoutQuantidade = (LinearLayout) rootView.findViewById(R.id.sim_lin_quantidade);
            layoutQuantidade.setVisibility(View.VISIBLE);
            LinearLayout layoutCalculate= (LinearLayout) rootView.findViewById(R.id.sim_lin_calculate);
            layoutCalculate.setVisibility(View.VISIBLE);
            LinearLayout layoutTotalInf= (LinearLayout) rootView.findViewById(R.id.sim_lin_tot_inf);
            layoutTotalInf.setVisibility(View.VISIBLE);

        }
    }

    class RecuperaProductos extends AsyncTask<Void, Void, ArrayList<Product>> {
        @Override
        protected ArrayList<Product> doInBackground(Void... params) {
            String url = "http://ecocicle.mybluemix.net/api/product/torecycle/0";
            //Log.i("URL", " " + url);
            String[] resposta = new WebServiceCliente().get(url);
            ArrayList<Product> products = null;
            if (resposta[0].equals("200")) {
                Gson gson = new Gson();
                Type collType = new TypeToken<ArrayList<Product>>() {}.getType();
                products = gson.fromJson(resposta[1], collType);
            }
            return products;
        }

        @Override
        protected void onPostExecute(ArrayList<Product> produtos) {
            todosProdutos = produtos;
            List<CharSequence> charSequences = new ArrayList<>();
            for(Product p : produtos){
                if(!charSequences.contains(p.getMaterial())){
                    charSequences.add(p.getMaterial());
                }
            }
            itensMeterial = charSequences.toArray(
                    new CharSequence[charSequences.size()]);
        }
    }
}
