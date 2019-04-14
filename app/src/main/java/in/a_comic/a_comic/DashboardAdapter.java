package in.a_comic.a_comic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> {
    private DashboardAdapter.RecyclerClickListener listener;
    private ArrayList<DashboardCategoryModel> list;
    private Context context;

    public DashboardAdapter(RecyclerClickListener listener, ArrayList<DashboardCategoryModel> list, Context context) {
        this.listener = listener;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DashboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dashboard_category,parent,false);
        DashboardViewHolder holder = new DashboardViewHolder(view, listener);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardViewHolder holder, int position) {
        if(!list.get(position).getImageUrl().equals(""))
            Glide.with(context)
                .load(list.get(position).getImageUrl())
                .into(holder.imageView);

        holder.noOfFiles.setText(list.get(position).getNoOfFiles());
        holder.views.setText(list.get(position).getViews());
        holder.title.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerClickListener {
        void onClick(View view, int adapterPosition);
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title,views,noOfFiles;
        private ImageView imageView;
        private RecyclerClickListener lv;

        public DashboardViewHolder(View itemView, RecyclerClickListener listener) {
            super(itemView);

            title = itemView.findViewById(R.id.category_title);
            views = itemView.findViewById(R.id.category_views);
            noOfFiles = itemView.findViewById(R.id.category_files);
            imageView = itemView.findViewById(R.id.category_imageView);

            lv = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            lv.onClick(view,getAdapterPosition());
        }
    }
}
