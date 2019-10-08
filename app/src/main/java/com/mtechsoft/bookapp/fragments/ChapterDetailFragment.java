package com.mtechsoft.bookapp.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.ScrollHandle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.mtechsoft.bookapp.SearchViewDialog;
import com.mtechsoft.bookapp.activities.MainActivity;
import com.mtechsoft.bookapp.R;
import com.mtechsoft.bookapp.utils.AppExt;

import net.gotev.speech.Speech;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ChapterDetailFragment extends BaseFragment implements View.OnClickListener, OnPageChangeListener {
    private TextView tvPageCount;
    private String parsedTxt = "";
    private PDFView pdfView;
    private ImageButton bPlay, ivSearch;
    private ImageView ivBack;
    SearchView searchView;
    Button btnQuiz;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chapter_detail, container, false);
        Toolbar toolbar = v.findViewById(R.id.ctoolbar);
        bPlay = toolbar.findViewById(R.id.bPlay);
        ivSearch = toolbar.findViewById(R.id.ivSearch);
        ivBack = toolbar.findViewById(R.id.ivBack);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        Speech.init(getActivity(), getActivity().getPackageName());
        AppExt.showPDFFile(pdfView, this);
        new ReadTextFromPDFTask().execute();
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        return builder;
    }

    private void initViews(View v) {
        btnQuiz = v.findViewById(R.id.btn_quiz);
        tvPageCount = v.findViewById(R.id.tv_pageNumber);
        searchView = v.findViewById(R.id.SearchView);
        pdfView = v.findViewById(R.id.pdf4);
        btnQuiz.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_chapterDetailFragment_to_quizCategoryFragment));
        bPlay.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        bPlay.setTag(R.drawable.ic_play_arrow_black_24dp);
    }
    void showDialog() {
        DialogFragment newFragment = SearchViewDialog.newInstance(
                R.string.welcome);
        newFragment.show(getFragmentManager(), "dialog");
    }

    private void getTextFromPdf() {
        try {
            StringBuilder parsedText = new StringBuilder();
            PdfReader reader = new PdfReader(AppExt.getFilePath(getActivity()));
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                parsedText.append(PdfTextExtractor.getTextFromPage(reader, i + 1).trim()).append("\n"); //Extracting the content from the different pages
            }
            parsedTxt = parsedText.toString();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class ReadTextFromPDFTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
//            pd = new ProgressDialog(getActivity());
//            pd.setTitle("Please Wait");
//            pd.setMessage("Loading...");
//            pd.setCancelable(false);
//            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getTextFromPdf();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            pd.dismiss();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivSearch) {
            showDialog();
           // onButtonShowPopupWindowClick(view);
            Toast.makeText(getActivity(), "searchview", Toast.LENGTH_SHORT).show();
        }
        if (view.getId() == R.id.bPlay) {
            playNStopSpeech();
        } else if (view.getId() == R.id.ivBack) {
            getActivity().onBackPressed();

        }
    }
    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.search_view_dialog_box, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private void playNStopSpeech() {
        Integer resId = (Integer) bPlay.getTag();
        resId = (resId == null) ? 0 : resId;

        switch (resId) {
            case R.drawable.ic_play_arrow_black_24dp:
                bPlay.setImageResource(R.drawable.ic_stop_black_24dp);
                bPlay.setTag(R.drawable.ic_stop_black_24dp);
                String tts = parsedTxt;
                if (parsedTxt.length() > 4000) {
                    tts = parsedTxt.substring(0, 3000);
                    System.out.println("Length is greater");
                }
                Speech.getInstance().say(tts);
                break;
            case R.drawable.ic_stop_black_24dp:
            default:
                bPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                bPlay.setTag(R.drawable.ic_play_arrow_black_24dp);
                Speech.getInstance().stopTextToSpeech();
                break;
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        tvPageCount.setText(String.format("%s / %s", page + 1, pageCount));
        if (page == pageCount - 1) {
            showButton();
        } else if (page == pageCount - 2) {
            if (btnQuiz.getVisibility() == View.VISIBLE) {
                Animation slideUpAnimation = AnimationUtils.loadAnimation(getContext(),
                        R.anim.slide_bottom_animation);
                btnQuiz.startAnimation(slideUpAnimation);
                btnQuiz.setVisibility(View.GONE);
            }
        }
    }

    private void showButton() {
        btnQuiz.setVisibility(View.VISIBLE);
        Animation slideUpAnimation = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up_animation);
        btnQuiz.startAnimation(slideUpAnimation);
    }

    @Override
    public void onStop() {
        super.onStop();
        Speech.init(getContext());
        Speech.getInstance().shutdown();
    }
}
