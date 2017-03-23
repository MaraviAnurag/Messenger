package org.aakashresearchlabs.messenger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by anuragmaravi on 18/03/17.
 */

public class ProfileFragment extends Fragment {
    View rootView;
    public ProfileFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        Toolbar profile_toolbar = (Toolbar) rootView.findViewById(R.id.tool_profile);
        ((AppCompatActivity) getActivity()).setSupportActionBar(profile_toolbar);
        profile_toolbar.setTitle("My Profile");
        setHasOptionsMenu(true);
        return rootView;

    }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.profile_menu,menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if(id==R.id.action_logout)
            {
                Toast.makeText(getActivity(), "Log out...", Toast.LENGTH_SHORT).show();
            }
            return super.onOptionsItemSelected(item);
        }
    }
