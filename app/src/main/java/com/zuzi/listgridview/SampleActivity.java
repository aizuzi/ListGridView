package com.zuzi.listgridview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zuzi.listgridview.view.ListGridView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleActivity extends AppCompatActivity {

    private ListViewCompat mListView;

    private final MAdapter mAdapter = new MAdapter();

    private String[] urls = new String[]{
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/umbrella.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/rubber-duck.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/polaroid-socialmatic.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/pan.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/sharpner.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/wind-sock.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/ps4-controller.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/sunglasses.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/meet.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/power-plant.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/new-mac-pro.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/printer-2.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/support.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/owl.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/snowman.png",
            "http://d.lanrentuku.com/down/png/1510/bianping-shenghuoicon/radio-4.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        Fresco.initialize(this);

        initListView();
    }

    private void initListView(){
        mListView = (ListViewCompat) findViewById(R.id.listView);
        mListView.setAdapter(mAdapter);
    }

    public class MAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup group) {
            ListGridView<GridItemBean> listGridView = new ListGridView(getApplicationContext());
            listGridView.setHorizontalSpacing(dp2px(15));
            listGridView.setVerticalSpacing(dp2px(15));
            listGridView.setGridViewAdapter(new ListGridView.GridViewAdapter<GridItemBean>() {
                @Override
                public void updateItem(GridItemBean item, View itemView, int position) {
                    TextView trainer_name = (TextView) itemView.findViewById(R.id.username_tv);
                    SimpleDraweeView trainer_avatar = (SimpleDraweeView) itemView.findViewById(R.id.user_avatar);
                    trainer_name.setText(item.getName());
                    trainer_avatar.setImageURI(Uri.parse(""+item.getAvatarUrl()));
                }
            });
            listGridView.refreshData(getDatds(i),R.layout.item_user);
            return listGridView;
        }
    }

    public List<GridItemBean> getDatds(int position){
        List<GridItemBean> lists = new ArrayList<>();
        int size =  new Random().nextInt(10);
        for (int i = 0; i < size; i++) {
            GridItemBean gridItemBean = new GridItemBean();
            gridItemBean.setAvatarUrl(urls[new Random().nextInt(urls.length)]);
            gridItemBean.setName(" Item "+i);
            lists.add(gridItemBean);
        }
        return lists;
    }


    public int dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static class GridItemBean{
        private String name;
        private String avatarUrl;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
