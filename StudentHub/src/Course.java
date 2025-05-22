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

}
