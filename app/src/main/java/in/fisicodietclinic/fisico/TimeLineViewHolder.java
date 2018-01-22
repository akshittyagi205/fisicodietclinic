package in.fisicodietclinic.fisico;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

/**
 * Created by manyamadan on 23/11/17.
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder {
    public TimelineView mTimelineView;
    public TextView title, year, genre;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.text_title);
        genre = (TextView) itemView.findViewById(R.id.text_details);
        year = (TextView) itemView.findViewById(R.id.text_year);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }
}