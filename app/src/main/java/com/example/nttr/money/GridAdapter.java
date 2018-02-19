package com.example.nttr.money;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    }

    private ArrayList<String> nameListAdapter = new ArrayList<String>();
    private ArrayList<String> priceListAdapter = new ArrayList<String>();

    private LayoutInflater inflater;
    //private int layoutID;

    // 引数がMainActivityからの設定と合わせる
    GridAdapter(Context context, int layoutID, ArrayList<String> nameList,ArrayList<String> priceList) {
        super();
        //this.inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //this.layoutID = layoutID;
        inflater = LayoutInflater.from(context);
        nameListAdapter = nameList;
        priceListAdapter = priceList;

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

        return nameListAdapter.size();

    }

    @Override
    public Object getItem(int position) {

        return nameListAdapter.get(position);

    }

    public void setItem(ArrayList<String> nameListAdapter,ArrayList<String> priceListAdapter) {
        this.nameListAdapter = nameListAdapter;
        this.priceListAdapter = priceListAdapter;
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
            convertView.setTag(holder);

        }else{
            //viewholderから取り出す。（効率的にセルを表示する）
            holder =(ViewHolder)convertView.getTag();
        }

        holder.name.setText(nameListAdapter.get(position));
        holder.price.setText(priceListAdapter.get(position));
        Log.d("grid", String.valueOf(nameListAdapter));


        //表示する
        return convertView;

    }
}
