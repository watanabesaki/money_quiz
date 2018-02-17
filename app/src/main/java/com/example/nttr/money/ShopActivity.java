package com.example.nttr.money;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Button backButton = (Button) findViewById(R.id.toTop2);

        //String[] list = {"牡羊座","乙女座","射手座"};

        // GridViewのインスタンス生成
        GridView mGridview = (GridView) findViewById(R.id.gridview);

//        GridView adapter = new GridAdapter(this.getApplicationContext(),
//                //R.layout.grid_items,
//                //imgList, //Data Source
//                //members // Data Source
//        );

        //ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.cell_layout,list);
        mGridview.setAdapter(new GridAdapter());

        //リストviewとアダプターの接続
        //mGridview.setAdapter(adapter);

    }

    public void onClickback(View view){
        // アクティビティを終了させる事により、一つ前のアクティビティへ戻る事が出来る。
        finish();
    }
}
