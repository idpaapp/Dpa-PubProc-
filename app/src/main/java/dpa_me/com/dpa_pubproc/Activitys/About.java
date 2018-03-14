package dpa_me.com.dpa_pubproc.Activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dpa_me.com.dpa_pubproc.R;
import dpa_me.com.dpa_pubproc.Units.PubProc;

public class About extends Fragment {
    ImageView MenuButton;
    int ImageResource;
    TextView todotype;

    public About(){
        ImageResource = 0;
    }

    public static About newInstance(int imageResource) {
        About fragment = new About();
        Bundle args = new Bundle();
        args.putInt("imageResource", imageResource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about, container, false);

        PubProc.mFragmentManager = getChildFragmentManager();
        todotype = view.findViewById(R.id.todotype);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.getString("mCaption") != null)
                todotype.setText(bundle.getString("mCaption"));

            if (bundle.getInt("imageResource") != 0)
                ((ImageView) view.findViewById(R.id.backImage)).setImageResource(bundle.getInt("imageResource"));
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}