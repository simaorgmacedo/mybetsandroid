package com.simaorossy.mybets.database;

public class ScriptDLL {

    public static String getCreatTableBets(){
        StringBuilder sql = new StringBuilder();

        sql.append ("CREATE TABLE IF NOT EXISTS BETS ( "      );
        sql.append ("        CODIGO    INTEGER        PRIMARY KEY AUTOINCREMENT NOT NULL,"      );
        sql.append ("        RESULTADO VARCHAR (10)   NOT NULL,"      );
        sql.append ("        MERCADO VARCHAR (250)    NOT NULL,"      );
        sql.append ("        APOSTA    DOUBLE         NOT NULL,"      );
        sql.append ("        RETORNO   DOUBLE         NOT NULL,"      );
        sql.append ("        ODD       DOUBLE         NOT NULL,"      );
        sql.append ("        DATA      DATE           NOT NULL,"      );
        sql.append ("        DESCRICAO VARCHAR (2000) );"      );

        return sql.toString();
    }

}
