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

public class SelectedCategoryAdapter extends RecyclerView.Adapter<SelectedCategoryAdapter.SelectedCategoryViewHolder> {
    private SelectedCategoryAdapter.RecyclerClickListener listener;
    private SelectedCategoryAdapter.RecyclerLongClickListener longClickListener;
    private ArrayList<ComicCardDataModel> list;
    private Context context;

    public SelectedCategoryAdapter(RecyclerClickListener listener, RecyclerLongClickListener longClickListener, ArrayList<ComicCardDataModel> list, Context context) {
        this.listener = listener;
        this.longClickListener = longClickListener;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectedCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selected_category,parent,false);
        SelectedCategoryViewHolder holder = new SelectedCategoryViewHolder(view, listener,longClickListener);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedCategoryViewHolder holder, int position) {

        Glide.with(context)
                .load(list.get(position).getImage_url())
                .into(holder.imageView);

        holder.views.setText(list.get(position).getViews());
        holder.title.setText(list.get(position).getTitle());
        holder.description.setText(list.get(position).getDescription());
        holder.size.setText(list.get(position).getSize());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerLongClickListener {
        void onClick(View view, int adapterPosition);
    }

    public class SelectedCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView title,views,size,description;
        private ImageView imageView;
        private SelectedCategoryAdapter.RecyclerClickListener lv;
        private SelectedCategoryAdapter.RecyclerLongClickListener longClickListener;
        public SelectedCategoryViewHolder(View itemView, RecyclerClickListener listener, final SelectedCategoryAdapter.RecyclerLongClickListener longClickListener) {
            super(itemView);
            title = itemView.findViewById(R.id.comic_title);
            views = itemView.findViewById(R.id.comic_view);
            size = itemView.findViewById(R.id.comic_size);
            description = itemView.findViewById(R.id.comic_description);
            imageView = itemView.findViewById(R.id.comic_image);

            lv = listener;
            this.longClickListener = longClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lv.onClick(view,getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onClick(view,getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public interface RecyclerClickListener {
        void onClick(View view, int adapterPosition);
    }
}
