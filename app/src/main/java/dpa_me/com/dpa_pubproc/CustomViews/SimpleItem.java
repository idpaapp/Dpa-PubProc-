package dpa_me.com.dpa_pubproc.CustomViews;

public class SimpleItem{
    public int id;
    public String Title;
    public String Cost;
    public String Description;
    public int Count;

    public SimpleItem(int _id, String _title){
        id = _id;
        Title = _title;
    }

    public SimpleItem(int _id, String _title, int _count){
        id = _id;
        Title = _title;
        Count = _count;
    }

    public SimpleItem(int id, String title, String cost, String description) {
        this.id = id;
        Title = title;
        Cost = cost;
        Description = description;
    }
}
