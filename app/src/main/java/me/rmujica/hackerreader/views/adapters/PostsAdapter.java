package me.rmujica.hackerreader.views.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.rmujica.hackerreader.R;
import me.rmujica.hackerreader.databinding.DetailPostBinding;
import me.rmujica.hackerreader.models.Post;
import me.rmujica.hackerreader.viewmodels.PostViewModel;

/**
 * Created by rene on 1/12/16.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.BindingHolder> {

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context) {
        this.context = context;
        posts = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DetailPostBinding postBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.detail_post,
                parent,
                false);

        return new BindingHolder(postBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        DetailPostBinding postBinding = holder.binding;
        postBinding.setViewModel(new PostViewModel(posts.get(position)));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    public void clearPosts() {
        while (posts.size() > 0) {
            posts.remove(0);
            notifyItemRemoved(0);
        }
    }

    public void addPost(Post post) {
        this.posts.add(post);
        notifyItemInserted(this.posts.size() - 1);
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private final DetailPostBinding binding;

        public BindingHolder(DetailPostBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
