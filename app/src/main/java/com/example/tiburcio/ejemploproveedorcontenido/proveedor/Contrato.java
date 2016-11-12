package com.example.tiburcio.ejemploproveedorcontenido.proveedor;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contrato {

    public static final String AUTHORITY = "com.example.tiburcio.ejemploproveedorcontenido.proveedor.ProveedorDeContenido";

    public static final class Ciclo implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://"+AUTHORITY+"/Ciclo");

        // Table column
        public static final String NOMBRE = "Nombre";
        public static final String ABREVIATURA = "Abreviatura";
    }
}
