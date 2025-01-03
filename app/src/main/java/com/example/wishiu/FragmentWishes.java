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

public class FragmentWishes extends Fragment {
    BD bd;
    RecyclerView rv;
    LinearLayoutManager llm;
    ArrayList<ProdutosWishes> produtos;
    ProdutosAdapter produtosA;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wishes, container, false);
        bd = new BD(getActivity());

        produtos = new ArrayList<>();
        produtos.clear();
        produtosA = new ProdutosAdapter(getActivity(), produtos);

        Cursor pp = bd.getProdutos();
        final RelativeLayout rlWishes = (RelativeLayout) rootView.findViewById(R.id.rlWishes);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);

        if(pp != null){
            pp.moveToPosition(-1);
            while (pp.moveToNext()) {
                byte[] imgp = pp.getBlob(3);
                String prcp = "$"+pp.getString(2);
                String titp = capitalizeFirstLetter(pp.getString(1));
                String catp = capitalizeFirstLetter(pp.getString(4));
                produtos.add(new ProdutosWishes(imgp, prcp, titp, catp, pp.getInt(0)));
                produtosA.notifyDataSetChanged();
            }
        }
        pp.close();

        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        if(produtos != null && produtos.size() > 0){
            rv.setAdapter(produtosA);
        } else {
            TextView emptyWishes = new TextView(getActivity());
            emptyWishes.setText("У вас пока нет желаний. Нажмите кнопку +, чтобы добавить в этот список.");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            params.setMargins(24,0,24,0);
            emptyWishes.setLayoutParams(params);
            emptyWishes.setGravity(Gravity.CENTER);
            rlWishes.removeView(rv);
            rlWishes.addView(emptyWishes);
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
