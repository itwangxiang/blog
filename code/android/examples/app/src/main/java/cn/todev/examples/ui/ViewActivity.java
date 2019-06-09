package cn.todev.examples.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.todev.examples.R;

public class ViewActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.sv_hello)
    SurfaceView svHello;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ButterKnife.bind(this);






        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] dataSet = new String[]{"1", "2", "3", "4"};
        mAdapter = new MyAdapter(dataSet);
        recyclerView.setAdapter(mAdapter);
    }

    static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            MyViewHolder(View v) {
                super(v);

                textView = v.findViewById(R.id.text);
            }
        }

        private String[] mDataset;

        MyAdapter(String[] myDataset) {
            mDataset = myDataset;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.textView.setText(mDataset[position]);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }

    }
}
