package dpa_me.com.dpa_pubproc.Units;

public class MenuModel {
 
    private int icon;
    private int title;
    private String counter;
    private boolean Enabled;

    private boolean isGroupHeader = false;
    private boolean isProfileImage = false;

	public MenuModel() {
		super();
		isProfileImage = true;
		Enabled = true;
	}

	public MenuModel(int title) {
		this(-1,title,null);
		isGroupHeader = true;
		Enabled = true;
	}

	public MenuModel(int icon, int title, String counter) {
		super();
		this.icon = icon;
		this.title = title;
		this.counter = counter;
		Enabled = true;
	}

	public MenuModel(int icon, int title, String counter, boolean mEnabled) {
		super();
		this.icon = icon;
		this.title = title;
		this.counter = counter;
		Enabled = mEnabled;
	}

	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	
	public int getTitle() {
		return title;
	}
	public void setTitle(int title) {
		this.title = title;
	}
	public String getCounter() {
		return counter;
	}
	public void setCounter(String counter) {
		this.counter = counter;
	}
	public boolean isProfileImage() {
		return isProfileImage;
	}
	public void setProfileImage(boolean isProfileImage) {
		this.isProfileImage = isProfileImage;
	}
	
	public boolean isGroupHeader() {
		return isGroupHeader;
	}
	public void setGroupHeader(boolean isGroupHeader) {
		this.isGroupHeader = isGroupHeader;
	}

	public boolean isEnabled(){
		return Enabled;
	}
	
	
	
}