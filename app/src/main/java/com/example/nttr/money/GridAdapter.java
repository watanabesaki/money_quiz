package com.example.nttr.money;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by nttr on 2018/02/17.
 */
// BaseAdapterを継承したGridAdapterクラス
public class GridAdapter extends BaseAdapter {

    // ViewHolderクラス
    private static class  ViewHolder {
        TextView name;
        TextView price;
        ImageView shopImage;
    }

    private ArrayList<PaymentHistory> paymentHistoryList = new ArrayList<PaymentHistory>();

    private LayoutInflater inflater;
    //private int layoutID;

    // 引数がMainActivityからの設定と合わせる
    GridAdapter(Context context, ArrayList<PaymentHistory> paymentHistoryList) {
        super();
        //this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.layoutID = layoutID;
        inflater = LayoutInflater.from(context);
        this.paymentHistoryList = paymentHistoryList;
    }



//    // コンストラクタ　GridViewからデータを受け取る
//    GridAdapter(Context context, // this.getApplicationContext()　コンテキスト
//                int layoutId, // R.layout.grid_items => grid_items.xmlファイル
//                List<Integer> iList, // imgList のarray
//                String[] members // members配列
//    )



    //工数
    @Override
    public int getCount() {

        return paymentHistoryList.size();

    }

    @Override
    public Object getItem(int position) {

        return paymentHistoryList.get(position);

    }

    public void setItem(ArrayList<PaymentHistory> paymentHistoryList) {
        this.paymentHistoryList = paymentHistoryList;
        // データを更新する
        notifyDataSetChanged();
    }

    //番目
    @Override
    public long getItemId(int position) {

        return position;
    }

    // ViewHolder と convertView を使って View の再利用
    //1位置　２作成済みのセルのこと、再利用する。
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null){
            //convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_layout,parent,false);
            //convertView = inflater.inflate(layoutID,parent,false);
            convertView = inflater.inflate(R.layout.cell_layout,null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.shopImage = (ImageView) convertView.findViewById(R.id.shopimage);

            convertView.setTag(holder);
        }else{
            //viewholderから取り出す。（効率的にセルを表示する）
            holder =(ViewHolder)convertView.getTag();
        }
        PaymentHistory item = paymentHistoryList.get(position);

        holder.name.setText(item.name);
        holder.price.setText(item.price + "円");
        Picasso.with(holder.shopImage.getContext())
                .load(item.getImageUrl())
                .error(R.drawable.bag)
//                .placeholder(R.drawable.bag)
                .into(holder.shopImage);
        //Log.d("grid", String.valueOf(paymentHistoryList));


        //表示する
        return convertView;

    }
}
