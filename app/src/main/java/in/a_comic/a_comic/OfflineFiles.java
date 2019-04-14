package in.a_comic.a_comic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import in.a_comic.a_comic.util.MyActivity;

import static in.a_comic.a_comic.Dashboard.APPFOLDER;

public class OfflineFiles extends MyActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<OfflineFileDataModel> fileList = new ArrayList<>();
    private ProgressDialog progressDialog;

    public static String subDirectory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_files);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        loadAdvertisement();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar23);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.offlinefile_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new OfflineFileAdapter(new OfflineFileAdapter.RecyclerClickListener() {
            @Override
            public void onClick(View view, int adapterPosition) {
                File pdfFile = new File(fileList.get(adapterPosition).getFile().getPath());//File path

                PdfViewActivity.str = "file";
                PdfViewActivity.file = pdfFile;
                Intent intent = new Intent(OfflineFiles.this,PdfViewActivity.class);
                startActivity(intent);
                showMyAdd(null,false);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                Toast.makeText(getApplicationContext(),pdfFile.toString(),Toast.LENGTH_LONG).show();
//
//                String newFilePath = pdfFile.toString().replaceAll("%20", " ");
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                    intent.setDataAndType(Uri.parse(newFilePath), "application/pdf");
//                } else {
//                    Uri uri = Uri.parse(newFilePath);
//                    File file = new File(uri.getPath());
//                    if (file.exists()) {
//                        uri = FileProvider.getUriForFile(getApplication().getApplicationContext(),
//                                "ibas.provider", file);
//                        intent.setDataAndType(uri, "application/pdf");
//                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    }
//                }
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                startActivity(intent);

            }
        }, new OfflineFileAdapter.RecyclerLongClickListener() {
            @Override
            public void onClick(View view, final int adapterPosition) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(OfflineFiles.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(OfflineFiles.this);
                }
                builder.setTitle("Delete")
                        .setMessage("Are you sure you want to delete this file?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                fileList.get(adapterPosition).getFile().delete();
                                Toast.makeText(getApplicationContext(),"deleted successfully!",Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }
                , fileList, OfflineFiles.this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        loadFileFromSdcard();
    }

    private void loadFileFromSdcard() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + APPFOLDER + subDirectory +"/");

        File list[] = file.listFiles();
        for( int i=0; i< list.length; i++)
        {
            String lastModDate = new Date(list[i].lastModified()).toString();
            lastModDate = lastModDate.substring(0,lastModDate.length()-15);

            double file_size = Double.parseDouble(String.valueOf(list[i].length()/(1024.0*1024.0)));
            String str = String.valueOf(new DecimalFormat("##.###").format(file_size));

            fileList.add(new OfflineFileDataModel(list[i].getName(),str+" MB",lastModDate,list[i]));
        }
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
