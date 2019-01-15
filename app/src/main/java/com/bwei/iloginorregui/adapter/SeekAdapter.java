package com.bwei.iloginorregui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.activity.DetailsActivity;
import com.bwei.iloginorregui.bean.sbean.FindCommodityListByLabel;
import com.bwei.iloginorregui.fragment.details.GoodIntroduceFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class SeekAdapter extends RecyclerView.Adapter<SeekAdapter.VH> {
    private List<FindCommodityListByLabel> list = new ArrayList<>();
    private Context context;
    //方法名
    private OnItemClickListener onItemClickListener;

    public SeekAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<FindCommodityListByLabel> labels) {
        if (labels != null) {
            list.clear();
            list.addAll(labels);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.seek_item_layout, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
        final FindCommodityListByLabel label = list.get(i);
        String masterPic = label.getMasterPic();
        String[] split = masterPic.split(",");
        vh.seek_item_image.setImageURI(split[0]);
        vh.seek_item_title.setText(label.getCommodityName());
        vh.seek_item_price.setText("￥" + label.getPrice() + "");
        vh.seek_item_saleNum.setText("已售" + label.getSaleNum() + "件");

            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //条目点击 获取商品id
                    onItemClickListener.onItemClick(label.getCommodityId());

                }
            });

    }

    /**
     * 条目点击进入详情页面 接口回调
     */

    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int cid);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView seek_item_image;
        private final TextView seek_item_title;
        private final TextView seek_item_price;
        private final TextView seek_item_saleNum;

        public VH(@NonNull View itemView) {
            super(itemView);
            seek_item_image = itemView.findViewById(R.id.seek_item_image);
            seek_item_title = itemView.findViewById(R.id.seek_item_title);
            seek_item_price = itemView.findViewById(R.id.seek_item_price);
            seek_item_saleNum = itemView.findViewById(R.id.seek_item_saleNum);
        }
    }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
}
