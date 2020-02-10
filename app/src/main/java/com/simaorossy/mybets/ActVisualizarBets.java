package com.simaorossy.mybets;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.simaorossy.mybets.database.DadosOpenHelper;
import com.simaorossy.mybets.dominio.entidade.Bets;
import com.simaorossy.mybets.dominio.repositorio.BetsRepositorio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActVisualizarBets extends AppCompatActivity {

    private Bets bets = new Bets();
    private String resultado;
    private DadosOpenHelper dadosOpenHelper;
    private BetsRepositorio betsRepositorio;
    private SQLiteDatabase conexao;
    private ImageView imageView;

    private TextView txtResultadoV;
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

        txtResultadoV     = findViewById(R.id.txtResultadoV);
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



            txtResultadoV.setText(bets.resultado);

          /*  if(resultado == "win"){
                 Drawable drawable = getResources().getDrawable(R.drawable.green);
                imageView.setImageDrawable(drawable);

            }else
            if(resultado == "loss") {
                Drawable drawable = getResources().getDrawable(R.drawable.red);
                imageView.setImageDrawable(drawable);

            }*/

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

}
