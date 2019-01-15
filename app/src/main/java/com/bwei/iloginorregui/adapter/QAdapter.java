package com.bwei.iloginorregui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.sbean.FindCircleList;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class QAdapter extends RecyclerView.Adapter<QAdapter.VH> {
    private List<FindCircleList> list = new ArrayList<>();
    private Context context;
    //方法名
    private OnItemClickListener onItemClickListener;

    public interface SingleRudderListener {
        void onSteeringWheelChanged(int action, int angle);
    }

    public SingleRudderListener listener = null; //事件回调接口

    public QAdapter(Context context) {
        this.context = context;
    }

    public void addItem(List<FindCircleList> circ) {
        if (circ != null) {
            list.addAll(circ);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.q_item_layout
                , viewGroup, false);
        return new VH(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH vh, final int i) {
        final FindCircleList findCircleList = list.get(i);
        int id = findCircleList.getId();
        vh.q_item_headPic.setImageURI(findCircleList.getHeadPic());
        vh.q_item_name.setText(findCircleList.getNickName());
        vh.q_item_time.setText(findCircleList.getCreateTime() + "");
        vh.q_item_content.setText(findCircleList.getContent());
        vh.q_item_image1.setImageURI(Uri.parse(findCircleList.getImage()));
        vh.q_item_greatNum.setText(findCircleList.getGreatNum() + "");
        vh.zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(findCircleList.getGreatNum(),findCircleList.getId());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private final SimpleDraweeView q_item_headPic;
        private final TextView q_item_name;
        private final TextView q_item_time;
        private final TextView q_item_content;
        private final TextView q_item_greatNum;
        private final ImageView q_item_image1;
        private final ImageView zan;

        public VH(@NonNull View itemView) {
            super(itemView);
            q_item_headPic = itemView.findViewById(R.id.q_item_headPic);
            q_item_name = itemView.findViewById(R.id.q_item_name);
            q_item_time = itemView.findViewById(R.id.q_item_time);
            q_item_content = itemView.findViewById(R.id.q_item_content);
            q_item_image1 = itemView.findViewById(R.id.q_item_image1);
            q_item_greatNum = itemView.findViewById(R.id.q_item_greatNum);
            zan = itemView.findViewById(R.id.zan);
        }
    }


    //定义接口
    public interface OnItemClickListener {
        void onItemClick(int num,int id);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
