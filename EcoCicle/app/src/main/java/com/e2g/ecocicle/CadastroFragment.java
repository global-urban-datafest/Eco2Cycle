package com.e2g.ecocicle;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Toast;

import com.e2g.ecocicle.Model.Client;
import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;

/**
 * Created by tigrao on 09/03/15.
 */
public class CadastroFragment extends Fragment implements View.OnClickListener{

    private View rootView;

    private SharedPreferences prefMain;
    private SharedPreferences.Editor editor;

    private Fragment fragment;
    private FragmentManager fragmentManager;

    private EditText nome;
    private EditText email;

    private EditText senhaUm;
    private EditText senhaDois;
    private Client novoCliente;

    private ProgressDialog progressDialog;

    public CadastroFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_cadastro, container, false);
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

        (rootView.findViewById(R.id.btn_novo_cadastro)).setOnClickListener(this);
        (rootView.findViewById(R.id.btn_cad_calcel)).setOnClickListener(this);
        (rootView.findViewById(R.id.btn_by_facebook)).setOnClickListener(this);
        (rootView.findViewById(R.id.btn_cad_google)).setOnClickListener(this);

        nome = (EditText) rootView.findViewById(R.id.editName);
        email = (EditText) rootView.findViewById(R.id.editEmail);

        senhaUm = (EditText) rootView.findViewById(R.id.editSenhaUm);
        senhaDois = (EditText) rootView.findViewById(R.id.editSenhaDois);


        fragmentManager = getActivity().getSupportFragmentManager();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_novo_cadastro:
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Validating the information!");
                progressDialog.show();
                if(validaForm()){
                    if(validaSenha()){
                        novoCliente = new Client();

                        novoCliente.setEmail(email.getText().toString());
                        novoCliente.setName(nome.getText().toString());
                        //novoCliente.setName("");
                        novoCliente.setPass(senhaDois.getText().toString());

                        novoCliente.setRg("");
                        novoCliente.setCpf("");
                        novoCliente.setAdress("");
                        novoCliente.setLogin(email.getText().toString());
                        //novoCliente.setLogin("");
                        novoCliente.setAtivo("");
                        novoCliente.setIdCliente(0L);
                        new CadastrarClient(novoCliente).execute();
                    }else{
                        Toast.makeText(getActivity(), "Passwords are not equal!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(getActivity(), "Fill out all fields!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                break;
            case R.id.btn_cad_calcel:
                Fragment fragmentCadastro = new LoginFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentCadastro).commit();
                break;
            case R.id.btn_by_facebook:
                isComming();
                break;
            case R.id.btn_cad_google:
                isComming();
                break;
        }
    }
    private Boolean validaSenha(){
        if (senhaUm.getText().toString().equals(senhaDois.getText().toString())){
            return true;
        }
        return false;
    }

    private Boolean validaForm(){
        if (nome.getText().toString().equals("")){
            return false;
        }
        if (email.getText().toString().equals("")){
            return false;
        }
        if (senhaDois.getText().toString().equals("")){
            return false;
        }
        if (senhaUm.getText().toString().equals("")){
            return false;
        }
        return true;
    }

    private void isComming(){
        Toast.makeText(getActivity(), "Function is coming in the next version!", Toast.LENGTH_SHORT).show();
    }

    class CadastrarClient extends AsyncTask<Void, Void, Client> {
        private Client novoCadastro;

        CadastrarClient(Client novoCadastro) {
            this.novoCadastro = novoCadastro;
        }

        @Override
        protected Client doInBackground(Void... params) {
            String url = "http://ecocicle.mybluemix.net/api/client/";
            try{
                Gson gson = new Gson();
                Log.i("Pessoa Json", "" + gson.toJson(novoCadastro));
                new WebServiceCliente().post(url, gson.toJson(novoCadastro));
                return novoCadastro;
            }catch (Exception e){
                Log.i("Erro", "" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Client cli) {
            if (cli != null){
                AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
                alerta.setTitle("Congratulations!");
                alerta.setMessage("Now, you can change the World!");
                alerta.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                ((Main)getActivity().getApplication()).setUsuarioNaApp(cli);
                                prefMain = getActivity().getSharedPreferences(LoginFragment.URL_PREF, Context.MODE_PRIVATE);
                                editor = prefMain.edit();
                                String login = prefMain.getString("login", "");
                                String senha = prefMain.getString("senha", "");
                                if (login.equals(""))
                                    editor.putString("login", cli.getLogin().toString());
                                if (senha.equals(""))
                                    editor.putString("senha", cli.getPass().toString());
                                editor.commit();
                                Toast.makeText(getActivity(), "Welcome, " + cli.getName() + "!", Toast.LENGTH_LONG).show();
                                ((Mapa)getActivity()).changeMenuLateral(cli.getName());

                                dialog.dismiss();
                            }
                        });
                progressDialog.dismiss();
                alerta.show();
            }else {
                Log.i("Deu merda", "Deu merda...");
                progressDialog.dismiss();
            }
        }
    }
}
