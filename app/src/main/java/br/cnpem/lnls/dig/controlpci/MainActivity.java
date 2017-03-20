package br.cnpem.lnls.dig.controlpci;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {

    // For q
    private Handler mHandler = new Handler();
    private TextView qrValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrValue = (TextView) findViewById(R.id.qrValue);
        ImageButton btnReadqr = (ImageButton) findViewById(R.id.btnReadqr);


        /***
         * Event of click on button
         */
        btnReadqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the last parameter to true to open front light if available
                IntentIntegrator.initiateScan(MainActivity.this);
            }
        });

    }


    /***
     *  For read qrcode
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                if (result != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            qrValue.setText(result);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://digsirius.tk:8080/?P=" + result));
                            startActivity(browserIntent);
                        }
                    });
                }
                break;
            default:
        }
    }
}
