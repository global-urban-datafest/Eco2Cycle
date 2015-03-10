package com.e2g.ecocicle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.e2g.ecocicle.Util.Main;
import com.e2g.ecocicle.barcode.IntentIntegrator;
import com.e2g.ecocicle.barcode.IntentResult;

/**
 * Created by tigrao on 09/03/15.
 */
public class LeitorQrFragment extends Fragment implements View.OnClickListener{

    private View rootView;
    public LeitorQrFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_leitorqr, container, false);
        (rootView.findViewById(R.id.btnReadQr)).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnReadQr:
                IntentIntegrator scanIntegrator = new IntentIntegrator(getActivity());
                scanIntegrator.initiateScan();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            Toast.makeText(getActivity(),
                    "QR READ ", Toast.LENGTH_SHORT).show();
//            TextView lbUser = (TextView) findViewById(R.id.textUser);
//            lbUser.setText(((Main)getApplication()).getUsuarioNaApp().getLogin().toString());

            //String scanContent = scanningResult.getContents();
            //contentTxt.setText(scanContent);

            //scanBtn.setEnabled(false);
            //new RecuperaVoucher(scanContent.toString()).execute();
        }else{
            Toast.makeText(getActivity(),
                    "Nada recebido!!", Toast.LENGTH_SHORT).show();
        }
    }
}
