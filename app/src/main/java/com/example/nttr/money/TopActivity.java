package com.example.nttr.money;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TopActivity extends Activity {

    //称号のテキスト
    TextView degreeTextView;
    TextView timeTextView;
    TextView walletTextView;
    //お金の画像
    ImageView moneyImage;

    //プリファレンスを代入するもの
    int total;
    int timemin;

    //タイマー
    private TextView timerText;
    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss.S", Locale.US);

    private int count, period;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

//        //NCMBクラスのinitializeメソッドでAndroid SDKの初期化を行う
//        NCMB.initialize(this.getApplicationContext(),"2506244bcc15d1459f7d3d12c1c22fd461e0f773f6a78a0383f32d8e795370ce","0cdd41e363bff95dbdf00ca4bbd07453d0d022c57badb8f511e49dc59de77f6b");
//
//        // クラスのNCMBObjectを作成
//        NCMBObject obj = new NCMBObject("TestClass");
//
//        // オブジェクトの値を設定
//        obj.put("message", "Hello, NCMB!");
//
//        // データストアへの登録
//        obj.saveInBackground(new DoneCallback() {
//            @Override
//            public void done(NCMBException e) {
//                if(e != null){
//                    //保存に失敗した場合の処理
//                    Log.d("NCMB","失敗");
//
//                }else {
//                    //保存に成功した場合の処理
//                    Log.d("NCMB","成功");
//
//
//                }
//            }
//        });


        //タイトル
        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        titleTextView.setText("スマートな会計をして、\n貯金額を増やそう");

        //スタートボタン
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setText("START");

        //合計金額
        walletTextView = (TextView) findViewById(R.id.walletTextView);

        //最小タイム
        timeTextView = (TextView) findViewById(R.id.time);


        moneyImage = (ImageView) findViewById(R.id.moneyImage);

        moneyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopActivity.this, ShopActivity.class);

                //startActivityでIntentの内容を発行する
                startActivity(intent);

            }
        });

        //遊び方
        Button ruleButton = (Button) findViewById(R.id.rule);
        ruleButton.setText("遊び方");

        //合計金額タイトル
        TextView totalTextview = (TextView) findViewById(R.id.totalTextView);
        totalTextview.setText("貯金額");

        //リセットボタン
        Button resetButton = (Button) findViewById(R.id.reset);
        resetButton.setText("リセット");

        moneyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), ShopActivity.class);
                Intent intent = new Intent(TopActivity.this, ShopActivity.class);
                //startActivityでIntentの内容を発行する
                startActivity(intent);

            }
        });


        //フォント
        Typeface typeface = Typeface.createFromAsset(getAssets(), "HolidayMDJP.otf");
        titleTextView.setTypeface(typeface);
        startButton.setTypeface(typeface);
        ruleButton.setTypeface(typeface);
        totalTextview.setTypeface(typeface);
        resetButton.setTypeface(typeface);


        //プリファレンスの読み込み
        SharedPreferences prefer = getSharedPreferences("prefarences", MODE_PRIVATE);
        total = prefer.getInt("total", 0);
        walletTextView.setText(total + "円");
        walletTextView.setTypeface(typeface);

        //タイムの表示
        timemin = prefer.getInt("timemin", 0);
        period = 100;
        timeTextView.setText("最高タイムは" + dataFormat.
                format(timemin * period) + "");
        timeTextView.setTypeface(typeface);
        Log.d("timemin", String.valueOf(timemin));


        //貯金額により称号を表示する
        degreeTextView = (TextView) findViewById(R.id.degree);
        degreeshow();
        degreeTextView.setTypeface(typeface);


    }

    @Override
    protected void onResume() {
        super.onResume();
        resultshow();

    }

    //結果の反映
    public void resultshow(){
        //フォント
        Typeface typeface = Typeface.createFromAsset(getAssets(), "HolidayMDJP.otf");

        //プリファレンスの読み込み
        SharedPreferences prefer = getSharedPreferences("prefarences", MODE_PRIVATE);

        //お金の反映
        total = prefer.getInt("total", 0);
        walletTextView.setText(total + "円");
        walletTextView.setTypeface(typeface);



        //貯金額により称号を表示する
        degreeTextView = (TextView) findViewById(R.id.degree);
        degreeshow();
        degreeTextView.setTypeface(typeface);

        //タイムの表示
        timemin = prefer.getInt("timemin", 0);
        period = 100;
        timeTextView.setText("最高タイムは" + dataFormat.
                format(timemin * period) + "");
        timeTextView.setTypeface(typeface);
        Log.d("timemin", String.valueOf(timemin));



    }


    //クイズへ
    public void click(View view) {

        //画面遷移のクラスはIntent
        //IntentはAndroid内でActivity同士やアプリ間の通信を行う
        Intent intent = new Intent(this, MainActivity.class);

        //startActivityでIntentの内容を発行する
        startActivity(intent);
    }




    //リセットボタン
    public void reset(View view) {
        total = 0;
        timemin = 0;

        //プリファレンスへアクセス
        SharedPreferences prefer = getSharedPreferences("prefarences", MODE_PRIVATE);
        //プリファレンスへ保存する
        SharedPreferences.Editor editor = prefer.edit();
        //キー　値
        editor.putInt("total", total);
        editor.putInt("timemin", timemin);
        //同期して保存
        editor.apply();

        //タイムの表示
        timeTextView.setText("最高タイムは" + dataFormat.format(timemin * period) + "");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "HolidayMDJP.otf");
        timeTextView.setTypeface(typeface);

        //金額の表示
        walletTextView.setText(total + "円");
        walletTextView.setTypeface(typeface);

        //称号の表示
        degreeshow();
        degreeTextView.setTypeface(typeface);
    }


    //遊び方へ
    public void clicktorule(View view) {
        //画面遷移のクラスはIntent
        //IntentはAndroid内でActivity同士やアプリ間の通信を行う
        Intent intent = new Intent(this, ruleActivity.class);

        //startActivityでIntentの内容を発行する
        startActivity(intent);
    }

    //貯金額による称号の場合分け
    public void degreeshow() {

        //場合分け
        if (total == 0){
            degreeTextView.setText("お金がない人");
        }else if (total < 1000) {
            //1000円未満
            degreeTextView.setText("お菓子が買える人");
        } else if ((total >= 1000) && (total < 2000)) {
            //1000以上2000円未満
            degreeTextView.setText("Tシャツが買える人");
        } else if ((total >= 2000) && (total < 3000)) {
            degreeTextView.setText("焼肉食べ放題に行ける人");
        } else if ((total >= 3000) && (total < 5000)) {
            degreeTextView.setText("ゲームソフトが買える人");
        } else if ((total >= 5000) && (total < 8000)) {
            degreeTextView.setText("メガネが買える人");
        } else if ((total >= 8000) && (total < 10000)) {
            degreeTextView.setText("テーマパークに行ける人");
        } else if ((total >= 10000) && (total < 12000)) {
            degreeTextView.setText("カメラが買える人");
        } else if((total >= 12000) && (total < 15000)){
            degreeTextView.setText("温泉旅行に行ける人");
        } else{
            degreeTextView.setText("あなたはお金持ち");
        }
    }


}
