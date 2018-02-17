package com.example.nttr.money;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements View.OnClickListener {

    //１変数の宣言
    TextView countTextView;
    TextView contentTextView;



    //ボタンの配列
    Button optionButtons[];

    //クイズの問題の金額
    int quizcontent[] = {521, 787, 875, 907, 426, 666, 218, 445, 551, 689,
            701, 876, 344, 119, 534, 743, 904, 642, 536, 419,
            247, 659, 532, 879, 491, 237, 613, 907, 865, 483};

    //クイズの選択肢１
    int quizoption1[] = {525, 790, 925, 910, 430, 1011,220, 455, 555, 690,
            705, 880, 350, 200, 554, 1043,1000, 1052, 540, 459,
            547, 700, 540, 1009, 541, 240, 615, 1000, 900, 485 };

    //クイズの選択肢２
    int quizoption2[] = {551, 807, 900, 1012, 451, 700, 223 ,550, 560, 1089,
            800, 906, 354, 150, 550, 1243, 1004, 650, 556, 1019,
            550, 660, 552, 900, 500, 252, 653, 910, 1065, 490 };

    //クイズの選択肢３
    int quizoption3[] = {1021, 792, 1075, 912, 530, 1066, 508 ,1045 ,601, 709,
            801, 1076, 544, 500, 1034, 800, 1404, 1152, 1036, 509,
            300, 1059, 1032, 1000, 1091, 537, 620, 912, 1370, 503};

    //クイズ問題の答え
    int quizanswer[] = {1021, 792, 925, 912, 451, 1066 ,223, 455, 601, 690,
            801, 1076, 354, 150, 554, 1243, 1004, 1152, 1036, 459,
            547, 660, 1032, 900, 541, 252, 615, 912, 1065, 483};


    //現在の問題番号
    int questionNumber;

    //獲得したポイント数
    int point;

    //会計金額
    int totalmoney;

    //Handler
    private Handler handler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            count ++;
            timerText.setText(dataFormat.
                    format(count*period));
            handler.postDelayed(this, period);
        }
    };

    private TextView timerText;
    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss.S", Locale.US);

    private int count, period;




    //List配列と同じように複数の変数をまとめて管理する。データが可変の時に使用
    List<Quiz> quizList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //２初期化処理
        //Viewの関連付け
        countTextView = (TextView) findViewById(R.id.countTextView);
        contentTextView = (TextView) findViewById(R.id.contentTextView);


        //配列の初期化
        //ボタンは３つなので、３の大きさの配列を用意する
        optionButtons = new Button[3];
        optionButtons[0] = (Button) findViewById(R.id.optionButton1);
        optionButtons[1] = (Button) findViewById(R.id.optionButton2);
        optionButtons[2] = (Button) findViewById(R.id.optionButton3);

        //TOPに戻るボタン
        Button backButton = (Button) findViewById(R.id.back);
        backButton.setText("TOPに戻る");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "HolidayMDJP.otf");
        backButton.setTypeface(typeface);


        //拡張for文(for each文）　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　r each文）全ての要素を取り出したい時。
        for(Button button : optionButtons){
            //setOnClickListenerに、thisを入れて呼び出す（thisはmainactibvity）
            //thisの下に、赤い波線が表示されたらAlt + Enterキーを押してView.OnClickListenerを実装する
            button.setOnClickListener((View.OnClickListener) this);
        }

        //クイズを１から始めるためのリセットメソッド
        resetQuiz();

        //Handlerの初期化
        count = 0;
        period = 100;

        timerText = findViewById(R.id.timer);
        timerText.setText(dataFormat.format(0));
        timerText.setTypeface(typeface);


    }

    //３クイズのリストを作成する
    void createQuizList(){

        //String option1Number = (moneynumber + 50) + "";
        //String option2Number = (moneynumber + 600) + "";
        //String option3Number = (moneynumber + 1100) + "";

        //クイズを入れるコレクション
        quizList = new ArrayList<>();

        //クイズクラス　変数名　= new クラス名（引数コンストラクト）
        for(int i=0; i < quizcontent.length; i++){
            Quiz quiz = new Quiz("合計金額は" + quizcontent[i] + "", quizoption1[i] + "", quizoption2[i] + "", quizoption3[i] +"", quizanswer[i] + "");
            // Listを実装したArrayListというものを使い、入れていく。
            quizList.add(quiz);

        }

//        Quiz quiz1 = new Quiz("合計金額は" + quizcontent[0] + "", quizoption1[], quizoption2[], quizoption3[], quizanswer[0] + "");
//        Quiz quiz2 = new Quiz("合計金額は" + quizcontent[1] + "", 790 + "", 807 + "", 792 + "", quizanswer[1] + "");
//        Quiz quiz3 = new Quiz("合計金額は" + quizcontent[2] + "", 925 + "", 900 + "", 1075 + "", quizanswer[2] + "");
//        Quiz quiz4 = new Quiz("合計金額は" + quizcontent[3] + "", 910 + "", 1012 + "", 912 + "", quizanswer[3] + "");
//        Quiz quiz5 = new Quiz("合計金額は" + quizcontent[4] + "", 430 + "", 451 + "", 530 + "", quizanswer[4] + "");
//        Quiz quiz6 = new Quiz("合計金額は" + quizcontent[5] + "", 1011 + "", 700 + "", 1066 + "",  quizanswer[5] + "");


//        // Listを実装したArrayListというものを使い、入れていく。
//        quizList = new ArrayList<>();
//        quizList.add(quiz1);
//        quizList.add(quiz2);
//        quizList.add(quiz3);
//        quizList.add(quiz4);
//        quizList.add(quiz5);
//        quizList.add(quiz6);

        // Listの中身をシャッフルします
        Collections.shuffle(quizList);

    }

    //４クイズを表示する
    void showQuiz(){


        //クイズの番号を表示する
        countTextView.setText((questionNumber + 1) + "問目");

        //表示する問題をリストから取得
        Quiz quiz = quizList.get(questionNumber);

        contentTextView.setText(quiz.content);
        optionButtons[0].setText(quiz.option1);
        optionButtons[1].setText(quiz.option2);
        optionButtons[2].setText(quiz.option3);

        //カスタムフォント
        Typeface typeface = Typeface.createFromAsset(getAssets(), "HolidayMDJP.otf");
        countTextView.setTypeface(typeface);
        contentTextView.setTypeface(typeface);
        optionButtons[0].setTypeface(typeface);
        optionButtons[1].setTypeface(typeface);
        optionButtons[2].setTypeface(typeface);

    }

    //５クイズのリセットをする
    void resetQuiz(){
        //タイマーリセット
        //timerText.setText(dataFormat.format(0));
        count = 0;
        //タイマーstart
        handler.post(runnable);

        questionNumber = 0;
        point = 0;
        createQuizList();
        showQuiz();

    }

    //６クイズのアップデート
    void updateQuiz(){
        //クイズ番号プラス１
        questionNumber++;

        //arraylistの要素数はsize。
        if (questionNumber < 5){
            showQuiz();

        }else{
            //タイマーストップ
            handler.removeCallbacks(runnable);

            // 全ての問題を解いてしまったので、結果を表示。
            // 結果表示にはDialogを使います。
            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
            builder.setTitle("結果");
            builder.setIcon(R.drawable.money);
            builder.setMessage( "5問中" + point + "正解でした！\nもらえたお釣りは" + totalmoney + "円です。");
            

            //OKボタン
            builder.setNeutralButton("TOPに戻る", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // トップに戻る
                    finish();
                }
            });

            builder.setPositiveButton("リトライ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //もう一度クイズをだす
                    resetQuiz();
                }
            });

            builder.show();

            //プリファレンスへアクセス
            SharedPreferences prefer = getSharedPreferences("prefarences",MODE_PRIVATE);
            //読み込み
            int result = prefer.getInt("result",0);
            int total =prefer.getInt("total",0);

            //結果とトータルをたす
            int resultplus = result + total;

            //タイムの最小を取り出す。
            int time = prefer.getInt("timemin",0);
            //Log.d("time", String.valueOf(time));
            //Log.d("count", String.valueOf(count));

            int timeresult = 0;


            //タイムの比較
            if (time == 0){
                timeresult = count;
            }else if (count >= time){
                timeresult = time;
            }else if(time > count){
                timeresult = count;
            }



            //プリファレンスへ保存する
            SharedPreferences.Editor editor = prefer.edit();
            //キー　値
            editor.putInt("total",resultplus);
            editor.putInt("timemin",timeresult);

            //同期して保存
            editor.commit();

        }
    }


    //７setOnClickListerを呼び出したViewをクリックした時に呼び出されるメソッド
    @Override
    public void onClick(View view) {
        // 引数のViewには、押されたViewが入っています。
        // Buttonが押された時しかよばれないので、キャストといって型を示してあげます。

        Button clickedButton = (Button) view;

        Quiz quiz = quizList.get(questionNumber);

        // ボタンの文字と、答えが同じかチェックします。
        if (TextUtils.equals(clickedButton.getText(), quiz.answer)){
            //正解の場合はプラス１ポイント
            point ++;
            //合計金額(お釣りの合計)
            int charge = quizanswer[questionNumber] - quizcontent[questionNumber];
            totalmoney += charge;

            //プリファレンスへアクセス
            SharedPreferences prefer = getSharedPreferences("prefarences",MODE_PRIVATE);
            //プリファレンスへ保存する
            SharedPreferences.Editor editor = prefer.edit();
            //キー　値
            editor.putInt("result",totalmoney);
            //同期して保存
            editor.commit();

            //正解のトースト
            Toast.makeText(this,"正解" ,Toast.LENGTH_SHORT).show();

        }else{
            //はずれのトースト

            Toast.makeText(this,"はずれ",Toast.LENGTH_SHORT).show();
        }
        //次の問題へアップデート
        updateQuiz();
    }






    public void onClickback(View view){
        // アクティビティを終了させる事により、一つ前のアクティビティへ戻る事が出来る。
        finish();

    }


}
