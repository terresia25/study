package com.example.fit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter_calorie extends BaseAdapter {
    //listViewItemList : 데이터를 저장하기 위한 ArrayList
    //ListViewItem : 아이템 저장하는 getter,setter 클래스

    private ArrayList<ListViewItem_calorie> listViewItemListCalorie = new ArrayList<ListViewItem_calorie>();



    // ListViewAdapter의 생성자
    public ListViewAdapter_calorie(){

    }

    // position : 리턴할 자식 뷰의 위치
    //convertView :  메소드 호출 시, position에 위치하는 자식 뷰
    //parent : 리턴할 부모 뷰, 어댑터 뷰


    //position에 위치한 데이터를 화면에 출력하는데 사용될 view 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_calorie, parent, false);
        }

        //list_item에 정의된 게시글제목이랑 작성자 참조
        TextView calorie = (TextView)convertView.findViewById(R.id.calorie);
        TextView menu = (TextView)convertView.findViewById(R.id.menu);

        ListViewItem_calorie menandcal = getItem(position);
        ListView rl = (ListView)convertView.findViewById(R.id.resultlist);

        calorie.setText(menandcal.getCalorie());
        menu.setText(menandcal.getMenu());


        final ListView BlistView = ((CalorieActivity) CalorieActivity.context_main).Blistview;
        BlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(view.getContext(), CaloriesSum.class);
                //Log.w("zzz","제발 나와라요~~~"+listViewItemList.get(position).getMenu());


                intent.putExtra("menu", listViewItemListCalorie.get(position).getMenu() );
                intent.putExtra("calorie", listViewItemListCalorie.get(position).getCalorie());
                intent.putExtra("position", position);

                context.startActivity(intent);

            }
        });




        return convertView;
    }


    //게시글 데이터 추가를 위한 함수
    public void addItem(String men, String cal)
    {
        ListViewItem_calorie menandcal = new ListViewItem_calorie();

        menandcal.setCalorie(cal);
        menandcal.setMenu(men);

        listViewItemListCalorie.add(menandcal);
    }




    //Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount(){
        return listViewItemListCalorie.size();
    }

    //지정한 position에 있는 데이터 리턴
    @Override
    public ListViewItem_calorie getItem(int position) {
        return listViewItemListCalorie.get(position);
    }
    //지정한 position에 있는 데이터와 관계된 아이템이 id리턴
    @Override
    public long getItemId(int position) {
        return position;
    }


}