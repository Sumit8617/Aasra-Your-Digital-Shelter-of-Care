package com.example.babycare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Use post() to ensure navigation occurs after the view is attached
        View emptyView = new View(requireContext());

        emptyView.post(() -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), Dashboard.class);
                startActivity(intent);
                getActivity().finish();  // Optional: close current activity if you don’t want backstack
            }
        });

        return emptyView;
    }
}
