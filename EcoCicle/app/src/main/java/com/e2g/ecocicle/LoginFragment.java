package com.e2g.ecocicle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.e2g.ecocicle.Model.Client;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;

/**
 * Created by tigrao on 09/03/15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        (rootView.findViewById(R.id.frag_btn_entrar)).setOnClickListener(this);
        (rootView.findViewById(R.id.btn_cadastra)).setOnClickListener(this);
        (rootView.findViewById(R.id.btn_cancelar)).setOnClickListener(this);

        fragment = new Fragment();
        fragmentManager = getActivity().getSupportFragmentManager();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_btn_entrar:
                tentarLogin();
                break;
            case R.id.btn_cadastra:
                break;
            case R.id.btn_cancelar:
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                break;
        }
    }

    private void tentarLogin(){
        TextView textLogin = (TextView) rootView.findViewById(R.id.fragEditLogin);
        TextView textSenha = (TextView) rootView.findViewById(R.id.fragEditSenha);

        if(textSenha.getText().toString().equals("") ||
                textLogin.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Fill out all fields!", Toast.LENGTH_LONG).show();
        }else{
            new RecuperaLogin(textSenha.getText().toString(),
                    textLogin.getText().toString()).execute();
        }
    }

    class RecuperaLogin extends AsyncTask<Void, Void, Client> {
        private String login;
        private String senha;

        RecuperaLogin(String senha, String login) {
            this.senha = senha;
            this.login = login;
        }

        @Override
        protected Client doInBackground(Void... params) {
            String url = "http://ecociclews.mybluemix.net/api/client/login";
            Client cliente = new Client(senha,login);
            Gson gson = new Gson();
            String clienteJson = gson.toJson(cliente);
            String respota[] =  new WebServiceCliente().post(url, clienteJson);
            Client clientPopulace = null;
            if(respota[0].equals("200")){
                clientPopulace = gson.fromJson(respota[1], Client.class);
            }
            return clientPopulace;
        }

        @Override
        protected void onPostExecute(Client client) {
            if(client == null || client.getIdCliente() == 0){
                Toast.makeText(getActivity(), "User or password is incorrect!", Toast.LENGTH_LONG).show();
            }else{
                ((Main)getActivity().getApplication()).setUsuarioNaApp(client);
//                Main m = new Main();
//                m.setUsuarioNaApp(client);
//                ((Main)getApplication()).setLogado(true);
//                ((Main)getApplication()).setUsuarioNaApp(client);
                Toast.makeText(getActivity(), "Welcome, " + client.getLogin() + "!", Toast.LENGTH_LONG).show();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        }
    }
}
