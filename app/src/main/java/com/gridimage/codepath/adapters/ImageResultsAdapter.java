package com.gridimage.codepath.adapters;

import android.content.Context;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gridimage.codepath.activities.R;
import com.gridimage.codepath.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by mkrish4 on 10/16/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {
    private static class ViewHolder {
        public ImageView ivImage;
        public TextView tvTitle;
    }

    public ImageResultsAdapter(Context context, List<ImageResult> objects) {
        super(context, R.layout.item_image_result, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ImageResult imageResult = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate data into the template view using the data object
        viewHolder.tvTitle.setText(Html.fromHtml(imageResult.title));
        viewHolder.ivImage.setImageResource(0);
        Picasso.with(getContext()).load(Uri.parse(imageResult.fullUrl)).fit().placeholder(R.mipmap.ic_launcher).into(viewHolder.ivImage);
        // Return the completed view to render on screen
        return convertView;
    }
}
