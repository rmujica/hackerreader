package me.rmujica.hackerreader.viewmodels;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.Bundle;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.Date;

import me.rmujica.hackerreader.R;
import me.rmujica.hackerreader.models.Post;
import me.rmujica.hackerreader.views.activities.WebViewActivity;

/**
 * Created by rene on 1/12/16.
 */

public class PostViewModel extends BaseObservable {

    private Post post;
    private Context context;

    public PostViewModel(Post post, Context context) {
        this.post = post;
        this.context = context;
    }

    public String getPostAuthor() {
        return post.author;
    }

    public String getPostTitle() {
        if (post.title == null) {
            return post.story_title;
        } else {
            return post.title;
        }
    }

    public String getPostURL() {
        if (post.url == null) {
            return post.story_url;
        } else {
            return post.url;
        }
    }

    public String getDate() {
        String date;
        DateTime now = new DateTime(DateTimeZone.UTC);
        DateTime posted = new DateTime(post.created_at);

        Period p = new Period(posted, now);
        PeriodFormatter pft = new PeriodFormatterBuilder().appendHours().appendSuffix("h ").appendMinutes().appendSuffix("m ").toFormatter();
        if ((now.getMillis() - posted.getMillis()) < (60 * 1000))
            date = context.getResources().getString(R.string.now);
        else if ((now.getMillis() - posted.getMillis()) < (24 * 3600 * 1000))
            date = pft.print(p.normalizedStandard());
        else
            date = DateTimeFormat.forPattern("MMM d").withLocale(context.getResources().getConfiguration().locale).print(posted);

        return date;
    }

    public String getPostMeta() {
        return getPostAuthor() + " – " + getDate();
    }

    public View.OnClickListener openLink() {
        return view -> {
            Bundle b = new Bundle();
            b.putString("URL", getPostURL());
            b.putString("TITLE", getPostTitle());

            Intent intent = new Intent(context, WebViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtras(b);
            context.startActivity(intent);
        };
    }

}
