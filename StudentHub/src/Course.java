public class Course {
    String title, department, time;
    String[] days, semesters;
    int CRN, year, credits, seats;

    public Course (String in_title,  String in_department, int in_CRN, String in_time, String[] in_days, String[] in_semesters, int in_year, int in_credits, int in_seats){
        title = in_title;
        department = in_department;
        CRN = in_CRN;
        time = in_time;
        days = in_days;
        semesters = in_semesters;
        year = in_year;
        credits = in_credits;
        seats = in_seats;
    }

    public String GetTitle() {
        return title;
    }

    public String GetDepartment() {
        return department;
    }

    public String GetTime() {
        return time;
    }

    public String[] GetDays() {
        return days;
    }

    public String[] GetSemesters() {
        return semesters;
    }

    public int GetCRN() {
        return CRN;
    }

    public int GetYear() {
        return year;
    }

    public int GetCredits() {
        return credits;
    }

    public int GetSeats() {
        return seats;
    }

    public void SetTitle(String in_title) {
        title = in_title;
    }

    public void SetDepartment(String in_department) {
        department = in_department;
    }

    public void SetTime(String in_time) {
        time = in_time;
    }

    public void SetDays(String[] in_days) {
        days = in_days;
    }

    public void SetSemesters(String[] in_semesters) {
        semesters = in_semesters;
    }

    public void SetYear(int in_year) {
        year = in_year;
    }

    public void SetCredits(int in_crn) {
        CRN = in_crn;
    }

    public void SetSeats(int in_seats) {
        CRN = in_seats;
    }
}
