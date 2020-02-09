package com.simaorossy.mybets.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simaorossy.mybets.dominio.entidade.Bets;

import java.util.ArrayList;
import java.util.List;

public class BetsRepositorio {
    private SQLiteDatabase conexão;

    public BetsRepositorio(SQLiteDatabase conexão){
        this.conexão = conexão;
    }

    public void inserir (Bets bets){
        ContentValues contentValues = new ContentValues();
        contentValues.put("RESULTADO", bets.resultado);
        contentValues.put("MERCADO", bets.mercado);
        contentValues.put("APOSTA", bets.aposta);
        contentValues.put("RETORNO", bets.retorno);
        contentValues.put("ODD", bets.odd);
        contentValues.put("DATA", bets.data);
        contentValues.put("DESCRICAO", bets.descricao);

        conexão.insertOrThrow("BETS", null, contentValues);
    }


    public void excluir(int codigo){
        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        conexão.delete("BETS", "CODIGO = ?", parametros);

    }


    public void alterar( Bets bets ){
        ContentValues contentValues = new ContentValues();
        contentValues.put("RESULTADO", bets.resultado);
        contentValues.put("MERCADO", bets.mercado);
        contentValues.put("APOSTA", bets.aposta);
        contentValues.put("RETORNO", bets.retorno);
        contentValues.put("ODD", bets.odd);
        contentValues.put("DATA", bets.data);
        contentValues.put("DESCRICAO", bets.descricao);



        String[] parametros = new String[1];
        parametros[0] = String.valueOf(bets.codigo);

        conexão.update("BETS", contentValues, "CODIGO = ?", parametros);

    }


    public List<Bets> buscarTodos(){

        List<Bets> bets = new ArrayList<Bets>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, RESULTADO, MERCADO, APOSTA, RETORNO, ODD, DATA, DESCRICAO FROM BETS");

        Cursor resultado = conexão.rawQuery(sql.toString(), null);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();

            do{
                Bets bet = new Bets();

                bet.codigo    = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                bet.resultado = resultado.getString(resultado.getColumnIndexOrThrow("RESULTADO"));
                bet.mercado   = resultado.getString(resultado.getColumnIndexOrThrow("MERCADO"));
                bet.aposta    = resultado.getDouble(resultado.getColumnIndexOrThrow("APOSTA"));
                bet.retorno   = resultado.getDouble(resultado.getColumnIndexOrThrow("RETORNO"));
                bet.odd       = resultado.getDouble(resultado.getColumnIndexOrThrow("ODD"));
                bet.data      = resultado.getString(resultado.getColumnIndexOrThrow("DATA"));
                bet.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

                bets.add(bet);
            }while (resultado.moveToNext());

        }
        return bets;
    }



    public Bets buscarBet(int codigo){
        Bets bet = new Bets();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, RESULTADO, MERCADO, APOSTA, RETORNO, ODD, DATA, DESCRICAO FROM BETS WHERE CODIGO = ?");

        String[] parametros = new String[1];
        parametros[0] = String.valueOf(codigo);

        Cursor resultado = conexão.rawQuery(sql.toString(),parametros);

        if(resultado.getCount() > 0){
            resultado.moveToFirst();

            bet.codigo    = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
            bet.resultado = resultado.getString(resultado.getColumnIndexOrThrow("RESULTADO"));
            bet.mercado   = resultado.getString(resultado.getColumnIndexOrThrow("MERCADO"));
            bet.aposta    = resultado.getDouble(resultado.getColumnIndexOrThrow("APOSTA"));
            bet.retorno   = resultado.getDouble(resultado.getColumnIndexOrThrow("RETORNO"));
            bet.odd       = resultado.getDouble(resultado.getColumnIndexOrThrow("ODD"));
            bet.data      = resultado.getString(resultado.getColumnIndexOrThrow("DATA"));
            bet.descricao = resultado.getString(resultado.getColumnIndexOrThrow("DESCRICAO"));

            return bet;
        }



        return null;
    }




}
