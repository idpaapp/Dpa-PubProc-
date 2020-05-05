package dpa_me.com.dpa_pubproc.Units;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dpa_me.com.dpa_pubproc.R;

public class MenuAdapter extends ArrayAdapter<MenuModel> {
 
        private final Context context;
        private ArrayList<MenuModel> modelsArrayList;
        public MenuAdapter(Context context, ArrayList<MenuModel> modelsArrayList) {
 
            super(context, R.layout.menuitem, modelsArrayList);
            this.context = context;
            this.modelsArrayList = new ArrayList<>();
            this.modelsArrayList.addAll(modelsArrayList);
        }
 
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
            View rowView = null;
            if(!modelsArrayList.get(position).isGroupHeader() && !modelsArrayList.get(position).isProfileImage()){
                rowView = inflater.inflate(R.layout.menuitem, parent, false);
 
                AppCompatImageView imgView = rowView.findViewById(R.id.item_icon);
                TextView titleView = rowView.findViewById(R.id.item_title);

                if (!modelsArrayList.get(position).isEnabled()) {
                    titleView.setTextColor(context.getResources().getColor(R.color.secondary_text));
                    imgView.setColorFilter(getContext().getResources().getColor(R.color.secondary_text));
                }

                imgView.setImageResource(modelsArrayList.get(position).getIcon());
                titleView.setText(context.getResources().getString(modelsArrayList.get(position).getTitle())+ " ");
            }
            else{
            	if(modelsArrayList.get(position).isGroupHeader())
            	{
                    rowView = inflater.inflate(R.layout.group_header_item, parent, false);
                    TextView titleView = rowView.findViewById(R.id.header);
                    titleView.setText(context.getResources().getString(modelsArrayList.get(position).getTitle())+ " ");
            	}
            }
            PubProc.HandleViewAndFontSize.overrideFonts(context, rowView);
            return rowView;
        }

    @Override
    public int getCount() {
        return this.modelsArrayList.size();
    }

    public void Refresh(ArrayList<MenuModel> mModelsArrayList){
        modelsArrayList.clear();
        modelsArrayList.addAll(mModelsArrayList);
        notifyDataSetChanged();
    }
}
