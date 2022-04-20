package com.example.fit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> ElistViewItemList = new ArrayList<ListViewItem>();



    public EListViewAdapter(ArrayList<ListViewItem> data) {
        this.ElistViewItemList = data;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_sports, parent, false);

        }

        TextView exer_name = (TextView)convertView.findViewById(R.id.exercise_name);
        TextView exer_explain = (TextView) convertView.findViewById(R.id.explain_exercise);
        ImageView thumb_nail = (ImageView) convertView.findViewById(R.id.thumbnail);

        ListViewItem exercise_info = ElistViewItemList.get(position);

        exer_name.setText(exercise_info.getmName());
        exer_explain.setText(exercise_info.getExplain());
        thumb_nail.setImageDrawable(exercise_info.getIconExer());

        final ListView ElistView = ((TraininglistActivity) TraininglistActivity.context_main).Elistview;
        ElistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), TrainingActivity.class);

                intent.putExtra("position", position);

                context.startActivity(intent);

            }
        });




        return convertView;
    }

   /* public void addExerItem(Drawable icon,  String name, String explain){

        ListViewItem exercise_info = new ListViewItem();

        exercise_info.setExplain(explain);
        exercise_info.setIconExer(icon);
        exercise_info.setName(name);

        ElistViewItemList.add(exercise_info);
    }*/
    @Override
    public int getCount(){
        return ElistViewItemList.size();
    }


    //지정한 position에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return ElistViewItemList.get(position);
    }



    //지정한 position에 있는 데이터와 관계된 아이템이 id리턴
    @Override
    public long getItemId(int position) {
        return position;
    }
}
