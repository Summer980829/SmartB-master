package my.edu.tarc.smartb;

public class BookingList {
    private String studID;
    private String venue;
    private String date;
    private String startTime;
    private String endTime;
    private String sportType;
    private String courtNo;

    public BookingList(String studID, String venue, String date, String startTime, String endTime, String sportType, String courtNo) {
        this.studID = studID;
        this.venue = venue;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sportType = sportType;
        this.courtNo = courtNo;
    }

    public BookingList() {
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getCourtNo() {
        return courtNo;
    }

    public void setCourtNo(String courtNo) {
        this.courtNo = courtNo;
    }

    public String getStudID() {
        return studID;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    @Override
    public String toString() {
        return "BookingList{" +
                "studID='" + studID + '\'' +
                ", venue='" + venue + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", sportType='" + sportType + '\'' +
                ", courtNo='" + courtNo + '\'' +
                '}';
    }
}
