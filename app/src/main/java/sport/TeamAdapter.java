package sport;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.soa4id.tec.jnavarro.sportec.NewsActivity;
import com.soa4id.tec.jnavarro.sportec.R;

import com.soa4id.tec.jnavarro.sportec.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewTeamHolder> {



    public class ViewTeamHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDescription;
        public CardView mCardView;

        /**
         * Bind to Xml
         * @param newsView
         */
        public ViewTeamHolder(View newsView){
            super(newsView);
            mTitle = itemView.findViewById(R.id.team_item_textItemHead);
            mDescription =  itemView.findViewById(R.id.team_item_textItemDescription);
            mImageView = itemView.findViewById(R.id.team_item_cardview_image);
            mCardView = itemView.findViewById(R.id.card_team);
        }

    }


    private List<TeamItem> mTeamList;
    private Context mContext;

    public TeamAdapter(List<TeamItem> mTeamList, Context mContext) {
        this.mTeamList = mTeamList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewTeamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.teamitem,parent,false);
        return new ViewTeamHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamAdapter.ViewTeamHolder holder, int position) {
        final TeamItem item = mTeamList.get(position);
        holder.mTitle.setText(item.getmTitle());
        holder.mDescription.setText(item.getmDescription());
        Picasso.get()
                .load(item.getmURIphoto())
                .resize(100, 100)
                .centerCrop()
                .into(holder.mImageView);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("JSON Card Team", "intent");

            }
        });
    }


    @Override
    public int getItemCount() {
        return mTeamList.size();
    }
}
