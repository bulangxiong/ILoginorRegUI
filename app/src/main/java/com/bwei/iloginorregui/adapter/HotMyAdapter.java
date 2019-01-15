package com.bwei.iloginorregui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.sbean.CommdityBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class HotMyAdapter extends RecyclerView.Adapter<HotMyAdapter.VH> {
    private List<CommdityBean.RxxpBean.CommodityListBean> list=new ArrayList<>();

    private Context context;

    public HotMyAdapter(Context context) {
        this.context = context;
    }
    public void addItem(List<CommdityBean.RxxpBean.CommodityListBean> commodities){
        if (commodities != null) {
            list.addAll(commodities);
        }
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.hot_item, viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        final CommdityBean.RxxpBean.CommodityListBean listBean = list.get(i);
        vh.item_title.setText(listBean.getCommodityName());
        vh.item_price.setText(listBean.getPrice()+"");
        vh.item_image.setImageURI(listBean.getMasterPic());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxxp.onItemClick(listBean.getCommodityId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder{

        private final TextView item_title;
        private final TextView item_price;
        private final SimpleDraweeView item_image;

        public VH(@NonNull View itemView) {
            super(itemView);
            item_title = itemView.findViewById(R.id.item_title);
            item_image = itemView.findViewById(R.id.item_image);
            item_price =  itemView.findViewById(R.id.item_price);
        }
    }
    private Rxxp rxxp;
    public interface Rxxp{
        void onItemClick(int position);
    }
    public void setRxxpp(Rxxp rxxpp){
        this.rxxp = rxxpp;
    }
}
