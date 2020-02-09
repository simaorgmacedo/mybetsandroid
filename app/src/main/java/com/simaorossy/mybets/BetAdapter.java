package com.simaorossy.mybets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simaorossy.mybets.dominio.entidade.Bets;

import java.util.List;

public class BetAdapter extends RecyclerView.Adapter<BetAdapter.ViewHolderBets> {

    public List<Bets> dados;

    public BetAdapter(List<Bets> dados){

        this.dados = dados;
    }



    @NonNull
    @Override
    public BetAdapter.ViewHolderBets onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.linha_bet, parent, false);

        ViewHolderBets holderBets = new ViewHolderBets(view);

        return holderBets;
    }

    @Override
    public void onBindViewHolder(@NonNull BetAdapter.ViewHolderBets holder, int position) {

        if ((dados != null) && (dados.size() > 0 )) {


            Bets bet = dados.get(position);

            holder.txtValorApostadoRCV.setText((int) bet.aposta);
            holder.txtDataRCV.setText(bet.data);
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }






    public class ViewHolderBets extends RecyclerView.ViewHolder{

        public TextView txtValorApostadoRCV;
        public TextView txtDataRCV;

        public ViewHolderBets(@NonNull View itemView) {
            super(itemView);
            txtValorApostadoRCV = (TextView) itemView.findViewById(R.id.txtValorApostadoRCV);
            txtDataRCV          = (TextView) itemView.findViewById(R.id.txtDataRCV);

        }
    }





}
