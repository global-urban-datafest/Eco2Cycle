package com.e2g.ecocicle;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.e2g.ecocicle.Model.Client;
import com.e2g.ecocicle.WebService.WebServiceCliente;
import com.google.gson.Gson;


public class Login extends ActionBarActivity {
    private TextView textLogin;
    private TextView textSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void OnClickBotao(View view){
        int id = view.getId();
        switch (id){
            case R.id.btn_entrar:
                //Fazer Login...
                textLogin = (TextView) findViewById(R.id.editLogin);
                textSenha = (TextView) findViewById(R.id.editSenha);
                //Verificar se tem alguma coisa nos campos
                if(textSenha.getText().toString().equals("") ||
                        textLogin.getText().toString().equals("")){
                    Toast.makeText(this, "Fill out all fields!", Toast.LENGTH_LONG).show();
                }else{
                    new RecuperaLogin(textSenha.getText().toString(),
                            textLogin.getText().toString()).execute();
                }

                break;
            case R.id.btn_cancelar:
                super.onBackPressed();
                break;
            case R.id.btn_cadastra:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);

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

    class RecuperaLogin extends AsyncTask<Void, Void, Client>{
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
//            Log.i("Respota 1 >", "" + respota[0]);
            Client clientPopulace = null;
            if(respota[0].equals("200")){
                clientPopulace = gson.fromJson(respota[1], Client.class);
            }
            return clientPopulace;
        }

        @Override
        protected void onPostExecute(Client client) {
            if(client == null || client.getIdCliente() == 0){
                Log.i("CLiente id: ","client não existe");
                Toast.makeText(getApplicationContext(), "User or password not !", Toast.LENGTH_LONG).show();
            }else{
                ((Main)getApplication()).setLogado(true);
                ((Main)getApplication()).setUsuarioNaApp(client);
                //Login
//                Log.i("CLiente id: ","" +client.getIdCliente());
                Toast.makeText(getApplicationContext(), "Welcome, " + client.getLogin() + "!", Toast.LENGTH_LONG).show();
                Intent myIntent;
                myIntent = new Intent(getApplicationContext(), Mapa.class);
                startActivity(myIntent);
            }
        }
    }
}
