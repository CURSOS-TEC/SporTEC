package sport;

public class TeamItem {
    private String mId;
    private String mTitle;
    private String mDescription;
    private String mCategory;
    private String mURIphoto;
    private String mMembers;

    /**
     * Constructor
     * @param mId
     * @param mTitle
     * @param mDescription
     * @param mCategory
     * @param mURIphoto
     * @param mMembers
     */
    public TeamItem(String mId, String mTitle, String mDescription, String mCategory, String mURIphoto, String mMembers) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mURIphoto = mURIphoto;
        this.mMembers = mMembers;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmURIphoto() {
        return mURIphoto;
    }

    public void setmURIphoto(String mURIphoto) {
        this.mURIphoto = mURIphoto;
    }

    public String getmMembers() {
        return mMembers;
    }

    public void setmMembers(String mMembers) {
        this.mMembers = mMembers;
    }
}
