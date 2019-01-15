package com.bwei.iloginorregui.adapter.d_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.dbean.AllOrderBean;
import com.bwei.iloginorregui.bean.dbean.AllOrderUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OnePaymentAdapter extends RecyclerView.Adapter {

    private Context context;

    public OnePaymentAdapter(Context context) {
        this.context = context;
    }
    private ArrayList<AllOrderUser> list = new ArrayList<>();
    //
    public void addItem(List<AllOrderUser> orderList) {
        if(orderList!=null)
        {
            list.addAll(orderList);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.wallpayone_item, null);
        One one = new One(view);
        return one;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AllOrderUser allOrderUser = list.get(i);
        List<AllOrderBean> detailList = (List<AllOrderBean>) allOrderUser.getDetailList();
        One one = (One) viewHolder;
        one.ordernum.setText(allOrderUser.getOrderId());
        Log.e("=========订单Id=========",allOrderUser.getOrderId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        //获取当前时间
        Date date = new Date(allOrderUser.getPayMethod());
        one.orderdata.setText(simpleDateFormat.format(date));

        //全部订单
        LinearLayoutManager linearLayoutManager0 = new LinearLayoutManager(context);
        one.oderrecy2.setLayoutManager(linearLayoutManager0);
        TwoPaymentAdapter twoPaymentAdapter = new TwoPaymentAdapter(context);
        //第二个添加数据
        twoPaymentAdapter.addItem(detailList);
        one.oderrecy2.setAdapter(twoPaymentAdapter);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //创建VH
    class One extends RecyclerView.ViewHolder
    {

        public TextView ordernum;
        public TextView orderdata;
        public RecyclerView oderrecy2;
        public One(@NonNull View itemView) {
            super(itemView);
            ordernum = itemView.findViewById(R.id.ordernum);
            orderdata = itemView.findViewById(R.id.orderdata);
            oderrecy2 = itemView.findViewById(R.id.oderrecy2);
        }
    }


}
