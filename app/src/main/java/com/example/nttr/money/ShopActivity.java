package com.example.nttr.money;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Button backButton = (Button) findViewById(R.id.toTop2);

        //arraylist
        final ArrayList nameList = new ArrayList();


        //NCMBクラスのinitializeメソッドでAndroid SDKの初期化を行う
        NCMB.initialize(this.getApplicationContext(),"2506244bcc15d1459f7d3d12c1c22fd461e0f773f6a78a0383f32d8e795370ce","0cdd41e363bff95dbdf00ca4bbd07453d0d022c57badb8f511e49dc59de77f6b");

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
                if (e != null){
                    //エラー
                    Log.d("NCMB","NCMB取得失敗");

                }else{
                    //成功
                    Log.d("NCMB","NCMB取得成功");
                    //Log.d("NCMB", String.valueOf(list.indexOf("name")));

                    //値の取り出し方
                    for (int i = 0 ;i < list.size() ; i++){
                        NCMBObject object = (NCMBObject)list.get(i);
                        //Log.d("NCMB", object.getString("name"));
                        Log.d("NCMB", object.getString("price"));
                        nameList.add(object.getString("name"));
                        Log.d("NCMB", String.valueOf(nameList));

                    }

                }
            }
        });

        // GridViewのインスタンス生成
        GridView mGridview = (GridView) findViewById(R.id.gridview);
        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル cell_layout.xml を
        // activity_shop.xml に inflate するためにGridAdapterに引数として渡す
        GridAdapter adapter = new GridAdapter(this.getApplicationContext(),R.layout.cell_layout,nameList);
        Log.d("NCMB", String.valueOf(nameList));

        //アダプターの設定
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.cell_layout,nameList);
        //リストviewとアダプターの接続
        mGridview.setAdapter(adapter);


    }

    public void onClickback(View view){
        // アクティビティを終了させる事により、一つ前のアクティビティへ戻る事が出来る。
        finish();
    }
}
