package com.example.thebeautyporterapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.thebeautyporterapp.R;
import com.orhanobut.hawk.Hawk;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardMultilineWidget;

public class PaymentActivity extends AppCompatActivity {


    CardMultilineWidget card_multiline_widget;
    CardView cardLayoutCardPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment);
        cardLayoutCardPayment = (CardView) findViewById(R.id.cardLayoutCardPayment);
        card_multiline_widget = (CardMultilineWidget) findViewById(R.id.card_multiline_widget);
        cardLayoutCardPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Card cardToSave = card_multiline_widget.getCard();

                if (cardToSave == null) {

                    return;
                } else {

                    String cardNumber = cardToSave.getNumber();
                    int expMonth = cardToSave.getExpMonth();
                    int expYear = cardToSave.getExpYear();
                    String cvv = cardToSave.getCVC();
                    int h = cardToSave.getExpMonth();
                    Hawk.put("cardNumber",cardNumber);
                    Hawk.put("expMonth",expMonth);
                    Hawk.put("expYear",expYear);
                    Hawk.put("cvv",cvv);
                    finish();
                }
            }
        });

    }
}
