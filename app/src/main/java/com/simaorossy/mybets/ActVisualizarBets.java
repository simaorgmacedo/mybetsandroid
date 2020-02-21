package com.simaorossy.mybets;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.simaorossy.mybets.database.DadosOpenHelper;
import com.simaorossy.mybets.dominio.entidade.Bets;
import com.simaorossy.mybets.dominio.repositorio.BetsRepositorio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActVisualizarBets extends AppCompatActivity {

    private Bets bets = new Bets();
    private String resultado;
    private DadosOpenHelper dadosOpenHelper;
    private BetsRepositorio betsRepositorio;
    private SQLiteDatabase conexao;
    private ImageView imageView;
    private Drawable imgGols;
    private Drawable imgCatao;
    private Drawable imgEscanteio;
    private Drawable imgGalgoBack;
    private Drawable imgGalgoLay;
    private Drawable imgVitoria;
    private Drawable imgEmpate;
    private Drawable imgBasquete;
    private Drawable ImgGalgoLayPlaced;
    private Drawable imgGalgoAvg;


    private ImageView imgMercado;
    //private TextView txtResultadoV;
    private TextView txtMercadoV;
    private TextView txtApostaV;
    private TextView txtRetornoV;
    private TextView txtedtOddV;
    private TextView txtedtDataV;
    private TextView txtedtDescricaoV;

    private ConstraintLayout actVisualizar;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_visualizar_bets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actVisualizar = findViewById(R.id.content_act_visualizar_bet);
        imageView     = findViewById(R.id.imgResultado);


        imgCatao      = getResources().getDrawable(R.drawable.img_cartao);
        imgEscanteio  = getResources().getDrawable(R.drawable.img_escanteio);
        imgGalgoBack  = getResources().getDrawable(R.drawable.img_galgo_back);
        imgGalgoLay   = getResources().getDrawable(R.drawable.img_galgo_lay);
        imgVitoria    = getResources().getDrawable(R.drawable.img_vitoria);
        imgGols       = getResources().getDrawable(R.drawable.img_gols);
        imgMercado    = findViewById(R.id.imgResultado);
        imgEmpate     = getResources().getDrawable(R.drawable.img_empate);
        imgGalgoAvg   = getResources().getDrawable(R.drawable.img_galgo_avb);
        ImgGalgoLayPlaced = getResources().getDrawable(R.drawable.img_galgo_lay_placed);
        imgBasquete   = getResources().getDrawable(R.drawable.img_basquete);

        //txtResultadoV     = findViewById(R.id.txtResultadoV);
        txtMercadoV       = findViewById(R.id.txtMercadoV);
        txtApostaV        = findViewById(R.id.txtApostaV);
        txtRetornoV       = findViewById(R.id.txtRetornoV);
        txtedtOddV        = findViewById(R.id.txtOddV);
        txtedtDataV       = findViewById(R.id.txtDataV);
        txtedtDescricaoV  = findViewById(R.id.txtDescricaoV);


        criarConexao();
        verificaparametro();




    }

    private void verificaparametro(){
        Bundle bundle = getIntent().getExtras();


        if((bundle != null ) && (bundle.containsKey("BET"))){
            bets = (Bets)bundle.getSerializable("BET");



            //txtResultadoV.setText(bets.resultado);
            resultado = bets.resultado;
            if(resultado.equals("win")){
                //Snackbar.make(actVisualizar,"win", Snackbar.LENGTH_LONG).setAction("OK",null).show();
                //Drawable drawable = getResources().getDrawable(R.drawable.green);
                //imageView.setImageDrawable(drawable);
                actVisualizar.setBackgroundColor(Color.parseColor("#7598FB98"));
            }else
            if(resultado.equals("loss")) {
                //Snackbar.make(actVisualizar,"loss", Snackbar.LENGTH_LONG).setAction("OK",null).show();
                //Drawable drawable = getResources().getDrawable(R.drawable.red);
                //imageView.setImageDrawable(drawable);
                actVisualizar.setBackgroundColor(Color.parseColor("#75FFA07A"));

            }

            switch (bets.mercado){
                case("CARTÃO"):
                    imgMercado.setImageDrawable(imgCatao);
                    break;
                case("ESCANTEIO"):
                    imgMercado.setImageDrawable(imgEscanteio);
                    break;
                case("GALGO LAY PLACED"):
                    imgMercado.setImageDrawable(ImgGalgoLayPlaced);
                    break;
                case("GALGO LAY"):
                    imgMercado.setImageDrawable(imgGalgoLay);
                    break;
                case("GOLS"):
                    imgMercado.setImageDrawable(imgGols);
                    break;
                case("VITÓRIA"):
                    imgMercado.setImageDrawable(imgVitoria);
                    break;
                case("BASQUETE"):
                    imgMercado.setImageDrawable(imgBasquete);
                    break;
                case("EMPATE"):
                    imgMercado.setImageDrawable(imgEmpate);
                    break;
                case("GALGO AVB"):
                    imgMercado.setImageDrawable(imgGalgoAvg);
                    break;
                case("GALGO BACK"):
                    imgMercado.setImageDrawable(imgGalgoBack);
                    break;
            }


            txtMercadoV.setText(bets.mercado);
            txtApostaV.setText(String.valueOf(bets.aposta));
            txtRetornoV.setText(String.valueOf(bets.retorno));
            txtedtOddV.setText(String.valueOf(bets.odd));
            txtedtDataV.setText(String.valueOf(bets.data));
            txtedtDescricaoV.setText(bets.descricao);

        }
    }


    private void criarConexao(){


        try{

            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            //Snackbar.make(layoutAct_cadastrar_bets,"banco de dados criado com sucesso", Snackbar.LENGTH_LONG).setAction("OK",null).show();

            betsRepositorio = new BetsRepositorio(conexao);


        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.show();

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_visualizar_bets, menu);

        return super.onCreateOptionsMenu(menu);

    }





    //metodo para definir o que sera feito ao clicar no menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.actionEditar:
                Intent it = new Intent(ActVisualizarBets.this, ActCadastrarBets.class);
                it.putExtra("BETS", bets);
                startActivityForResult(it, 2);
                break;

            case R.id.actionExcluir:
                betsRepositorio.excluir(bets.codigo);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        bets = betsRepositorio.buscarBet(bets.codigo);

        //txtResultadoV.setText(bets.resultado);
        resultado = bets.resultado;
        if(resultado.equals("win")){
            actVisualizar.setBackgroundColor(Color.parseColor("#7598FB98"));
        }else
        if(resultado.equals("loss")) {
            actVisualizar.setBackgroundColor(Color.parseColor("#75FFA07A"));
        }

        switch (bets.mercado){
            case("CARTÃO"):
                imgMercado.setImageDrawable(imgCatao);
                break;
            case("ESCANTEIO"):
                imgMercado.setImageDrawable(imgEscanteio);
                break;
            case("GALGO LAY PLACED"):
                imgMercado.setImageDrawable(ImgGalgoLayPlaced);
                break;
            case("GALGO LAY"):
                imgMercado.setImageDrawable(imgGalgoLay);
                break;
            case("GOLS"):
                imgMercado.setImageDrawable(imgGols);
                break;
            case("VITÓRIA"):
                imgMercado.setImageDrawable(imgVitoria);
                break;
            case("BASQUETE"):
                imgMercado.setImageDrawable(imgBasquete);
                break;
            case("EMPATE"):
                imgMercado.setImageDrawable(imgEmpate);
                break;
            case("GALGO AVB"):
                imgMercado.setImageDrawable(imgGalgoAvg);
                break;
            case("GALGO BACK"):
                imgMercado.setImageDrawable(imgGalgoBack);
                break;
        }
        txtMercadoV.setText(bets.mercado);
        txtApostaV.setText(String.valueOf(bets.aposta));
        txtRetornoV.setText(String.valueOf(bets.retorno));
        txtedtOddV.setText(String.valueOf(bets.odd));
        txtedtDataV.setText(String.valueOf(bets.data));
        txtedtDescricaoV.setText(bets.descricao);

    }



}
