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

import androidx.annotation.NonNull;
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
            resultado = bets.resultado;
            if(resultado.equals("win")){
                //Snackbar.make(actVisualizar,"win", Snackbar.LENGTH_LONG).setAction("OK",null).show();
                //Drawable drawable = getResources().getDrawable(R.drawable.green);
                //imageView.setImageDrawable(drawable);
                actVisualizar.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

            }else
            if(resultado.equals("loss")) {
                //Snackbar.make(actVisualizar,"loss", Snackbar.LENGTH_LONG).setAction("OK",null).show();
                //Drawable drawable = getResources().getDrawable(R.drawable.red);
                //imageView.setImageDrawable(drawable);
                actVisualizar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

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
                Toast.makeText(this, "action editar",Toast.LENGTH_LONG).show();
                break;

            case R.id.actionExcluir:
                Toast.makeText(this, "action excluir",Toast.LENGTH_LONG).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }




}
