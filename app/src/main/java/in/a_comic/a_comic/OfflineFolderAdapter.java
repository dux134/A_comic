package in.a_comic.a_comic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OfflineFolderAdapter extends RecyclerView.Adapter<OfflineFolderAdapter.OfflineFolderViewHolder> {
    private OfflineFolderAdapter.RecyclerClickListener listener;
    private ArrayList<String> list;
    private Context context;

    public OfflineFolderAdapter(OfflineFolderAdapter.RecyclerClickListener listener, ArrayList<String> list, Context context) {
        this.listener = listener;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OfflineFolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offline_folder,parent,false);
        OfflineFolderViewHolder holder = new OfflineFolderViewHolder(view, listener);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineFolderViewHolder holder, int position) {

        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerClickListener {
        void onClick(View view, int adapterPosition);
    }

    public class OfflineFolderViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private RecyclerClickListener lv;

        public OfflineFolderViewHolder(View itemView, RecyclerClickListener listener) {
            super(itemView);
            textView = itemView.findViewById(R.id.folder_textview);
            lv = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lv.onClick(view,getAdapterPosition());
                }
            });
        }
    }
}
