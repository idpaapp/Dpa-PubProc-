package dpa_me.com.dpa_pubproc.Activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class About extends Fragment {
    ImageView MenuButton;
    int ImageResource;

    public About(){
        ImageResource = 0;
    }

    public About(int imageResource){
        ImageResource = imageResource;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);

        PubProc.mFragmentManager = getChildFragmentManager();

        MenuButton = view.findViewById(R.id.MenuButton);
        if (MenuButton != null) {
            MenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PubProc.mDrawerLayout.openDrawer(PubProc.mDrawerList);
                }
            });
        }

        if (ImageResource != 0) {
            ((ImageView) view.findViewById(R.id.backImage)).setImageResource(ImageResource);
            view.findViewById(R.id.abmMenuLayout).setVisibility(View.GONE);
        }
        return view;
    }
}