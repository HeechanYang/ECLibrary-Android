package com.ec.library.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AlertDialogBuilderUtil {
    public static void simpleDialog(Context context, String title, String message,
                                    final SimpleListener simpleListener) {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpleListener.positiveListener(dialog, which);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

    public static void editTextDialog(Context context, String title, String message,
                                      final EditTextListener positiveListener) {
        final EditText et = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(PxDpUtil.convertPixelsToDp(20, context), 0, PxDpUtil.convertPixelsToDp(20, context), 0);
        et.setLayoutParams(lp);

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title)
                .setMessage(message)
                .setView(et)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveListener.positiveListener(dialog, which, et);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

    public static void doubleEditTextDialog(Context context, String title, String message,
                                            String[] values, String[] hints,
                                            final DoubleEditTextListener positiveListener) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(PxDpUtil.convertPixelsToDp(20, context), 0, PxDpUtil.convertPixelsToDp(20, context), 0);
        final List<EditText> editTexts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            final EditText et = new EditText(context);
            et.setText(values[i]);
            et.setHint(hints[i]);
            et.setLayoutParams(lp);
            linearLayout.addView(et);
            editTexts.add(et);
        }

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title)
                .setMessage(message)
                .setView(linearLayout)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveListener.positiveListener(dialog, which,
                                editTexts.get(0), editTexts.get(1));
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

    public static void tripleEditTextDialog(Context context, String title, String message,
                                            String[] values, String[] hints,
                                            final TripleEditTextListener positiveListener) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(PxDpUtil.convertPixelsToDp(20, context), 0, PxDpUtil.convertPixelsToDp(20, context), 0);
        final List<EditText> editTexts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final EditText et = new EditText(context);
            et.setText(values[i]);
            et.setHint(hints[i]);
            et.setLayoutParams(lp);
            linearLayout.addView(et);
            editTexts.add(et);
        }

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle(title)
                .setMessage(message)
                .setView(linearLayout)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        positiveListener.positiveListener(dialog, which,
                                editTexts.get(0), editTexts.get(1), editTexts.get(2));
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertBuilder.create().show();
    }

    public interface SimpleListener {
        public void positiveListener(DialogInterface dialog, int which);
    }

    public interface EditTextListener {
        public void positiveListener(DialogInterface dialog, int which, EditText editText);
    }

    public interface DoubleEditTextListener {
        public void positiveListener(DialogInterface dialog, int which, EditText editText1
                , EditText editText2);
    }

    public interface TripleEditTextListener {
        public void positiveListener(DialogInterface dialog, int which,
                                     EditText editText1, EditText editText2, EditText editText3);
    }
}
