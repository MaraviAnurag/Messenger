package org.aakashresearchlabs.messenger;



import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;

import android.view.Menu;

import android.view.MenuInflater;

import android.view.MenuItem;

import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**

 * Created by anuragmaravi on 18/03/17.

 */



public class GroupsFragment extends Fragment {
    ListView listView;
    Toolbar toolbar;
    private AdapterRecyclerGroup arrayAdapter;
    AdapterRecyclerGroup madapterRecyclerGroup;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    private List<String> list_of_rooms=new ArrayList<>();
    public GroupsFragment() {

    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);

        toolbar=(Toolbar)rootView.findViewById(R.id.tool_group);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        arrayAdapter=new AdapterRecyclerGroup(getActivity(),R.layout.groupcard,list_of_rooms);
        listView=(ListView)rootView.findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
//        Map<String,Object> map = new HashMap<String, Object>();
//        root.updateChildren(map);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }

                list_of_rooms.clear();
                list_of_rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(),chatRoom.class);
                intent.putExtra("room_name",((TextView)view).getText().toString() );
                intent.putExtra("user_name","hul");
                startActivity(intent);
            }
        });
        root.keepSynced(true);
        setHasOptionsMenu(true);

        return rootView;

    }



    @Override

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.group_menu,menu);

        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.action_announcements)

        {

            Intent announce_int=new Intent(getActivity(),announcements.class);

            startActivity(announce_int);

        }

        return super.onOptionsItemSelected(item);

    }

}