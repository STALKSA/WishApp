package com.example.wishiu;

import android.database.Cursor;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentAchieved extends Fragment {
    BD bd;
    RecyclerView rva;
    LinearLayoutManager llma;
    ArrayList<ProdutosWishes> produtosa;
    ProdutosAdaptera produtosAa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_achieved, container, false);
        bd = new BD(getActivity());

        produtosa = new ArrayList<>();
        produtosa.clear();
        produtosAa = new ProdutosAdaptera(getActivity(), produtosa);
        Cursor ppa = bd.getAchieved();
        final RelativeLayout rlWishesa = rootView.findViewById(R.id.rlAchieved);
        rva = rootView.findViewById(R.id.rvA);

        if (ppa != null) {
            ppa.moveToPosition(-1);
            while (ppa.moveToNext()) {
                Cursor ppai = bd.getProdutoAchieved(ppa.getString(1));
                String prcp = "null";
                if (ppai != null && ppai.moveToFirst()) {
                    String titp = capitalizeFirstLetter(ppai.getString(1));
                    String catp = capitalizeFirstLetter(ppai.getString(4));
                    byte[] imgp = ppai.getBlob(3);
                    produtosa.add(new ProdutosWishes(imgp, prcp, titp, catp, ppa.getInt(1)));
                    produtosAa.notifyDataSetChanged();
                }
                if (ppai != null) {
                    ppai.close();
                }
            }
        }
        if (ppa != null) {
            ppa.close();
        }

        llma = new LinearLayoutManager(getActivity());
        rva.setLayoutManager(llma);
        if (produtosa != null && produtosa.size() > 0) {
            rva.setAdapter(produtosAa);
        } else {
            TextView emptyWishesa = new TextView(getActivity());
            emptyWishesa.setText("Пока что ни одно из ваших желаний не осуществилось. Когда вы это сделаете, они будут добавлены в этот список.");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            params.setMargins(24, 0, 24, 0);
            emptyWishesa.setLayoutParams(params);
            emptyWishesa.setGravity(Gravity.CENTER);
            rlWishesa.removeView(rva);
            rlWishesa.addView(emptyWishesa);
        }

        // return no final
        return rootView;
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}

