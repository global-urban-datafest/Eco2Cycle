package com.e2g.ecocicle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.e2g.ecocicle.Model.Product;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigrao on 09/03/15.
 */
public class FilterFragment extends Fragment{
    private View rootView;
    ListView lista;
    FilterAdpter filterAdpter;

    public FilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        new RecuperaProduct().execute();
        return rootView;
    }

    class RecuperaProduct extends AsyncTask<Void, Void, ArrayList<Product>> {
        @Override
        protected ArrayList<Product> doInBackground(Void... params) {
            String url = "http://ecociclews.mybluemix.net/api/product/0";
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
            try {
                lista = (ListView) rootView.findViewById(R.id.listaFilters);
                filterAdpter = new FilterAdpter(getActivity(), produtos);
                lista.setAdapter(filterAdpter);
                Log.i("FUNFO", "Funcionou..");
            }catch (Exception e){
                Log.i("ERRO TRAT", ">> " + e.getMessage());
            }
        }
    }

    class FilterAdpter extends BaseAdapter{
        private Context context;
        private List<Product> produtos;

        FilterAdpter(Context context, List<Product> produtos) {
            this.context = context;
            this.produtos = produtos;
        }

        @Override
        public int getCount() {
            return produtos.size();
        }

        @Override
        public Object getItem(int position) {
            return produtos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Product produto = new Product();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.row_valor, null);

            TextView tipo = (TextView) layout.findViewById(R.id.textTipo);
            tipo.setTextColor(Color.parseColor("#030000"));
            tipo.setTypeface(null, Typeface.BOLD_ITALIC);
            tipo.setText(produtos.get(position).getProduct() + "  -  ");
            TextView descricao = (TextView) layout.findViewById(R.id.itemDescricao);
            descricao.setTextColor(Color.parseColor("#030000"));
            descricao.setText(produtos.get(position).getMaterial());
            TextView val = (TextView) layout.findViewById(R.id.itemValor);
            val.setText("R$ " + produtos.get(position).getPrice().toString().replace(".", ",") + "    ");
            TextView eco = (TextView) layout.findViewById(R.id.textEcoCoin);
            eco.setText("Ecc " + produtos.get(position).getEcocoin().toString().replace(".", ","));
            return layout;
        }
    }
}
