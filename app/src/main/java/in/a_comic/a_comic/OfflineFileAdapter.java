package in.a_comic.a_comic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OfflineFileAdapter extends RecyclerView.Adapter<OfflineFileAdapter.OfflineFileViewHolder> {
    private OfflineFileAdapter.RecyclerClickListener listener;
    private RecyclerLongClickListener longClickListener;
    private ArrayList<OfflineFileDataModel> list;
    private Context context;

    public OfflineFileAdapter(RecyclerClickListener listener, RecyclerLongClickListener longClickListener, ArrayList<OfflineFileDataModel> list, Context context) {
        this.listener = listener;
        this.longClickListener = longClickListener;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OfflineFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offline_file,parent,false);
        OfflineFileViewHolder holder = new OfflineFileViewHolder(view, listener,longClickListener);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineFileViewHolder holder, int position) {
        String str = list.get(position).getTitle();
        str = str.substring(0,str.length()-4);
        holder.title.setText(str);
        holder.size.setText(list.get(position).getSize());
        holder.time.setText(list.get(position).getModifiedTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface RecyclerClickListener {
        void onClick(View view, int adapterPosition);
    }

    public interface RecyclerLongClickListener {
        void onClick(View view, int adapterPosition);
    }



    public class OfflineFileViewHolder extends RecyclerView.ViewHolder {
        private TextView title,size,time;
        private RecyclerClickListener listener;
        private RecyclerLongClickListener longClickListener2;
        public OfflineFileViewHolder(View itemView, final RecyclerClickListener listener, RecyclerLongClickListener longClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.offline_file_title);
            size = itemView.findViewById(R.id.offlinefile_size);
            time = itemView.findViewById(R.id.offlinefile_time);
            this.listener = listener;
            this.longClickListener2 = longClickListener;

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener2.onClick(view,getAdapterPosition());
                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(view,getAdapterPosition());
                }
            });
        }
    }
}
