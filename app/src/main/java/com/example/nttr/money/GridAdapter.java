package com.example.nttr.money;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by nttr on 2018/02/17.
 */

// BaseAdapterを継承したGridAdapterクラス
public class GridAdapter extends BaseAdapter {

    // ViewHolderクラス
    private static class  ViewHolder {
        TextView name;
        TextView price;
    }

    private String[] nameList = {"牡羊座","乙女座","射手座"};

    private String[] priceList = {"100","200","300"};



//    // コンストラクタ　GridViewからデータを受け取る
//    GridAdapter(Context context, // this.getApplicationContext()　コンテキスト
//                int layoutId, // R.layout.grid_items => grid_items.xmlファイル
//                List<Integer> iList, // imgList のarray
//                String[] members // members配列
//    )

    //工数
    @Override
    public int getCount() {

        return nameList.length;
    }

    @Override
    public Object getItem(int position) {

        return nameList[position];
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(android.R.id.text1);
            holder.price = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(holder);

        }else{
            //viewholderから取り出す。（効率的にセルを表示する）
            holder =(ViewHolder)convertView.getTag();
        }

        holder.name.setText(nameList[position]);
        holder.price.setText(priceList[position]);

        //表示する
        return convertView;

    }
}
