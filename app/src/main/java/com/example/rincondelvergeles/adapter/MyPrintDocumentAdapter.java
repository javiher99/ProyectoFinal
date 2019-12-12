package com.example.rincondelvergeles.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;

import com.example.rincondelvergeles.model.Comanda;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MyPrintDocumentAdapter extends PrintDocumentAdapter {

    Context context;
    private int pageHeight;
    private int pageWidth;
    private PrintedPdfDocument myPdfDocument;
    public int totalPages;
    private int resto = 0;
    private int inicio = 0;
    private int fin = 12;
    private Bitmap bm;
    private boolean ultimaPagina = false;
    private boolean header = false;
    private boolean siHeader = false;
    List<Comanda> comandaList;
    private String totalFactura;
    private String hora;

    public MyPrintDocumentAdapter(Context context,
                                  List<Comanda> comandaList,
                                  Bitmap bm,
                                  String totalFactura,
                                  String hora) {
        this.context = context;
        this.comandaList = comandaList;
        this.bm = bm;
        this.totalFactura = totalFactura;
        this.hora = hora;
        this.totalPages = contarNumPaginas();
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        myPdfDocument = new PrintedPdfDocument(context, newAttributes);
        pageHeight = newAttributes.getMediaSize().getHeightMils() / 1000 * 72;
        pageWidth = newAttributes.getMediaSize().getWidthMils() / 1000 * 72;

        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }

        if (totalPages > 0) {
            PrintDocumentInfo.Builder builder = new
                    PrintDocumentInfo
                            .Builder("factura_impresion.pdf")
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(totalPages);
            PrintDocumentInfo info = builder.build();
            callback.onLayoutFinished(info, true);
        } else {
            callback.onLayoutFailed("Page count is zero.");
        }
    }

    private int contarNumPaginas(){
        int resul = 0;
        int cantidadComandas = comandaList.size();
        int cociente = 0;
        int resto = 0;

        cociente = cantidadComandas / 12;
        resto = cantidadComandas % 12;

        if(resto == 0){
            resul = cociente;
        } else {
            resul = cociente + 1;
        }

        return resul;
    }

    private boolean pageInRange(PageRange[] pageRanges, int page) {
        for (int i = 0; i < pageRanges.length; i++) {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }

    @Override
    public void onWrite(final PageRange[] pageRanges,
                        final ParcelFileDescriptor destination,
                        final CancellationSignal cancellationSignal,
                        final WriteResultCallback callback) {

        for (int i = 0; i < totalPages; i++) {
            if (pageInRange(pageRanges, i)) {
                PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i).create();

                PdfDocument.Page page = myPdfDocument.startPage(newPage);

                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    myPdfDocument.close();
                    myPdfDocument = null;
                    return;
                }
                drawPage(page, i, totalPages);
                myPdfDocument.finishPage(page);
            }
        }

        try {
            myPdfDocument.writeTo(new FileOutputStream(destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            myPdfDocument.close();
            myPdfDocument = null;
        }

        callback.onWriteFinished(pageRanges);

    }

    private void drawPage(PdfDocument.Page page, int pagenumber, int totalPages) {
        Canvas canvas = page.getCanvas();

        pagenumber++;

        int titleBaseLine = 80;

        int leftMargin = 60;
        int description = 175;
        int leftMidMargin = 100;

        int rightMargin = 500;
        int rightMidMargin = 410;
        int rightMarginHora = 485;

        int separadorEstandar = 100;

        int imagenLeft = 20;

        Paint title = new Paint();

        if (header == false) {

            canvas.drawBitmap(bm, imagenLeft, 0, null);

            title.setTextSize(33);
            canvas.drawText("Pedidos Realizados", leftMargin, separadorEstandar + 40, title);

            title.setTextSize(20);
            canvas.drawText("────────────────────────────────────", leftMargin, separadorEstandar + 60, title);

            title.setTextSize(23);
            canvas.drawText("Unid.", leftMargin, separadorEstandar + 91, title);
            title.setTextSize(23);
            canvas.drawText("Descripcion", description, separadorEstandar + 91, title);
            title.setTextSize(23);
            canvas.drawText("", rightMidMargin, separadorEstandar + 91, title);
            title.setTextSize(23);
            canvas.drawText("Total", rightMargin, separadorEstandar + 91, title);

            title.setTextSize(20);
            canvas.drawText("────────────────────────────────────", leftMargin, separadorEstandar + 115, title);

            header = true;
        }

        int comanda = 215;
        int separador = 30;
        for (int i = inicio; i < fin && i < comandaList.size(); i++) {
            comanda = comanda + separador;
            title.setTextSize(23);
            canvas.drawText("" + comandaList.get(i).getUnidades(), leftMargin, comanda, title);
            title.setTextSize(23);
            canvas.drawText(comandaList.get(i).getNombreProducto(), description, comanda, title);
            title.setTextSize(23);
            canvas.drawText("", rightMidMargin, comanda, title);
            title.setTextSize(23);
            canvas.drawText("" + comandaList.get(i).getPrecio(), rightMargin, comanda, title);
        }
        inicio += 12;
        fin += 12;

        separador = 24;
        comanda = comanda + separador;

        if (comanda >= 707) {
            totalPages++;
        }

        if (pagenumber == totalPages) {
            ultimaPagina = true;
        }



        if (ultimaPagina) {
            title.setTextSize(20);
            canvas.drawText("────────────────────────────────────", leftMargin, comanda, title);

            separador = 30;
            comanda = comanda + separador;

            title.setTextSize(33);
            canvas.drawText("TOTAL EUROS", leftMargin, comanda, title);

            title.setTextSize(33);
            canvas.drawText(totalFactura, rightMargin - 40, comanda, title);

            separador = 24;
            comanda = comanda + separador;

            title.setTextSize(20);
            canvas.drawText("────────────────────────────────────", leftMargin, comanda, title);

            separador = 30;
            comanda = comanda + separador;

            title.setTextSize(21);
            canvas.drawText("Hora atendido", leftMargin, comanda, title);
            title.setTextSize(21);
            canvas.drawText(hora, rightMarginHora, comanda, title);

            separador = 24;
            comanda = comanda + separador;

            title.setTextSize(20);
            canvas.drawText("────────────────────────────────────", leftMargin, comanda, title);


            separador = 30;
            comanda = comanda + separador;

            title.setTextSize(21);
            canvas.drawText("Email", leftMargin, comanda, title);
            title.setTextSize(21);
            canvas.drawText("rincondelvergeles@gmail.com", rightMargin - 210, comanda, title);

            comanda = comanda + separador;

            title.setTextSize(21);
            canvas.drawText("Telefono", leftMargin, comanda, title);
            title.setTextSize(21);
            canvas.drawText("958764567", rightMargin - 40, comanda, title);
        }
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
