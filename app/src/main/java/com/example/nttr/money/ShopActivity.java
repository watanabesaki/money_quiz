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

    final ArrayList<PaymentHistory> paymentHistoryArrayList = new ArrayList<>();
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
        final GridAdapter adapter = new GridAdapter(this.getApplicationContext(), new ArrayList<PaymentHistory>());

        //アダプターの設定
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.cell_layout,paymentHistoryArrayList);
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
                    //Log.d("NCMB", "NCMB取得失敗");
                } else {
                    //成功
                    //Log.d("NCMB", "NCMB取得成功");
                    //Log.d("NCMB", String.valueOf(list.indexOf("name")));

                    //値の取り出し方
                    for (int i = 0; i < list.size(); i++) {
                        NCMBObject object = (NCMBObject) list.get(i);
                        //Log.d("NCMB", object.getString("name"));
                        //Log.d("NCMB", object.getString("price"));
                        final PaymentHistory paymentHistory = new PaymentHistory();
                        paymentHistory.name = object.getString("name");
                        paymentHistory.price = Integer.valueOf(object.getString("price"));
                        paymentHistory.imageName = object.getString("imageName");
                        paymentHistoryArrayList.add(paymentHistory);
                        //ファイルから画像の取得
                        /*NCMBFile file = new NCMBFile(object.getString("imageName"));
                        file.fetchInBackground(
                                new FetchFileCallback() {
                                    @Override
                                    public void done(byte[] bytes, NCMBException e) {
                                        if (e != null) {
                                            //取得失敗
                                            Log.d("NCMB", "画像取得失敗");
                                            paymentHistory.imageBody = null;
                                            paymentHistoryArrayList.add(paymentHistory);
                                        } else {
                                            //取得成功
                                            Log.d("NCMB", "画像取得成功");
                                            Log.d("NCMB", String.valueOf(bytes));
                                            paymentHistory.imageBody = bytes;

                                        }

                                    }
                                }
                        );*/
                    }
                    adapter.setItem(paymentHistoryArrayList);
                }
            }
        });


        //タップされた事を認識するために、リスナーを設定します
        mGridview.setOnItemClickListener((AdapterView.OnItemClickListener) this);

//        //ファイルから画像の取得
//        NCMBQuery<NCMBFile> file = new NCMBQuery<>("file");
//        file.whereEqualTo("fileName",imageName);
//        file.findInBackground(new FindCallback<NCMBFile>() {
//            @Override
//            public void done(final List<NCMBFile> list, NCMBException e) {
//                if(e !=null){
//                    //画像検索失敗
//                    Log.d("NCMB", "画像検索失敗");
//                }else{
//                    //画像検索成功
//                    for (int i = 0; i < list.size(); i++) {
//                        list.get(i).fetchInBackground(new FetchFileCallback() {
//                            @Override
//                            public void done(byte[] bytes, NCMBException e) {
//                                if(e != null){
//                                    //取得失敗
//                                    Log.d("NCMB", "画像取得失敗");
//
//                                }else{
//                                    //取得成功
//                                    Log.d("NCMB", "画像取得成功");
//                                    Log.d("NCMB", String.valueOf(bytes));
//                                }
//                            }
//                        });
//
//                    }
//
//                }
//            }
//        });

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
        final PaymentHistory selectedItem = (PaymentHistory) paymentHistoryArrayList.get(position);

        Log.d("grid", selectedItem.name);
        Log.d("grid", String.valueOf(selectedItem.price));

        //プリファレンスの読み込み
        SharedPreferences prefer = getSharedPreferences("prefarences", MODE_PRIVATE);
        total = prefer.getInt("total", 0);
        Log.d("grid", String.valueOf(total));

        if (total < selectedItem.price) {
            //Log.d("grid", "お金が足りません");
            Toast.makeText(ShopActivity.this, "お金が足りません", Toast.LENGTH_SHORT).show();

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
            builder.setTitle("貯金額は" + total + "円です。");
            builder.setIcon(R.drawable.money);
            builder.setMessage(selectedItem + "は" + String.valueOf(selectedItem.price) + "円です。買いますか？");


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

                    total = total - selectedItem.price;
                    Log.d("grid", String.valueOf(total));

                    //プリファレンスへアクセス
                    SharedPreferences prefer = getSharedPreferences("prefarences", MODE_PRIVATE);
                    //プリファレンスへ保存する
                    SharedPreferences.Editor editor = prefer.edit();
                    //キー　値
                    editor.putInt("total", total);

                    //同期して保存
                    editor.commit();

                }
            });

            builder.show();
        }


    }
}


    /*NCMBQuery<NCMBFile> file = new NCMBQuery<>("file");
                        file.whereEqualTo("fileName", imageList.get(i));
                                Log.d("NCMB", String.valueOf(imageList.get(i)));

                                file.findInBackground(new FindCallback<NCMBFile>() {
@Override
public void done(final List<NCMBFile> list, NCMBException e) {
        if (e != null) {
        //画像検索失敗
        Log.d("NCMB", "画像検索失敗");
        } else {
        //画像検索成功
        for (int i = 0; i < list.size(); i++) {
        list.get(i).fetchInBackground(new FetchFileCallback() {
@Override
public void done(byte[] bytes, NCMBException e) {
        if (e != null) {
        //取得失敗
        Log.d("NCMB", "画像取得失敗");

        } else {
        //取得成功
        Log.d("NCMB", "画像取得成功");
        Log.d("NCMB", String.valueOf(bytes));
        }
        }
        });

        }

        }
        }
        });*/