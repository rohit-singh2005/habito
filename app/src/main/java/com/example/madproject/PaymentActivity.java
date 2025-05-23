package com.example.madproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    private String[] paymentNames = {"Credit Card", "Debit Card", "PayPal", "Wallet", "UPI", "Net Banking"};
    private int[] paymentIcons = {R.drawable.ic_credit_card, R.drawable.ic_debit_card, R.drawable.paypal,
            R.drawable.ic_wallet, R.drawable.ic_upi, R.drawable.ic_netbanking};
    private RadioButton lastChecked = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        LinearLayout container = findViewById(R.id.paymentMethodsContainer);

        for (int i = 0; i < paymentNames.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.payment_method_item, container, false);

            TextView methodText = view.findViewById(R.id.methodText);
            ImageView methodIcon = view.findViewById(R.id.methodIcon);
            RadioButton radioButton = view.findViewById(R.id.methodRadio);

            methodText.setText(paymentNames[i]);
            methodIcon.setImageResource(paymentIcons[i]);

            radioButton.setOnClickListener(v -> {
                if (lastChecked != null) {
                    lastChecked.setChecked(false);
                }
                radioButton.setChecked(true);
                lastChecked = radioButton;
            });

            container.addView(view);
        }

        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, activity_orders.class);
            startActivity(intent);
            Toast.makeText(this, "Room has been booked.", Toast.LENGTH_SHORT).show();
        });
    }
}
