package shineloading.hansheng.com.nbadatabinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import shineloading.hansheng.com.nbadatabinding.databinding.ActivityNbaBinding;
import shineloading.hansheng.com.nbadatabinding.databinding.NbaItemBinding;
import shineloading.hansheng.com.nbadatabinding.model.Nba;

/**
 * Created by hansheng on 2016/5/22.
 */
public class NbaActivity extends AppCompatActivity{
    private ActivityNbaBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nba);
        binding.toolbar.setTitle("NBA");
        setSupportActionBar(binding.toolbar);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        NBA();
    }

    private void NBA() {
        binding.progressBar.setVisibility(View.VISIBLE);
         Nba.searchMovies(new Nba.INbaResponse<List<Nba>>() {
            @Override
            public void onData(List<Nba> books) {
                MyAdapter mAdapter = new MyAdapter(NbaActivity.this, books);
                binding.recyclerView.setAdapter(mAdapter);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.BindingHolder> {
        //private final int mBackground;
        private List<Nba> nba;

        private final TypedValue mTypedValue = new TypedValue();


        public class BindingHolder extends RecyclerView.ViewHolder {
            private NbaItemBinding binding;

            public BindingHolder(View v) {
                super(v);
            }

            public NbaItemBinding getBinding() {
                return binding;
            }

            public void setBinding(NbaItemBinding binding) {
                this.binding = binding;
            }
        }

        public MyAdapter(Context context, List<Nba> nba) {
            this.nba = nba;
        }

        @Override
        public MyAdapter.BindingHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
            NbaItemBinding binding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()),
                    R.layout.nba_item,
                    parent,
                    false);
            BindingHolder holder = new BindingHolder(binding.getRoot());
            holder.setBinding(binding);
            return holder;
        }

        @Override
        public void onBindViewHolder(BindingHolder holder, int position) {
            Nba mnba= nba.get(position);
            List<String> img=mnba.getImgUrlList();
            if(img.isEmpty()){
                return;
            }
            else{
              //  System.out.println("img========"+img.get(0)+news.getTitle()+news.getDescription()+news.getImgUrlList().toString());
              //  ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).mNewsImg,img.get(0));
                Glide.with(NbaActivity.this)
                        .load(img.get(0))
                        .fitCenter()
                        .into(holder.binding.ivNews);
            }


            holder.binding.setVariable(shineloading.hansheng.com.nbadatabinding.BR.nba, mnba);
            holder.binding.executePendingBindings();
        }

        @Override
        public int getItemCount() {
            return nba.size();
        }
    }
}
