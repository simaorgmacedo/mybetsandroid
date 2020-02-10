package com.simaorossy.mybets;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.simaorossy.mybets.database.DadosOpenHelper;
import com.simaorossy.mybets.dominio.entidade.Bets;
import com.simaorossy.mybets.dominio.repositorio.BetsRepositorio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.List;

public class ActMain extends AppCompatActivity {

    private FloatingActionButton fab;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private ConstraintLayout layoutContent_act_main;
    private RecyclerView recyclerView;
    private BetAdapter betAdapter;
    private BetsRepositorio betsRepositorio;
    private Bets b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        b = new Bets();


        fab = findViewById(R.id.FabCadastrar);


        layoutContent_act_main = findViewById(R.id.layoutContent_act_main);
        recyclerView = findViewById(R.id.RcvBets);

        criarConexao();

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        betsRepositorio = new BetsRepositorio(conexao);

        List<Bets> dados = betsRepositorio.buscarTodos();

        betAdapter= new BetAdapter(dados);

        recyclerView.setAdapter(betAdapter);






    }


    private void criarConexao(){


        try{

            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            Snackbar.make(layoutContent_act_main,"banco de dados criado com sucesso", Snackbar.LENGTH_LONG).setAction("OK",null).show();



        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.show();

        }
    }

    public void cadastrar (View view){
        Intent it = new Intent(ActMain.this, ActCadastrarBets.class);
        startActivityForResult(it, 0);
    }




    // ao volta para tela inicial lista novamente o recivlerviewer
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        List<Bets> dados = betsRepositorio.buscarTodos();
        betAdapter = new BetAdapter(dados);
        recyclerView.setAdapter(betAdapter);

    }
}
