package com.bwei.iloginorregui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.dbean.FindShoppingCartBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class GAdapter extends RecyclerView.Adapter<GAdapter.VH> {
    private List<FindShoppingCartBean> list = new ArrayList<>();
    private Context context;

    public GAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<FindShoppingCartBean> beans) {
        if (beans != null) {
            list.addAll(beans);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.gou_item_layout, viewGroup, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        FindShoppingCartBean bean = list.get(i);
        vh.gou_item_image.setImageURI(bean.getPic());
        vh.gou_item_title.setText(bean.getCommodityName());
        vh.gou_item_price.setText("￥"+bean.getPrice()+"");
        vh.gou_item_num.setText(bean.getCount()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final CheckBox gou_item_money;
        private final TextView gou_item_title;
        private final SimpleDraweeView gou_item_image;
        private final TextView gou_item_price;
        private final TextView gou_item_jian;
        private final TextView gou_item_jia;
        private final TextView gou_item_num;

        public VH(@NonNull View itemView) {
            super(itemView);
            gou_item_money = itemView.findViewById(R.id.gou_item_money);
            gou_item_title = itemView.findViewById(R.id.gou_item_title);
            gou_item_image = itemView.findViewById(R.id.gou_item_image);
            gou_item_price = itemView.findViewById(R.id.gou_item_price);
            gou_item_jian = itemView.findViewById(R.id.gou_item_jian);
            gou_item_jia = itemView.findViewById(R.id.gou_item_jia);
            gou_item_num = itemView.findViewById(R.id.gou_item_num);
        }
    }
}
