package com.example2.parth.sample_firebase_app;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.StringTokenizer;

public class ReadTagActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;

    ToggleButton tglReadWrite;
    TextView txtTagContent;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_tag);
        //Context context=getBaseContext();
        //nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        txtTagContent=(TextView)findViewById(R.id.textTagContent);
        // NfcManager manager = (NfcManager) context.getSystemService(Context.NFC_SERVICE);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);


    }


    @Override
    protected void onResume() {
        super.onResume();

        enableForegroundDispatchSystem();
    }

    @Override
    protected void onPause() {
        super.onPause();

        disableForegroundDispatchSystem();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);


        if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
            Toast.makeText(this, "NfcIntent!", Toast.LENGTH_SHORT).show();


                Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                //Bundle b=intent.getExtras();

                if (parcelables != null && parcelables.length > 0) {

                    Toast.makeText(this, "Readin message", Toast.LENGTH_SHORT).show();
                    readTextFromMessage((NdefMessage)parcelables[0]);

                } else {
                    Toast.makeText(this, "No NDEF messages found!", Toast.LENGTH_SHORT).show();
                }


        }
    }

    private void readTextFromMessage(NdefMessage ndefMessage) {

        NdefRecord[] ndefRecords = ndefMessage.getRecords();

        if(ndefRecords != null && ndefRecords.length>0){

            NdefRecord ndefRecord = ndefRecords[0];
            //NdefRecord ndefRecord1=ndefRecord[1];

            String[] tagContent = getTextFromNdefRecord(ndefRecord);
            //Toast.makeText(this,tagContent[0],Toast.LENGTH_SHORT).show();
            //txtTagContent.setText(tagContent);
            txtTagContent.setText(tagContent[0]+"\n"+tagContent[1]);

            //i++;

        }else
        {
            Toast.makeText(this, "No NDEF records found!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void enableForegroundDispatchSystem() {

        Intent intent = new Intent(this, ReadTagActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter[] intentFilters = new IntentFilter[]{};

        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
    }

    private void disableForegroundDispatchSystem() {
        nfcAdapter.disableForegroundDispatch(this);
    }


    public String[] getTextFromNdefRecord(NdefRecord ndefRecord)
    {
        Toast.makeText(this,"Reading Text From NDef Record",Toast.LENGTH_LONG).show();
        String tagContent = null;
        String[] tagInfo=new String[10];
        //tagInfo[0]="";
        try {
            byte[] payload = ndefRecord.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
            //new String()
            Toast.makeText(this,"Creating string from ndefrecord",Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            Log.e("get Text FromNdefRecord", e.getMessage(), e);
        }
        StringTokenizer st=new StringTokenizer(tagContent,"/");

        int len=st.countTokens();
        Toast.makeText(this,"Number of Tokens: "+len,Toast.LENGTH_SHORT).show();
        for(i=0;i<len;i++){
            tagInfo[i]=st.nextToken();

        }
        return tagInfo;
    }



}
