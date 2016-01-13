package me.rmujica.hackerreader.views.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import me.rmujica.hackerreader.R;
import me.rmujica.hackerreader.databinding.DetailPostBinding;
import me.rmujica.hackerreader.models.Post;
import me.rmujica.hackerreader.models.Post_Table;
import me.rmujica.hackerreader.viewmodels.PostViewModel;

/**
 * Created by rene on 1/12/16.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.BindingHolder> implements ItemTouchHelperAdapter {

    private Context context;
    private List<Post> posts;
    private Post lastRemoved;
    private Integer lastRemovedIndex;
    private RecyclerView list;

    public PostsAdapter(Context context, RecyclerView list) {
        this.context = context;
        this.list = list;
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
        postBinding.setViewModel(new PostViewModel(posts.get(position), context.getApplicationContext()));
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

    @Override
    public void onItemDismiss(int position) {
        lastRemoved = posts.remove(position);
        lastRemovedIndex = position;
        notifyItemRemoved(position);

        // query the db
        Post p = SQLite.select().from(Post.class)
                .where(Post_Table.objectID.eq(lastRemoved.objectID)).querySingle();
        SQLite.delete().from(Post.class)
                .where(Post_Table.objectID.eq(lastRemoved.objectID)).query();

        // show the undo stuff
        Snackbar.make(list, R.string.done_for_now, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.undo, view -> {
                    posts.add(lastRemovedIndex, lastRemoved);
                    notifyItemInserted(lastRemovedIndex);

                    if (lastRemovedIndex == 0) {
                        list.smoothScrollToPosition(0);
                    }

                    // insert again in db
                    TransactionManager.getInstance()
                            .addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(p)));
                }).show();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {
        private final DetailPostBinding binding;

        public BindingHolder(DetailPostBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
