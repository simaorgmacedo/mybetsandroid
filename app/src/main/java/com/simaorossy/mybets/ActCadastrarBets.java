package com.simaorossy.mybets;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.simaorossy.mybets.dominio.entidade.Bets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    Bets bets = new Bets();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastrar_bets);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cbResultado = findViewById(R.id.cbWin);
        edtMercado  = findViewById(R.id.edtMercado);
        edtAposta   = findViewById(R.id.edtAposta);
        edtRetorno  = findViewById(R.id.edtRetorno);
        edtOdd      = findViewById(R.id.edtOdd);
        edtData     = findViewById(R.id.edtData);
        edtDescricao= findViewById(R.id.edtDescricao);

    }




    //metodo para verificar se o checkbox esta ou nao selecionado
    public void onCheckboxClicked(View view){


        boolean checked = ((CheckBox)view).isChecked();
        switch (view.getId()){
            case R.id.cbWin:
                if(checked){
                    resultado = "win";
                    Toast.makeText(this,resultado,Toast.LENGTH_LONG).show();
                }else{
                    resultado = "loss";
                    Toast.makeText(this,resultado,Toast.LENGTH_LONG).show();
                }
        }

    }

    public void confirma(){
        if(validaCampo() == false){

            Toast.makeText(this, bets.resultado + bets.mercado + bets.aposta + bets.retorno
                    + bets.odd + bets.data + bets.descricao, Toast.LENGTH_LONG).show();

        }
    }


    // validação dos campos
    //se estiver tudo ok vai retornar false
    public boolean validaCampo(){
        boolean res = false;


        String mercado   = edtMercado.getText().toString();
        double aposta    = Double.parseDouble(edtAposta.getText().toString());
        double retorno   = Double.parseDouble(edtRetorno.getText().toString());
        double odd       = Double.parseDouble(edtOdd.getText().toString());
        String data      = edtData.getText().toString();
        String descricao = edtDescricao.getText().toString();

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
            if(campoVazio(String.valueOf(aposta)) ){
                res = true;
                edtAposta.requestFocus();
                dlg.setTitle("Aviso");
                dlg.setMessage("Aposta esta incorreta");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }else
                if(campoVazio(String.valueOf(odd)) ){
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
