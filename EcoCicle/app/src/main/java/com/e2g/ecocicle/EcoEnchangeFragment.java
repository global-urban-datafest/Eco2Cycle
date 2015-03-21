package com.e2g.ecocicle;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.e2g.ecocicle.Model.Operation;
import com.e2g.ecocicle.Model.PontoTroca;
import com.e2g.ecocicle.Model.Product;
import com.e2g.ecocicle.Model.ProdutEco;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.VolleySingleton;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class EcoEnchangeFragment extends Fragment {
    private final String URL_PRODUTO = "http://ecocicle.mybluemix.net/api/productpoint/produtosponto/sell/mobile";
    private ListView listaProdutos;
    private ProdutosAdpter produtosAdpter;
    private ProgressDialog progressDialog;

    private View rootView;

    public EcoEnchangeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_eco_enchange, container, false);

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
        progressDialog.setMessage("Loading list of products!");
        progressDialog.show();

        new RecuperaProdutosVenda().execute();
        return rootView;
    }


    class RecuperaProdutosVenda extends AsyncTask<Void, Void, ArrayList<ProdutEco>>{
        @Override
        protected ArrayList<ProdutEco> doInBackground(Void... params) {
            ArrayList<ProdutEco> produtos = null;
            try {
                String resposta[] = new WebServiceCliente().get(URL_PRODUTO);
                if (resposta[0].equals("200")){
                    Gson gson = new Gson();
                    Type collType = new TypeToken<ArrayList<ProdutEco>>() {}.getType();
                    produtos = gson.fromJson(resposta[1], collType);
                }
            }catch (Exception e){
                Log.i("Erro>>", ""+e.getMessage());
            }
            return produtos;
        }

        @Override
        protected void onPostExecute(final ArrayList<ProdutEco> products) {
            if (products != null){
                listaProdutos = (ListView) rootView.findViewById(R.id.listViewProdutosEco);
                produtosAdpter = new ProdutosAdpter(getActivity(), products);
                listaProdutos.setAdapter(produtosAdpter);
                listaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                        alerta.setTitle("Buy!");
                        alerta.setMessage("Are you sure to buy a " + products.get(position).getName() + "?");
                        alerta.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        new EfetivarCompra(products.get(position)).execute();
                                        dialog.dismiss();
                                    }
                                });
                        alerta.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alerta.show();
                    }
                });
                progressDialog.dismiss();
            }
        }
    }

    class EfetivarCompra extends AsyncTask<Void, Void, String>{
        private ProdutEco produto;

        EfetivarCompra(ProdutEco produto) {
            this.produto = produto;
        }

        @Override
        protected String doInBackground(Void... params) {
            Long idCliente = ((Main)getActivity().getApplication()).getUsuarioNaApp().getIdCliente();
            String url = "http://ecocicle.mybluemix.net/api/client/coins/" + idCliente.toString();
            String respotas[] = new WebServiceCliente().get(url);
            if(respotas[0].equals("200")){
                try {
                    JSONObject objeto = new JSONObject(respotas[1]);
                    Double valor = objeto.getDouble("coins");
                    Double valorGasto = objeto.getDouble("xp");
                    Double saldo = valor - valorGasto;
                    if (saldo >= produto.getEcocoin()){
                        StringBuffer strOperacao = new StringBuffer();
                        strOperacao.append("{\"idOperacao\":\"0\",\"price\":\"0\",\"ecoCoin\":\"");
                        strOperacao.append(produto.getEcocoin());
                        strOperacao.append("\",\"productPointidProdutoPonto\":{\"idProdutoPonto\":\"");
                        strOperacao.append(produto.getIdProdutoPonto());
                        strOperacao.append("\"},\"buy\":\"true\",");
                        strOperacao.append("\"clientidCliente\": {\"idCliente\":\"");
                        strOperacao.append(idCliente);
                        strOperacao.append("\"}}");

                        String urlCadastrar = "http://ecocicle.mybluemix.net/api/operation/";
                        new WebServiceCliente().post(urlCadastrar, strOperacao.toString());
                        return "efetivado";
                    }else{
                        return "notsaldo";
                    }
                } catch (Exception e){
                    Log.i("Erro> ", "" + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
            alerta.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            if (s.equals("efetivado")){
                alerta.setTitle("Congratulations!");
                alerta.setMessage("Your purchase has been completed!");
            }
            if (s.equals("notsaldo")){
                alerta.setTitle(":(");
                alerta.setMessage("You not have EcoCoins enough, but not give up!");
            }
            alerta.show();
        }
    }

    class ProdutosAdpter extends BaseAdapter {
        private Context context;
        private List<ProdutEco> produtosEco;

        ProdutosAdpter(Context context, List<ProdutEco> produtosEco) {
            this.context = context;
            this.produtosEco = produtosEco;
        }

        @Override
        public int getCount() {
            return produtosEco.size();
        }

        @Override
        public Object getItem(int position) {
            return produtosEco.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.row_produto_eco, null);

            TextView descricao = (TextView) layout.findViewById(R.id.txtTitulo);
            TextView valor = (TextView) layout.findViewById(R.id.txtPrecoEco);
            TextView descricaoProduto = (TextView) layout.findViewById(R.id.txtDescricaoProduto);
            final ImageView image = (ImageView) layout.findViewById(R.id.imgProduto);

            descricao.setText(produtosEco.get(position).getName());
            String result = String.format("%.2f", produtosEco.get(position).getEcocoin());
            valor.setText("Eco$: " + result);
            descricaoProduto.setText(produtosEco.get(position).getDescription().toString());

            //setar imagem
            try{
                ImageLoader imagemLoader = VolleySingleton.getInstance(getActivity().getApplicationContext()).getImageLoader();
                imagemLoader.get(produtosEco.get(position).getImg(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        //Log.i("Img ime > ", " "+ isImmediate);
                        if(response.getBitmap() != null){
                            image.setImageBitmap(response.getBitmap());
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //podemos setar uma imagem de erro aki...
                        System.out.println("ERRO: "+ error.getMessage());
                    }
                });
            } catch (Exception e){
                Log.i("Erro imagem", "" + e.getMessage());
            }
            return layout;
        }
    }
}
