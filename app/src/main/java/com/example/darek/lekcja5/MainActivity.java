package com.example.darek.lekcja5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String[] tele;
    private Class[] activities={Tele1Activity.class, Tele2Activity.class, Tele3Activity.class, Tele4Activity.class, Tele5Activity.class};

    static final private int ALERT_DIALOG_PLAIN = 1;
    static final private int ALERT_DIALOG_BUTTONS = 2;
    static final private int ALERT_DIALOG_LIST = 3;
    static final private int CUSTOM_ALERT_DIALOG = 4;
    private Button btnNewAlertDialog;
    private Button btnNewAlertDialogButton;
    private Button btnNewAlertDialogList;
    private Button btnNewCustomAlertDialog;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.telephones);
        initResources();
        initTelephonesListView();
        btnNewAlertDialog = (Button) findViewById(R.id.btnNewAlertDialog);
        btnNewAlertDialogButton = (Button) findViewById(R.id.btnNewAlertDialogButton);
        btnNewAlertDialogList = (Button) findViewById(R.id.btnNewAlertDialogList);
        btnNewCustomAlertDialog = (Button) findViewById(R.id.btnNewCustomAlertDialog);
        initButtonsClick();
    }

    private void initResources(){
        Resources res = getResources();
        tele = res.getStringArray(R.array.telephones);
    }

    private void initTelephonesListView(){
        lv.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1,tele));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id){
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,activities[pos]);
                startActivity(intent);
            }
        });
    }

    private void initButtonsClick() {
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnNewAlertDialog:
                        showDialog(ALERT_DIALOG_PLAIN);
                        break;
                    case R.id.btnNewAlertDialogButton:
                        showDialog(ALERT_DIALOG_BUTTONS);
                        break;
                    case R.id.btnNewAlertDialogList:
                        showDialog(ALERT_DIALOG_LIST);
                        break;
                    case R.id.btnNewCustomAlertDialog:
                        showDialog(CUSTOM_ALERT_DIALOG);
                        break;
                    default:
                        break;
                }
            }
        };
        btnNewAlertDialog.setOnClickListener(listener);
        btnNewAlertDialogButton.setOnClickListener(listener);
        btnNewAlertDialogList.setOnClickListener(listener);
        btnNewCustomAlertDialog.setOnClickListener(listener);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ALERT_DIALOG_PLAIN:
                return createPlainAlertDialog();
            case ALERT_DIALOG_BUTTONS:
                return createAlertDialogWithButtons();
            case ALERT_DIALOG_LIST:
                return createAlertDialogWithList();
            case CUSTOM_ALERT_DIALOG:
                return createCustomAlertDialog();
            default:
                return null;
        }
    }
    private Dialog createPlainAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Witam w aplikacji");
        dialogBuilder.setMessage("Ta wiadomość jest do Ciebie!");
        return dialogBuilder.create();
    }

    private Dialog createAlertDialogWithButtons() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Wyjście");
        dialogBuilder.setMessage("Czy napewno chcesz wyjść?");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Tak", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                showToast("Wychodzę");
                MainActivity.this.finish();
            }
        });
        dialogBuilder.setNegativeButton("Nie", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                showToast("Anulowaleś wyjście");
            }
        });
        return dialogBuilder.create();
    }
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private Dialog createCustomAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        View layout = getCustomDialogLayout();
        dialogBuilder.setView(layout);
        dialogBuilder.setTitle("HEHEHE");
        return dialogBuilder.create();
    }
    private View getCustomDialogLayout() {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        //return inflater.inflate(R.layout.hehe_layout, (ViewGroup)findViewById(R.id.activity_main));
        return inflater.inflate(R.layout.hehe_layout,null);
    }

    private Dialog createAlertDialogWithList() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final String[] options = {"Odwtórz 1 utwór", "Odtwórz 2 utwór","Odtwórz 3 utwór","Odtwórz 4 utwór", "Zastopuj"};
        dialogBuilder.setTitle("Odtwarzacz");
        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int position){
                if(position==4){
                    mediaPlayer.stop();
                }
                if(position==0) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                    }
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.pila);
                    mediaPlayer.start();
                }
                if(position==1) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                    }
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.okruszek);
                    mediaPlayer.start();
                }
                if(position==2) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                    }
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.balagane);
                    mediaPlayer.start();
                }
                if(position==3) {
                    if (mediaPlayer != null) {
                        mediaPlayer.release();
                    }
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.miller);
                    mediaPlayer.start();
                }
            }
        });
        return dialogBuilder.create();
//      return dialogBuilder.show();
    }

}
