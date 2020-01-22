package ch.adrart.zli.spingo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class spin extends AppCompatActivity {

    private static final String TAG = "spin";
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin);
    }
}
