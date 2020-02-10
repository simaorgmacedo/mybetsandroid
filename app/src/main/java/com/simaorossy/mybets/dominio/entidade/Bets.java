package com.simaorossy.mybets.dominio.entidade;

import java.io.Serializable;

public class Bets implements Serializable {

    public int    codigo;
    public String resultado;
    public String mercado;
    public double aposta;
    public double retorno;
    public double odd;
    public String data;
    public String descricao;

    public Bets(){
        codigo = 0;
    }

}
