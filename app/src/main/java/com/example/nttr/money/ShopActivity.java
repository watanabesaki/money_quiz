package com.example.nttr.money;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    final ArrayList nameList = new ArrayList();
    final ArrayList priceList = new ArrayList();

    int total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Button backButton = (Button) findViewById(R.id.toTop2);



        // GridViewのインスタンス生成
        GridView mGridview = (GridView) findViewById(R.id.gridview);//setContentView にListViewのインスタンスを設定
        mGridview.setNumColumns(3);
        //setContentView(mGridview);

        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル cell_layout.xml を
        // activity_shop.xml に inflate するためにGridAdapterに引数として渡す
        final GridAdapter adapter = new GridAdapter(this.getApplicationContext(), R.layout.cell_layout, new ArrayList(),new ArrayList());

        //アダプターの設定
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.cell_layout,nameList);
        //リストviewとアダプターの接続
        mGridview.setAdapter(adapter);

        //NCMBクラスのinitializeメソッドでAndroid SDKの初期化を行う
        NCMB.initialize(this.getApplicationContext(), "2506244bcc15d1459f7d3d12c1c22fd461e0f773f6a78a0383f32d8e795370ce", "0cdd41e363bff95dbdf00ca4bbd07453d0d022c57badb8f511e49dc59de77f6b");

//        NCMBObject obj =new NCMBObject("shop");
//        try {
//            obj.fetch();
//        } catch (NCMBException e) {
//            e.printStackTrace();
//        }

        // クラスのNCMBObject指定
        final NCMBQuery<NCMBObject> query = new NCMBQuery<NCMBObject>("Shop");
        //価格昇順で並び替え
        query.addOrderByAscending("price");


        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, NCMBException e) {
                if (e != null) {
                    //エラー
                    Log.d("NCMB", "NCMB取得失敗");
                } else {
                    //成功
                    Log.d("NCMB", "NCMB取得成功");
                    //Log.d("NCMB", String.valueOf(list.indexOf("name")));

                    //値の取り出し方
                    for (int i = 0; i < list.size(); i++) {
                        NCMBObject object = (NCMBObject) list.get(i);
                        //Log.d("NCMB", object.getString("name"));
                        //Log.d("NCMB", object.getString("price"));
                        nameList.add(object.getString("name"));
                        priceList.add(object.getString("price"));
                        //Log.d("NCMB", String.valueOf(nameList));
                    }
                    adapter.setItem(nameList,priceList);


                }
            }
        });

        //タップされた事を認識するために、リスナーを設定します
        mGridview.setOnItemClickListener((AdapterView.OnItemClickListener) this);

    }

    public void onClickback(View view) {
        // アクティビティを終了させる事により、一つ前のアクティビティへ戻る事が出来る。
        finish();
    }

    //itemがタップされ時に onItemClick() が呼ばれ、itemの position から配列のindexを取得して putExtra() で intent にセットします。
    // そして遷移先の Activitiy に渡す。
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //// clickされたpositionのitemのID,商品名を取得
        String selectedItem = (String) nameList.get(position);
        final int selectedprice = Integer.parseInt((String) priceList.get(position));

        Log.d("grid", selectedItem);
        Log.d("grid", String.valueOf(selectedprice));

        //プリファレンスの読み込み
        SharedPreferences prefer = getSharedPreferences("prefarences", MODE_PRIVATE);
        total = prefer.getInt("total", 0);
        Log.d("grid", String.valueOf(total));

        if(total < selectedprice){
            Log.d("grid", "お金が足りません");
            Toast.makeText(ShopActivity.this,"お金が足りません",Toast.LENGTH_SHORT).show();

        }else{

            AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyAlertDialogStyle);
            builder.setTitle("貯金額は" + total + "です。");
            builder.setIcon(R.drawable.money);
            builder.setMessage( selectedItem + "は" + String.valueOf(selectedprice) + "円です。買いますか？");


            //OKボタン
            builder.setNeutralButton("やめる", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // トップに戻る
                    finish();
                }
            });

            builder.setPositiveButton("買う", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //買い物する
//                    Log.d("grid", "買い物しましょう");

                    total = total - selectedprice;
                    Log.d("grid", String.valueOf(total));

                    //プリファレンスへアクセス
                    SharedPreferences prefer = getSharedPreferences("prefarences",MODE_PRIVATE);
                    //プリファレンスへ保存する
                    SharedPreferences.Editor editor = prefer.edit();
                    //キー　値
                    editor.putInt("total",total);

                    //同期して保存
                    editor.commit();

                }
            });

            builder.show();
        }






    }
}
