package com.e2g.ecocicle;

import android.app.Application;

import com.e2g.ecocicle.Model.Client;

/**
 * Created by tigrao on 06/03/15.
 */
public class Main extends Application{
    boolean logado = false;
    Client usuarioNaApp;

    public Main() {
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }

    public Client getUsuarioNaApp() {
        return usuarioNaApp;
    }

    public void setUsuarioNaApp(Client usuarioNaApp) {
        this.usuarioNaApp = usuarioNaApp;
    }
}
