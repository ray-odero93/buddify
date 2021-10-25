package com.guritchistudios.buddify;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    String myuid;
    RecyclerView recyclerView;
    List<PostsModel> posts;
    PostsAdapters adapterPosts;

    public HomeFragment() {
    }
}