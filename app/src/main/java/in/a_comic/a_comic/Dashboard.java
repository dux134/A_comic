package in.a_comic.a_comic;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Dashboard extends AppCompatActivity {
    public final static String APPFOLDER = "Acomic/";

    private ProgressDialog progressDialog;
    private RecyclerView recyclerView,folderRecycler;
    private RecyclerView.Adapter adapter,folderAdapter;
    private ArrayList<DashboardCategoryModel> list = new ArrayList<>();
    private ArrayList<String> folderList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        createAppFolder();
        loadListFromFireStore();

        recyclerView = findViewById(R.id.dashboard_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new DashboardAdapter(new DashboardAdapter.RecyclerClickListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                SelectedCategory.document = list.get(adapterPosition).getDocument();
                SelectedCategory.title = list.get(adapterPosition).getTitle();
                startActivity(new Intent(Dashboard.this,SelectedCategory.class));
                finish();
            }
        },list,Dashboard.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        folderRecycler = findViewById(R.id.folder_recycler);
        folderRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        folderAdapter = new OfflineFolderAdapter(new OfflineFolderAdapter.RecyclerClickListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                OfflineFiles.subDirectory = folderList.get(adapterPosition);
                startActivity(new Intent(Dashboard.this,OfflineFiles.class));
            }
        },folderList,Dashboard.this);
        folderRecycler.setAdapter(folderAdapter);
        folderRecycler.setItemAnimator(new DefaultItemAnimator());
        loadFolderList();


    }

    private void loadFolderList() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + APPFOLDER);

        File list[] = file.listFiles();

        String str = null;
        for( int i=0; i< list.length; i++)
        {
            folderList.add(list[i].getName());
        }
        folderAdapter.notifyDataSetChanged();
    }

    private void createAppFolder() {
        String  folder = Environment.getExternalStorageDirectory() + File.separator + APPFOLDER;

        //Create androiddeft folder if it does not exist
        File file = new File(folder);

        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void loadListFromFireStore() {
        list.clear();
        final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        db.collection("comic_category").orderBy("priority")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot querySnapshot,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen error", e);
                            return;
                        }


                        assert querySnapshot != null;
                        for (DocumentChange change : querySnapshot.getDocumentChanges()) {
                            if (change.getType() == DocumentChange.Type.ADDED) {
                                String image = change.getDocument().get("image_url").toString();
                                String noOfFiles = change.getDocument().get("no_of_files").toString();
                                String views = change.getDocument().get("views").toString();
                                String title = change.getDocument().get("title").toString();
                                String document = change.getDocument().get("document").toString();

                                list.add(new DashboardCategoryModel(title,views,noOfFiles,image,document));
                                adapter.notifyDataSetChanged();
                            }

                            String source = querySnapshot.getMetadata().isFromCache() ?
                                    "local cache" : "server";
                            Log.d(TAG, "Data fetched from " + source);
                        }
                        progressDialog.dismiss();
                    }
                });
    }
}
