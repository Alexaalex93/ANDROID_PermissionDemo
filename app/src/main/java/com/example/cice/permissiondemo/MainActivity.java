package com.example.cice.permissiondemo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    /**
     * checkSelfPermission()
     * PackageManager.PERMISSION_GRANTED
     * PackageManager.PERMISSION_DENIED
     * <p>
     * requestPermissions()
     * -Referencia al Activity
     * -Identificador del permiso
     * -Request Code
     * <p>
     * onRequestPermissionResult()
     */

    private static String TAG = "PermissionDemo";
    private static final int RECORD_REQUEST_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "No puedes realizar grabaciones de audio");

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                // si tendria que preguntar o no una vez lo rechazas

                //Creamos el AlertDialog en una vez se ha rechazado para poder pedir el acceso siempre que queramos
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Esta app requiere permiso para acceder al micro").setTitle("Permiso Requerido");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.i(TAG, "Apretado");
                        makeRequest();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                makeRequest();
            }
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RECORD_REQUEST_CODE:
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    Log.i(TAG, "Permiso denegado por el usuario");
                else
                    Log.i(TAG, "Permiso concedido por el usuario");
                return;

        }
    }
}
