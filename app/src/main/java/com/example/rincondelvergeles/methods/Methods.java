package com.example.rincondelvergeles.methods;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.telecom.Call;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;

import com.example.rincondelvergeles.R;

public class Methods {

    public static NumberPicker numberPicker;

    public static AlertDialog.Builder cargandoMostrar(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(context.getResources().getString(R.string.working))
                .setMessage(message);
        return builder;
    }

    public static void animShake (Context context, final View view) {
        Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shaking);
        view.startAnimation(animShake);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.clearAnimation();
            }
        }, 500);
    }

    public static void animShakeSides (Context context, final View view) {
        Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shaking_sides);
        view.startAnimation(animShake);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.clearAnimation();
            }
        }, 1000);
    }

    public static AlertDialog.Builder numberPickerDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        numberPicker = new NumberPicker(context);
        numberPicker.setMaxValue(10);
        numberPicker.setMinValue(1);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.unidades));
        builder.setMessage(context.getResources().getString(R.string.selectUnidades));
        builder.setPositiveButton(context.getResources().getString(R.string.ok), onClickListener);
        builder.setNegativeButton(context.getResources().getString(R.string.cancelar), null);
        builder.setView(numberPicker);
        return builder;
    }

    public static Bitmap redimensionarImagen (Bitmap bm){

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) 550) / width;
        float scaleHeight = ((float) 150) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bm,0,0, width, height, matrix,false);
    }
}
