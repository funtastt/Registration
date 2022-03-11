package com.example.stocks.ui.top_up;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stocks.User;
import com.example.stocks.databinding.FragmentTopUpBinding;
import com.example.stocks.sqlite.UserCredentialsDatabaseHandler;

import java.util.Date;

public class TopUpFragment extends Fragment {

    private FragmentTopUpBinding binding;
    private UserCredentialsDatabaseHandler mHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TopUpViewModel topUpViewModel =
                new ViewModelProvider(this).get(TopUpViewModel.class);

        binding = FragmentTopUpBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textTopUp;
        topUpViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        mHandler = new UserCredentialsDatabaseHandler(getContext());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        User currentUser = mHandler.getUser();
        currentUser.setLastLoginDate(new Date().getTime());
        mHandler.updateUser(currentUser);
    }
}