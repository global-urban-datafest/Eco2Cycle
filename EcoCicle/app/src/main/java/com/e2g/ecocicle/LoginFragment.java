package com.e2g.ecocicle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

    //Configurações globais so sistema
    public static final String URL_PREF = "LoginFragment";
    private SharedPreferences prefMain;
    private SharedPreferences.Editor editor;

    private View rootView;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
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
                Fragment fragmentCadastro = new CadastroFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentCadastro).commit();
                break;
            case R.id.btn_cancelar:
                ((Mapa)getActivity()).changeMenuLateral("");
                //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
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
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE
            );
            imm.hideSoftInputFromWindow(textSenha.getWindowToken(), 0);
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
            String url = "http://ecocicle.mybluemix.net/api/client/login";
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
                /*
                preferencias da Activity...
                 */
                ((Main)getActivity().getApplication()).setUsuarioNaApp(client);
                prefMain = getActivity().getSharedPreferences(URL_PREF, Context.MODE_PRIVATE);
                editor = prefMain.edit();
                String login = prefMain.getString("login", "");
                String senha = prefMain.getString("senha", "");
                if (login.equals(""))
                    editor.putString("login", client.getLogin().toString());
                if (senha.equals(""))
                    editor.putString("senha", client.getPass().toString());
                editor.commit();
                //((Main)getActivity().getApplication()).setUsuarioNaApp(client);
                Toast.makeText(getActivity(), "Welcome, " + client.getName() + "!", Toast.LENGTH_LONG).show();
                ((Mapa)getActivity()).changeMenuLateral(client.getName());
                //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        }
    }
}
