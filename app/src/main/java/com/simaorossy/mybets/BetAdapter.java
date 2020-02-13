package com.simaorossy.mybets;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

        ViewHolderBets holderBets = new ViewHolderBets(view, parent.getContext());

        return holderBets;
    }

    @Override
    public void onBindViewHolder(@NonNull BetAdapter.ViewHolderBets holder, int position) {

        if ((dados != null) && (dados.size() > 0 )) {


            Bets bet = dados.get(position);

            holder.txtValorApostadoRCV.setText(String.valueOf(bet.aposta));
            holder.txtDataRCV.setText(bet.data);


            //MUDAR COR DO LAYOUT VERDE OU VERMELHO
            if(bet.resultado.equals("win")){

                holder.layoutLinhaBet.setBackgroundColor(Color.parseColor("#7598FB98"));

            }else {
                if (bet.resultado.equals("loss")) {

                    holder.layoutLinhaBet.setBackgroundColor(Color.parseColor("#75FFA07A"));

                }

            }

            //MUDAR IMAGEM BETS
            switch (bet.mercado){
                case("CARTÃO"):
                    holder.imgMercado.setImageDrawable(holder.imgCartao);
                    break;
                case("ESCANTEIO"):
                    holder.imgMercado.setImageDrawable(holder.imgEscanteio);
                    break;
                case("GALGO BACK"):
                    holder.imgMercado.setImageDrawable(holder.imgGalgoBack);
                    break;
                case("GALGO LAY"):
                    holder.imgMercado.setImageDrawable(holder.imgGalgoLay);
                    break;
                case("GOLS"):
                    holder.imgMercado.setImageDrawable(holder.imgGols);
                    break;
                case("VITÓRIA"):
                    holder.imgMercado.setImageDrawable(holder.imgVitoria);
                    break;

            }






        }








    }

    @Override
    public int getItemCount() {
        return dados.size();
    }






    public class ViewHolderBets extends RecyclerView.ViewHolder{
        private LinearLayout layoutLinhaBet ;
        public TextView txtValorApostadoRCV;
        public TextView txtDataRCV;
        public ImageView imgMercado;
        public Drawable imgCartao;
        public Drawable imgEscanteio;
        public Drawable imgGalgoBack;
        public Drawable imgGalgoLay;
        public Drawable imgGols;
        public Drawable imgVitoria;


        public ViewHolderBets(@NonNull View itemView, final Context context) {
            super(itemView);
            txtValorApostadoRCV = (TextView)     itemView.findViewById(R.id.txtValorApostadoRCV);
            txtDataRCV          = (TextView)     itemView.findViewById(R.id.txtDataRCV);
            layoutLinhaBet      = (LinearLayout) itemView.findViewById(R.id.layoutlinha_bet);
            imgMercado          = (ImageView)    itemView.findViewById(R.id.image_view_bets);

            imgCartao           = (Drawable) context.getResources().getDrawable(R.drawable.img_cartao);
            imgEscanteio        = (Drawable) context.getResources().getDrawable(R.drawable.img_escanteio);
            imgGalgoBack        = (Drawable) context.getResources().getDrawable(R.drawable.img_galgo_back);
            imgGalgoLay         = (Drawable) context.getResources().getDrawable(R.drawable.img_galgo_lay);
            imgGols             = (Drawable) context.getResources().getDrawable(R.drawable.img_gols);
            imgVitoria          = (Drawable) context.getResources().getDrawable(R.drawable.img_vitoria);

            //int RID = context.getApplicationContext().getResources().getIdentifier(imgGols,"drawable",Pack);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dados.size() > 0){
                        Bets bets = dados.get(getLayoutPosition());

                        Intent it = new Intent(context, ActVisualizarBets.class);
                        it.putExtra("BET", bets);
                        ((AppCompatActivity)context).startActivityForResult(it,1);

                    }
                }
            });

        }
    }





}
