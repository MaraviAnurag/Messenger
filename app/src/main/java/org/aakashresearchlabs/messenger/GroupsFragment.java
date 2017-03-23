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

/**
 * Created by anuragmaravi on 18/03/17.
 */

public class GroupsFragment extends Fragment {
    Toolbar toolbar;
    String[] groupNames={"Group 1","Group 2","Group 3","Group 4","Group 5","Group 6"};
    AdapterRecyclerGroup madapterRecyclerGroup;
    public GroupsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.recyclerViewGroups);
        toolbar=(Toolbar)rootView.findViewById(R.id.tool_group);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        madapterRecyclerGroup=new AdapterRecyclerGroup(groupNames,getActivity());
        rv.setAdapter(madapterRecyclerGroup);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
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

