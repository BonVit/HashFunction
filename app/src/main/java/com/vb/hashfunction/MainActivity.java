package com.vb.hashfunction;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String ENCRYPT = "ENCRYPT";
    private static final String DECRYPT = "DECRYPT";

    ProgressBar mLoadingBar;
    RelativeLayout mContent;
    EditText mEncryptText;
    EditText mHashCode;
    Button mEncryptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingBar = (ProgressBar) findViewById(R.id.activityMainProgressBar);
        mContent = (RelativeLayout) findViewById(R.id.activityMainContent);

        mHashCode = (EditText) findViewById(R.id.editTextHashCode);

        mEncryptText = (EditText) findViewById(R.id.editTextEncrypt);
        mEncryptText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length() == 0)
                    mEncryptButton.setEnabled(false);
                else
                    mEncryptButton.setEnabled(true);
            }
        });



        mEncryptButton = (Button) findViewById(R.id.buttonEcnrypt);
        mEncryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading(true);
                new CryptAsyncTask().execute(mEncryptText.getText().toString());
            }
        });

        loading(false);
        if(mEncryptText.getText().toString().length() == 0)
            mEncryptButton.setEnabled(false);
    }

    private class CryptAsyncTask extends AsyncTask<String, Object, String>
    {
        @Override
        protected String doInBackground(String... args) throws IllegalArgumentException {
            if(args.length != 1)
                return null;

            long h = HashFunction.getHash(args[0]);

            String result = new String((ByteBuffer.allocate(8).putLong(h).array()), Charset.forName("UTF-8"));

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            loading(false);

            mHashCode.setText(result);
        }
    }

    private void loading(boolean isLoading)
    {
        if(isLoading)
        {
            mLoadingBar.setVisibility(View.VISIBLE);
            mContent.setVisibility(View.GONE);
        }
        else
        {
            mLoadingBar.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        }
    }
}
