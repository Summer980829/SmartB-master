package my.edu.tarc.smartb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class BookingAdapter extends ArrayAdapter<BookingList> {

    public BookingAdapter(Activity context, int resource, List<BookingList> list) {
        super(context, resource, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BookingList bookingList = getItem(position);

        LayoutInflater inflater  = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.adapter_view_bookinglist, parent, false);

        TextView studID, bookingDate, startTime, endTime, venue, sportType, courtNo;

        studID = rowView.findViewById(R.id.tvStudID);
        bookingDate = rowView.findViewById(R.id.bookingDate);
        startTime = rowView.findViewById(R.id.startTime);
        endTime = rowView.findViewById(R.id.endTime);
        venue = rowView.findViewById(R.id.venueBooked);
        sportType = rowView.findViewById(R.id.sportType);
        courtNo = rowView.findViewById(R.id.courtNo);

        studID.setText("Student ID" + ": " + bookingList.getStudID());
        bookingDate.setText("Date" + ": " +bookingList.getDate());
        startTime.setText("Start time" + ": " +bookingList.getStartTime());
        endTime.setText("End time" + ": " +bookingList.getEndTime());
        venue.setText("Venue" + ": " +bookingList.getVenue());
        sportType.setText("Sport type" + ": " +bookingList.getSportType());
        courtNo.setText("Court No"+ ": " +bookingList.getCourtNo());

        return rowView;
    }
}
