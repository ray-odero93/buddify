package com.guritchistudios.buddify;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UsersFragment extends Fragment {

    RecyclerView recyclerView;
    UsersAdapters adapterUsers;
    List<UsersModel> usersList;
    FirebaseAuth firebaseAuth;

    public UsersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recyclep);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        usersList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        getAllUsers();
        return view;
    }

    private void getAllUsers() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UsersModel modelUsers = dataSnapshot1.getValue(UsersModel.class);
                    assert modelUsers != null;
                    if (modelUsers.getUid() != null) {
                        assert firebaseUser != null;
                        if (!modelUsers.getUid().equals(firebaseUser.getUid())) {
                            usersList.add(modelUsers);
                        }
                    }
                    adapterUsers = new UsersAdapters(getActivity(), usersList);
                    recyclerView.setAdapter(adapterUsers);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchUsers(final String s) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    UsersModel modelUsers = dataSnapshot1.getValue(UsersModel.class);
                    assert firebaseUser != null;
                    assert modelUsers != null;
                    if (!modelUsers.getUid().equals(firebaseUser.getUid())) {
                        if (modelUsers.getName().toLowerCase().contains(s.toLowerCase()) ||
                                modelUsers.getEmail().toLowerCase().contains(s.toLowerCase())) {
                            usersList.add(modelUsers);
                        }
                    }
                    adapterUsers = new UsersAdapters(getActivity(), usersList);
                    adapterUsers.notifyDataSetChanged();
                    recyclerView.setAdapter(adapterUsers);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
}