package com.e2g.ecocicle;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.e2g.ecocicle.Model.InfLevel;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tigrao on 09/03/15.
 */
public class PerfilFragment extends Fragment implements View.OnClickListener{

    private View rootView;

    private TextView nomeCaboko;
    private TextView levelCaboko;
    private TextView txtProgress;

    private TextView txtSaldo;

    private ProgressBar progreLevel;
    private ProgressDialog progressDialog;

    ListView listaProgress;
    PerfilAdpter listaAdapter;

    public PerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

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
        progressDialog.setMessage("Loading user information!");
        progressDialog.show();

        nomeCaboko = (TextView) rootView.findViewById(R.id.txtUsuario);
        levelCaboko = (TextView) rootView.findViewById(R.id.txtLevel);
        txtProgress = (TextView) rootView.findViewById(R.id.txtProgress);
        progreLevel = (ProgressBar) rootView.findViewById(R.id.progressLevel);
        txtSaldo = (TextView) rootView.findViewById(R.id.totEco);

        nomeCaboko.setText(((Main)getActivity().getApplication()).getUsuarioNaApp().getName().toString());

        new RecuperaLevel(((Main)getActivity().getApplication()).getUsuarioNaApp().getIdCliente().toString()).execute();
        new BuscarSaldo().execute();
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_btn_entrar:
                break;
        }
    }

    class BuscarSaldo extends AsyncTask<Void, Void, String>{

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
                    return String.format("%.2f", saldo);
                } catch (JSONException e) {
                    Log.i("Erro: ", " >>" + e.getMessage());
                    return "0.00";
                }
            }
            return "0.00";
        }

        @Override
        protected void onPostExecute(String valor) {
            txtSaldo.setText("Eco$: "+valor);
        }
    }

    class RecuperaLevel extends AsyncTask<Void, Void, String> {
        private String codigo;

        RecuperaLevel(String codigo) {
            this.codigo = codigo;
        }

        @Override
        protected String doInBackground(Void... params) {
            try{
                String url = "http://ecocicle.mybluemix.net/api/client/profile/" + codigo;
                String[] resposta = new WebServiceCliente().get(url);
                if (resposta[0].equals("200")){
                    return resposta[1];
                }
                return null;
            } catch (Exception e){
                Log.i("ERRO!!", "" + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String strJson) {

            if (strJson != null) {
                try{
                    //GERAL xp
                    JSONObject jsonObject = new JSONObject(strJson);
                    int level = jsonObject.getInt("Level");
                    int maximo = jsonObject.getInt("next");
                    Double prog = jsonObject.getDouble("xp");
                    levelCaboko.setText("Level: " + level);
                    txtProgress.setText(prog.intValue() + "/" + maximo);
                    progreLevel.setMax(maximo);
                    progreLevel.setProgress(prog.intValue());

                    Gson gson = new Gson();
                    ArrayList<InfLevel> niveisColeecions = new ArrayList<>();
                    InfLevel infLevel = null;
                    //Glass...
                    JSONObject glas = jsonObject.getJSONObject("glass");
                    infLevel = gson.fromJson(glas.toString(), InfLevel.class);
                    infLevel.setTipoNome("Glass");
                    niveisColeecions.add(infLevel);

                    //paper...
                    JSONObject paper = jsonObject.getJSONObject("paper");
                    infLevel = gson.fromJson(paper.toString(), InfLevel.class);
                    infLevel.setTipoNome("Paper");
                    niveisColeecions.add(infLevel);

                    //plastic...
                    JSONObject plastic = jsonObject.getJSONObject("plastic");
                    infLevel = gson.fromJson(plastic.toString(), InfLevel.class);
                    infLevel.setTipoNome("Plastic");
                    niveisColeecions.add(infLevel);

                    //organic...
                    JSONObject organic = jsonObject.getJSONObject("organic");
                    infLevel = gson.fromJson(organic.toString(), InfLevel.class);
                    infLevel.setTipoNome("Organic");
                    niveisColeecions.add(infLevel);

                    //metal...
                    JSONObject metal = jsonObject.getJSONObject("metal");
                    infLevel = gson.fromJson(metal.toString(), InfLevel.class);
                    infLevel.setTipoNome("Metal");
                    niveisColeecions.add(infLevel);

                    listaProgress = (ListView) rootView.findViewById(R.id.listViewPerfil);
                    listaAdapter = new PerfilAdpter(getActivity(), niveisColeecions);
                    listaProgress.setAdapter(listaAdapter);

                    progressDialog.dismiss();
                }catch (Exception e){
                    Log.i("Erro", "" + e.getMessage());
                }
            }
        }
    }

    class PerfilAdpter extends BaseAdapter{

        private Context context;
        private List<InfLevel> listaLeveis;

        PerfilAdpter(Context context, List<InfLevel> listaLeveis) {
            this.context = context;
            this.listaLeveis = listaLeveis;
        }

        @Override
        public int getCount() {
            return listaLeveis.size();
        }

        @Override
        public Object getItem(int position) {
            return listaLeveis.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.row_perfil, null);

            TextView tipoLevel = (TextView) layout.findViewById(R.id.textTipoPerfil);
            TextView levelTipo = (TextView) layout.findViewById(R.id.txtNivel);
            TextView lvlCategoria = (TextView) layout.findViewById(R.id.lvlCategoria);

            tipoLevel.setText(listaLeveis.get(position).getTipoNome() + "  -  ");
            //int lvl = Integer.valueOf(listaLeveis.get(position).getLevel());
            levelTipo.setText("Level: " + listaLeveis.get(position).getLevel());

            ProgressBar pb = (ProgressBar) layout.findViewById(R.id.progressBarCada);
            int max = Integer.parseInt(listaLeveis.get(position).getNext());
            pb.setMax(max);
            pb.setProgress(listaLeveis.get(position).getXp().intValue());
            lvlCategoria.setText(listaLeveis.get(position).getXp().intValue() + "/" + max);

            ImageView imagem = (ImageView) layout.findViewById(R.id.imageViewPerfil);
            switch (listaLeveis.get(position).getLevel().toString()){
                case "0":
                    imagem.setImageResource(R.drawable.sorte);
                    break;
                case "1":
                    imagem.setImageResource(R.drawable.bronze);
                    break;
                case "2":
                    imagem.setImageResource(R.drawable.silver);
                    break;
                case "3":
                    imagem.setImageResource(R.drawable.ouro);
                    break;
                case "4":
                    imagem.setImageResource(R.drawable.diamante);
                    break;
                default:
                    imagem.setImageResource(R.drawable.diamante);
                    break;
            }
            return layout;
        }
    }
}
