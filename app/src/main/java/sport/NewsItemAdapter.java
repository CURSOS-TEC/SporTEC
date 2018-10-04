package sport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.soa4id.tec.jnavarro.sportec.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView  mTitle;
        public TextView  mShortDescription;

        public ViewHolder(View newsView){
            super(newsView);
            mTitle = (TextView) itemView.findViewById(R.id.textItemHead);
            mShortDescription = (TextView) itemView.findViewById(R.id.textItemDescription);
            mImageView = (ImageView) itemView.findViewById(R.id.cardview_image);
        }

    }

    private List<NewsItem> mNewsList;
    private Context mContext;

    /**
     * Constructor
     * @param mNewsList
     * @param mContext
     */
    public NewsItemAdapter(List<NewsItem> mNewsList, Context mContext) {
        this.mNewsList = mNewsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newsitem,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsItem item = mNewsList.get(position);
        holder.mTitle.setText(item.getmTitle());
        holder.mShortDescription.setText(item.getmShortDescription());
        /*Picasso*/
        Picasso.get()
                .load(item.getmURIphoto())
                .resize(100, 100)
                .centerCrop()
                .into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }
}
