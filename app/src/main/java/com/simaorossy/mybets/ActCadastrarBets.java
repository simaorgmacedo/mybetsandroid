package com.simaorossy.mybets;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class ActCadastrarBets extends AppCompatActivity {

    private CheckBox cbResultado;
    private EditText edtMercado;
    private EditText edtAposta;
    private EditText edtRetorno;
    private EditText edtOdd;
    private EditText edtData;
    private EditText edtDescricao;
    public  String resultado = "loss";
    private Bets bets;
    private BetsRepositorio betsRepositorio;
    private ConstraintLayout layoutAct_cadastrar_bets;
    private DadosOpenHelper dadosOpenHelper;
    private SQLiteDatabase conexao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastrar_bets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bets = new Bets();
        cbResultado = findViewById(R.id.cbWin);
        edtMercado  = findViewById(R.id.edtMercado);
        edtAposta   = findViewById(R.id.edtAposta);
        edtRetorno  = findViewById(R.id.edtRetorno);
        edtOdd      = findViewById(R.id.edtOdd);
        edtData     = findViewById(R.id.edtData);
        edtDescricao= findViewById(R.id.edtDescricao);

        layoutAct_cadastrar_bets = findViewById(R.id.layoutContent_act_cadastrar_bets);

        criarConexao();
        verificaParametro();

    }



    private void verificaParametro(){
        Bundle bundle = getIntent().getExtras();



        if ((bundle != null) && (bundle.containsKey("BETS"))){

            bets = (Bets) bundle.getSerializable("BETS");


           // boolean checked = ((CheckBox)view).isChecked();
           // switch (view.getId()){
            //    case R.id.cbWin:
            //        if(checked){
             //           resultado = "win";

             //       }else{
             //           resultado = "loss";

             //       }
           // }



            if(bets.resultado.equals("win")){
                cbResultado.setChecked(true);
                resultado = "win";
            }else {
                cbResultado.setChecked(false);
            }


            edtMercado.setText(bets.mercado);
            edtAposta.setText(String.valueOf(bets.aposta));
            edtRetorno.setText(String.valueOf(bets.retorno));
            edtOdd.setText(String.valueOf(bets.odd));
            edtData.setText(bets.data);
            edtDescricao.setText(bets.descricao);

        }

    }

    private void criarConexao(){


        try{

            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();


            betsRepositorio = new BetsRepositorio(conexao);


        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("ERRO");
            dlg.setMessage(ex.getMessage());
            dlg.show();

        }
    }




    //metodo para verificar se o checkbox esta ou nao selecionado
    public void onCheckboxClicked(View view){


        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.cbWin:
                if(checked){
                    resultado = "win";

                }else{
                    resultado = "loss";

                }
        }

    }

    public void confirma(){
        if(validaCampo() == false){
            // AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            // dlg.setTitle("ERRO");
            // dlg.setMessage("RESULTADO " + bets.resultado +", MERCADO " + bets.mercado+", APOSTA " + bets.aposta+", RETORNO "
            //      + bets.retorno+", ODD " + bets.odd+", DATA " + bets.data+", DESCRICAO " +bets.descricao);
            //       dlg.show();
            try{

                if(bets.codigo == 0) {
                    betsRepositorio.inserir(bets);
                    finish();
                }else{
                    betsRepositorio.alterar(bets);
                    finish();
                }


            }catch (SQLException ex){
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                dlg.setTitle("ERRO");
                dlg.setMessage(ex.getMessage());
                dlg.show();
            }


        }
    }


    // validação dos campos
    //se estiver tudo ok vai retornar false
    public boolean validaCampo(){
        boolean res = false;

        String apostaa   = edtAposta.getText().toString();
        String retornoo  = edtRetorno.getText().toString();
        String oddd       = edtOdd.getText().toString();

        String mercado   = edtMercado.getText().toString();
        double aposta    = 0;
        double retorno   = 0;
        double odd       = 0;
        String data      = edtData.getText().toString();
        String descricao = edtDescricao.getText().toString();

        if(campoVazio(apostaa)){
            aposta = 0;
        }else{
            aposta = Double.parseDouble(apostaa);
        }

        if (campoVazio(retornoo)){
            retorno = 0;
        }else{
            retorno = Double.parseDouble(retornoo);
        }

        if(campoVazio(oddd)){
            odd = 0;
        }else{
            odd = Double.parseDouble(oddd);
        }

        bets.resultado = resultado;
        bets.mercado   = mercado;
        bets.aposta    = aposta;
        bets.retorno   = retorno;
        bets.odd       = odd;
        bets.data      = data;
        bets.descricao = descricao;



        AlertDialog.Builder dlg = new AlertDialog.Builder(this);

        if (campoVazio(mercado)){
            res = true;
            edtMercado.requestFocus();
            dlg.setTitle("Aviso");
            dlg.setMessage("Mercado esta incorreto");
            dlg.setNeutralButton("OK",null);
            dlg.show();
        }else
            if(aposta == 0 ){
                res = true;
                edtAposta.requestFocus();
                dlg.setTitle("Aviso");
                dlg.setMessage("Aposta esta incorreta");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }else
                if( odd == 0 ){
                    res = true;
                    edtOdd.requestFocus();
                    dlg.setTitle("Aviso");
                    dlg.setMessage("ODD esta incorreta");
                    dlg.setNeutralButton("OK",null);
                    dlg.show();

                }else
                    if(campoVazio(data)){
                        res = true;
                        edtData.requestFocus();
                        dlg.setTitle("Aviso");
                        dlg.setMessage("Data esta incorreta");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    }




        return res;
    }




    //metodo para colocar o menu cadastrar la em cima
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cadastrar_bets, menu);

        return super.onCreateOptionsMenu(menu);

    }





    //metodo para definir o que sera feito ao clicar no menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case R.id.menu_cadastrar:
                confirma();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    // metodo para retorno se os campos estao vazios ou nao
    //sera usado no validacampos
    public boolean campoVazio(String valor){

        if(TextUtils.isEmpty(valor) || valor.trim().isEmpty()){
            return true;
        }else{
            return false;
        }



    }

}
