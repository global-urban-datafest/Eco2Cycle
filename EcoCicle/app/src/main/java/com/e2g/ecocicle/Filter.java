package com.e2g.ecocicle;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.e2g.ecocicle.Model.Pontodetroca;
import com.e2g.ecocicle.Model.Product;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class Filter extends ActionBarActivity {
    ListView lista;
    FilterAdpter filterAdpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        new RecuperaProduct().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
                lista = (ListView) findViewById(R.id.listaFilters);
                filterAdpter = new FilterAdpter(getApplicationContext(), produtos);
                lista.setAdapter(filterAdpter);
                Log.i("FUNFO","Funcionou..");
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
