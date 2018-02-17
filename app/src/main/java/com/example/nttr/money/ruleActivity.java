package com.example.nttr.money;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ruleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        TextView rule = (TextView) findViewById(R.id.rule);
        rule.setText("遊び方");

        TextView main = (TextView) findViewById(R.id.main);

        TextView example = (TextView) findViewById(R.id.example);

        Button backButton = (Button) findViewById(R.id.toTop);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "HolidayMDJP.otf");
        rule.setTypeface(typeface);
        main.setTypeface(typeface);
        example.setTypeface(typeface);
        backButton.setTypeface(typeface);



    }

    public void onClickback(View view){
        // アクティビティを終了させる事により、一つ前のアクティビティへ戻る事が出来る。
        finish();
    }
}
