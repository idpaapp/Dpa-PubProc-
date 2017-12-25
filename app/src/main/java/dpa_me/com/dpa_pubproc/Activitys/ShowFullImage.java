package dpa_me.com.dpa_pubproc.Activitys;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class ShowFullImage extends AppCompatActivity {
    private Context mContext;
    private String ImagePath;
    private boolean FullView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = PubProc.HandleApplication.SetActivityParams(this, R.layout.activity_show_full_image, false, "");
        FullView = false;

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("ImagePath")!= null)
            ImagePath = bundle.getString("ImagePath");

        final RelativeLayout suMainMenuLyout = (RelativeLayout) findViewById(R.id.suMainMenuLyout);
        final ImageView sfiImage = (ImageView) findViewById(R.id.sfiImage);
        Picasso.with(mContext).load(ImagePath).into(sfiImage);

        assert sfiImage != null;
        sfiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (FullView){
                   suMainMenuLyout.setVisibility(View.VISIBLE);
                   sfiImage.setBackgroundColor(getResources().getColor(R.color.divider_light));
                   FullView = false;
               }
                else{
                    suMainMenuLyout.setVisibility(View.GONE);
                    sfiImage.setBackgroundColor(getResources().getColor(R.color.primary_text));
                   FullView = true;
               }
            }
        });
    }
}
