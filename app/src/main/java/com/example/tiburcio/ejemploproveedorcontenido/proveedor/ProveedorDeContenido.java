package com.example.tiburcio.ejemploproveedorcontenido.proveedor;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.util.ArrayList;

public class ProveedorDeContenido extends ContentProvider {
    //private static final String LOGTAG = "Tiburcio - ProveedorDeContenido";

    private static final int CICLO_ONE_REG = 1;
    private static final int CICLO_ALL_REGS = 2;

    private SQLiteDatabase sqlDB;
    public DatabaseHelper dbHelper;
    private static final String DATABASE_NAME = "Convalidaciones.db";
    private static final int DATABASE_VERSION = 35;

    private static final String CICLO_TABLE_NAME = "Ciclo";

    // Indicates an invalid content URI
    public static final int INVALID_URI = -1;

    // Defines a helper object that matches content URIs to table-specific parameters
    private static final UriMatcher sUriMatcher;

    // Stores the MIME types served by this provider
    private static final SparseArray<String> sMimeTypes;

    /*
     * Initializes meta-data used by the content provider:
     * - UriMatcher that maps content URIs to codes
     * - MimeType array that returns the custom MIME type of a table
     */
    static {

        // Creates an object that associates content URIs with numeric codes
        sUriMatcher = new UriMatcher(0);

        /*
         * Sets up an array that maps content URIs to MIME types, via a mapping between the
         * URIs and an integer code. These are custom MIME types that apply to tables and rows
         * in this particular provider.
         */
        sMimeTypes = new SparseArray<String>();

        // Adds a URI "match" entry that maps picture URL content URIs to a numeric code

        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                CICLO_TABLE_NAME,
                CICLO_ALL_REGS);
        sUriMatcher.addURI(
                Contrato.AUTHORITY,
                CICLO_TABLE_NAME + "/#",
                CICLO_ONE_REG);

        // Specifies a custom MIME type for the picture URL table

        sMimeTypes.put(
                CICLO_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTHORITY + "." + CICLO_TABLE_NAME);
        sMimeTypes.put(
                CICLO_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTHORITY + "." + CICLO_TABLE_NAME);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);

            //if (!db.isReadOnly()){
            //Habilitamos la integridad referencial
            db.execSQL("PRAGMA foreign_keys=ON;");
            //}
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create table to store

            db.execSQL("Create table "
                            + CICLO_TABLE_NAME
                            + "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, "
                            + Contrato.Ciclo.NOMBRE + " TEXT , "
                            + Contrato.Ciclo.ABREVIATURA + " TEXT ); "
            );

            inicializarDatos(db);

        }

        void inicializarDatos(SQLiteDatabase db){

            db.execSQL("INSERT INTO " + CICLO_TABLE_NAME + " (" +  Contrato.Ciclo._ID + "," + Contrato.Ciclo.NOMBRE + "," + Contrato.Ciclo.ABREVIATURA + ") " +
                    "VALUES (1,'Administración de Sistemas Informáticos en Red','ASIR')");
            db.execSQL("INSERT INTO " + CICLO_TABLE_NAME + " (" +  Contrato.Ciclo._ID + "," + Contrato.Ciclo.NOMBRE + "," + Contrato.Ciclo.ABREVIATURA + ") " +
                    "VALUES (2,'Desarrollo de Aplicaciones Web','DAW')");
            db.execSQL("INSERT INTO " + CICLO_TABLE_NAME + " (" +  Contrato.Ciclo._ID + "," + Contrato.Ciclo.NOMBRE + "," + Contrato.Ciclo.ABREVIATURA + ") " +
                    "VALUES (3,'Desarrollo de Aplicaciones Multiplataforma','DAM')");
            db.execSQL("INSERT INTO " + CICLO_TABLE_NAME + " (" +  Contrato.Ciclo._ID + "," + Contrato.Ciclo.NOMBRE + "," + Contrato.Ciclo.ABREVIATURA + ") " +
                    "VALUES (4,'Sistemas Microinformáticos y Redes','SMR')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + CICLO_TABLE_NAME);

            onCreate(db);
        }

    }

    public ProveedorDeContenido() {
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return (dbHelper == null) ? false : true;
    }

    public void resetDatabase() {
        dbHelper.close();
        dbHelper = new DatabaseHelper(getContext());
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        sqlDB = dbHelper.getWritableDatabase();

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case CICLO_ALL_REGS:
                table = CICLO_TABLE_NAME;
                break;
        }

        long rowId = sqlDB.insert(table, "", values);

        if (rowId > 0) {
            Uri rowUri = ContentUris.appendId(
                    uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case CICLO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Ciclo._ID + " = "
                        + uri.getLastPathSegment();
                table = CICLO_TABLE_NAME;
                break;
            case CICLO_ALL_REGS:
                table = CICLO_TABLE_NAME;
                break;
        }
        int rows = sqlDB.delete(table, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = null;

        switch (sUriMatcher.match(uri)) {
            case CICLO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Ciclo._ID + " = "
                        + uri.getLastPathSegment();
                qb.setTables(CICLO_TABLE_NAME);
                break;
            case CICLO_ALL_REGS:
                if (TextUtils.isEmpty(sortOrder)) sortOrder =
                        Contrato.Ciclo._ID + " ASC";
                qb.setTables(CICLO_TABLE_NAME);
                break;
        }

        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs, null, null,
                        sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        sqlDB = dbHelper.getWritableDatabase();
        // insert record in user table and get the row number of recently inserted record

        String table = "";
        switch (sUriMatcher.match(uri)) {
            case CICLO_ONE_REG:
                if (null == selection) selection = "";
                selection += Contrato.Ciclo._ID + " = "
                        + uri.getLastPathSegment();
                table = CICLO_TABLE_NAME;
                break;
            case CICLO_ALL_REGS:
                table = CICLO_TABLE_NAME;
                break;
        }

        int rows = sqlDB.update(table, values, selection, selectionArgs);
        if (rows > 0) {
            getContext().getContentResolver().notifyChange(uri, null);

            return rows;
        }
        throw new SQLException("Failed to update row into " + uri);
    }
}
