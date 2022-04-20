package com.example.fit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    //listViewItemList : 데이터를 저장하기 위한 ArrayList
    //ListViewItem : 아이템 저장하는 getter,setter 클래스
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    // ListViewAdapter의 생성자
    public ListViewAdapter(){

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
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        //list_item에 정의된 게시글제목이랑 작성자 참조
        TextView tname = (TextView)convertView.findViewById(R.id.textId);
        TextView ttile = (TextView)convertView.findViewById(R.id.textTitle);

        ListViewItem titleandname = getItem(position);

        ttile.setText(titleandname.getTitle());
        tname.setText(titleandname.getName());


/*
게시글 클릭시 전문 뜨게 하는 이벤트 작성해야 됨
 */

       return convertView;
    }


    //게시글 데이터 추가를 위한 함수
    public void addItem(String title, String name)
    {
        ListViewItem titleandname = new ListViewItem();

        titleandname.setTitle(title);
        titleandname.setName(name);

        listViewItemList.add(titleandname);
    }




    //Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount(){
        return listViewItemList.size();
    }

//지정한 position에 있는 데이터 리턴
    @Override
    public ListViewItem getItem(int position) {
        return listViewItemList.get(position);
    }
//지정한 position에 있는 데이터와 관계된 아이템이 id리턴
    @Override
    public long getItemId(int position) {
        return position;
    }


}
