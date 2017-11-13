package com.example.tiago.testewidgets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;

    private CheckBox pchkExibir;
    private LinearLayout plytConteudo;
    private LinearLayout plytPrincipal;

    private Button pbtnAdicionar;
    private Button pbtnRemover;
    private Button pbtnExibir;

    private int pintQtdeViews = 0;
    private List<String> plstTextos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InicializarComponentes();
    }

    private void InicializarComponentes() {
        pchkExibir = (CheckBox) findViewById(R.id.checkBox);
        plytConteudo = (LinearLayout) findViewById(R.id.lytConteudo);
        plytPrincipal = (LinearLayout) findViewById(R.id.lytPrincipal);

        pbtnAdicionar = (Button) findViewById(R.id.btnAdd);
        pbtnRemover = (Button) findViewById(R.id.btnRemove);
        pbtnExibir = (Button) findViewById(R.id.btnShow);

        pchkExibir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ExibirOcultarView(plytConteudo);
            }
        });

        //pchkExibir.setChecked(true);

        pbtnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AdicionarLayoutImagem();
                CapturarFoto();

            }
        });

        pbtnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoverLayoutImagem();
            }
        });

        pbtnExibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plstTextos.clear();
                ExibirTextos(RetornarTextos(plytPrincipal));
            }
        });
    }

    private void AdicionarLayoutImagem(Bitmap bmpFoto) {

        pintQtdeViews++;

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout lytPhoto = (LinearLayout) inflater.inflate(R.layout.photo, null, false);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(15, 15, 15, 15);
        lytPhoto.setLayoutParams(lp);

        //((ImageView) lytPhoto.getChildAt(0)).setImageDrawable(getDrawable(R.drawable.image_placeholder));
        ((ImageView) lytPhoto.getChildAt(0)).setImageBitmap(bmpFoto);
        lytPhoto.getChildAt(0).setId(pintQtdeViews);

        ((EditText) lytPhoto.getChildAt(2)).setText("Foto número = " + pintQtdeViews);

        plytPrincipal.addView(lytPhoto);
    }

    private void AdicionarLayoutImagem2(Bitmap bmpFoto) {

        pintQtdeViews++;

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout lytPhoto = (LinearLayout) inflater.inflate(R.layout.photo2, null, false);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(15, 15, 15, 15);
        lytPhoto.setLayoutParams(lp);

        ((Button) lytPhoto.findViewById(R.id.btnX)).setOnClickListener(pOnClickListener);

        ((ImageView) lytPhoto.findViewById(R.id.imageView)).setImageBitmap(bmpFoto);
        lytPhoto.findViewById(R.id.imageView);

        ((EditText) lytPhoto.findViewById(R.id.editText)).setText("Foto número = " + pintQtdeViews);

        plytPrincipal.addView(lytPhoto);
    }

    private void RemoverLayoutImagem() {

        pintQtdeViews--;

        int intQteViews = plytPrincipal.getChildCount();

        if (intQteViews == 0) {
            return;
        }

        plytPrincipal.removeViewAt(intQteViews - 1);
    }

    private View.OnClickListener pOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            plytPrincipal.removeView((ViewGroup) v.getParent().getParent());//.removeView((ViewGroup)v.getParent());
        }
    };

    private void ExibirOcultarView(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

     private List<String> RetornarTextos(ViewGroup viewGroup) {

        for (int i = 0; i < viewGroup.getChildCount(); i++){
            View child = viewGroup.getChildAt(i);
            if (child instanceof ViewGroup){
                RetornarTextos((ViewGroup) child);
            } else if (child instanceof EditText){
                plstTextos.add(((EditText) child).getText().toString());
            } else if (child instanceof ImageView) {
                //plstTextos.add(((ImageView) child).getId() + "\n");
            }
        }

        /*for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View view = viewGroup.getChildAt(i);

            if (view instanceof EditText) {
                plstTextos.add(((EditText) view).getText().toString() + "\n");
            }
        }*/

        return plstTextos;
    }

    private void ExibirTextos(List<String> lstTextos) {

        String strTextoToast = "";

        for (String strTexto : lstTextos) {
            strTextoToast += strTexto + "\n";
        }

        Toast.makeText(this, strTextoToast, Toast.LENGTH_LONG).show();
    }

    public void CapturarFoto(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            AdicionarLayoutImagem2(photo);
            //imageView.setImageBitmap(photo);
        }
    }
}
