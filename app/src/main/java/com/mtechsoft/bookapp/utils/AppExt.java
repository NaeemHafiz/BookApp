package com.mtechsoft.bookapp.utils;

import android.content.Context;
import android.widget.ScrollView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AppExt {
    private static final String PDF_FILE_NAME = "button.pdf";

    public static String getFilePath(Context context) {
        File f = new File(context.getCacheDir() + "/" + PDF_FILE_NAME);
        if (!f.exists()) try {
            InputStream is = context.getAssets().open(PDF_FILE_NAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(buffer);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return f.getPath();
    }

    public static  void showPDFFile(PDFView pdfView, OnPageChangeListener listener) {
        pdfView.fromAsset("button.pdf")
                .defaultPage(0)
                .enableSwipe(true) // allows to block changing pages using swipe
                .onPageChange(listener)
                .swipeHorizontal(false)
                .spacing(10) // in dp
                .enableAnnotationRendering(true)
                .load();
    }
}
