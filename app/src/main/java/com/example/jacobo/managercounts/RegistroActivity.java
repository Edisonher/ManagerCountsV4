package com.example.jacobo.managercounts;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jacobo.managercounts.Objetos.Clientes;
import com.example.jacobo.managercounts.Objetos.FirebaseReferences;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;


public class RegistroActivity extends AppCompatActivity {

    EditText eNombre,eApellido,eTelefono,eDireccion,eBarrio,eCedula, eRClave1, eRClave2, eRCorreo;
    Button bRegistrarse, bCancelar;
    ImageButton imagenperfil;

    String cedula,nombre,apellido,direccion,barrio, correo, telefono;

    Clientes cliente;
    DatabaseReference myRef;

    private static final int PICK_IMAGE_REQUEST = 234;
    public static final String FB_STORAGE_PATH = "image/";
    private Uri filepath;
    private StorageReference storageReference;


    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        storageReference = FirebaseStorage.getInstance().getReference();

        bRegistrarse = (Button) findViewById(R.id.bRegistrarse);
        bCancelar = (Button) findViewById(R.id.bCancelar);
        eNombre = (EditText) findViewById(R.id.eRNombres);
        eApellido = (EditText) findViewById(R.id.eRApellidos);
        eTelefono = (EditText) findViewById(R.id.eRTelefono);
        eDireccion = (EditText) findViewById(R.id.eRDireccion);
        eBarrio = (EditText) findViewById(R.id.eRBarrio);
        eCedula = (EditText) findViewById(R.id.eRCedula);
        imagenperfil = (ImageButton) findViewById(R.id.imagenperfil);




        imagenperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowFileChooser();
            }
        });

    }

    private void ShowFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an Image"),PICK_IMAGE_REQUEST);

    }
    private void uploadFile(){

        if(filepath != null ) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference riversRef = storageReference.child(FB_STORAGE_PATH + System.currentTimeMillis() + "." + getImageExt(filepath));
            riversRef.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                            cedula = eCedula.getText().toString();
                            nombre = eNombre.getText().toString();
                            apellido = eApellido.getText().toString();
                            telefono = eTelefono.getText().toString();
                            direccion = eDireccion.getText().toString();
                            barrio = eBarrio.getText().toString();

                            Clientes cliente = new Clientes(cedula,nombre,apellido,telefono,direccion,barrio,"","",taskSnapshot.getDownloadUrl().toString());
                            //Clientes cliente = new Clientes(apellido,barrio,cedula,"",direccion,"",nombre,telefono);
                            myRef = database.getReference("clientes").child(cedula);
                            myRef.setValue(cliente);

                            //String uploadId = mDatabaseRef.push().getKey();
                            //mDatabaseRef.child(uploadId).setValue(taskSnapshot.getDownloadUrl().toString());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                            // ...
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage(((int) progress) +"% Uploaded..." );
                        }
                    });
            ;

        }else{


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filepath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath) ;
                imagenperfil.setImageBitmap(bitmap);
                //icono.setImageBitmap(bitmap);
                //uploadFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getImageExt(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void click(View v) {
        Intent i = new Intent();
        switch (v.getId()) {

            case R.id.bRegistrarse:
                /*cedula = eCedula.getText().toString();
                nombre = eNombre.getText().toString();
                apellido = eApellido.getText().toString();
                telefono = eTelefono.getText().toString();
                direccion = eDireccion.getText().toString();
                barrio = eBarrio.getText().toString();

                Clientes cliente = new Clientes(cedula,nombre,apellido,telefono,direccion,barrio,"","","");
                //Clientes cliente = new Clientes(apellido,barrio,cedula,"",direccion,"",nombre,telefono);
                myRef = database.getReference("clientes").child(cedula);
                myRef.setValue(cliente);*/

                uploadFile();

                //setResult(RESULT_OK, i);

                //Intent intent = new Intent(this, DrawerVendActivity.class);
                //startActivity(intent);
                //finish();
                //finish();
                break;

            case R.id.bCancelar:
                setResult(RESULT_CANCELED, i);
                finish();
                break;
        }
    }
}
