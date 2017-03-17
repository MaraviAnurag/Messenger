package org.aakashresearchlabs.messenger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by anuragmaravi on 18/03/17.
 */

public class DocumentsFragment extends Fragment {
    public DocumentsFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_documents, container, false);
    }
}

