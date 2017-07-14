package it.cammino.catechisti.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CatechistiDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "CatechistiDB";
    private static final int DB_VERSION = 1;

    public static final String COMUNITA_TABLE = "COMUNITA";
    public static final String PASSAGGI_TABLE = "PASSAGGI";
    public static final String DZ_PASSAGGI_TABLE = "DZ_PASSAGGI";


    public CatechistiDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creazione della tabella
        String sql = "CREATE TABLE IF NOT EXISTS " + COMUNITA_TABLE + " (";
        sql += "_id INTEGER PRIMARY KEY,";
        sql += "DIOCESI TEXT ,";
        sql += "ORDINE INTEGER NOT NULL,";
        sql += "ANNI_CAMMINO INTEGER NOT NULL,";
        sql += "PARROCCHIA TEXT,";
        sql += "RESPONSABILE TEXT,";
        sql += "NOTE INTEGER NOT NULL";
        sql += ");";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS CONTATTI (";
        sql += "_id INTEGER PRIMARY KEY,";
        sql += "ID_COMUNITA INTEGER NOT NULL,";
        sql += "CONTATTO TEXT NOT NULL";
        sql += "TIPO_CONTATTO INTEGER NOT NULL,";
        sql += ");";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + PASSAGGI_TABLE + " (";
        sql += "_id INTEGER PRIMARY KEY,";
        sql += "ID_COMUNITA INTEGER NOT NULL,";
        sql += "COD_PASSAGGIO TEXT NOT NULL,";
        sql += "DATA_PASSAGGIO DATE NOT NULL";
        sql += ");";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS CONVIVENZE (";
        sql += "_id INTEGER PRIMARY KEY,";
        sql += "ID_COMUNITA INTEGER NOT NULL,";
        sql += "DATA_CONVIVENZA DATE NOT NULL";
        sql += ");";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS VISITE (";
        sql += "_id INTEGER PRIMARY KEY,";
        sql += "ID_COMUNITA INTEGER NOT NULL,";
        sql += "DATA_VISITA DATE NOT NULL";
        sql += ");";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS FRATELLI (";
        sql += "_id INTEGER PRIMARY KEY,";
        sql += "ID_COMUNITA INTEGER NOT NULL,";
        sql += "NOME TEXT NOT NULL,";
        sql += "NOTE TEXT,";
        sql += "SPOSATO INTEGER NOT NULL,";
        sql += "FIGLI INTEGER NOT NULL,";
        sql += ");";
        db.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS " + DZ_PASSAGGI_TABLE + " (";
        sql += "COD_PASSAGGIO INTEGER PRIMARY KEY,";
        sql += "DESCRIZIONE TEXT NOT NULL,";
        sql += ");";
        db.execSQL(sql);

        ContentValues values = new ContentValues();
        values.put("COD_PASSAGGIO", 0);
        values.put("DESCRIZIONE", "Precatecumenato");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 1);
        values.put("DESCRIZIONE", "1° Scrutinio");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 2);
        values.put("DESCRIZIONE", "1° Schemà");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 3);
        values.put("DESCRIZIONE", "1° 2° Scrutinio (aperto)");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 4);
        values.put("DESCRIZIONE", "2° Scrutinio");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 5);
        values.put("DESCRIZIONE", "Iniziazione alla preghiera");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 6);
        values.put("DESCRIZIONE", "Traditio");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 7);
        values.put("DESCRIZIONE", "Ritraditio");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 8);
        values.put("DESCRIZIONE", "Redditio Simboli");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 9);
        values.put("DESCRIZIONE", "Padre Nostro 1a parte");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 10);
        values.put("DESCRIZIONE", "Padre Nostro 2a parte");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 11);
        values.put("DESCRIZIONE", "Padre Nostro 3a parte");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 12);
        values.put("DESCRIZIONE", "Elezione 1a parte");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 13);
        values.put("DESCRIZIONE", "Elezione 2a parte");
        db.insert("DZ_PASSAGGI", null, values);

        values = new ContentValues();
        values.put("COD_PASSAGGIO", 14);
        values.put("DESCRIZIONE", "Fine del cammino");
        db.insert("DZ_PASSAGGI", null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        reCreateDatabse(db);
    }

    private void reCreateDatabse(SQLiteDatabase db) {
        String sql = "DROP TABLE IF EXISTS DZ_PASSAGGI";
        db.execSQL(sql);
        onCreate(db);
    }

}