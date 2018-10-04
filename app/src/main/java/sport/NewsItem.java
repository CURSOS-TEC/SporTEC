package sport;

public class NewsItem {
    private String mId;
    private String mTitle;
    private String mShortDescription;
    private String mDescription;
    private String mCategory;
    private String mURIphoto;

    /**
     * Creates a new
     * @param mId DB id
     * @param mTitle title
     * @param mShortDescription short description
     * @param mDescription long description
     * @param mCategory the category
     * @param mURIphoto the link to the photo
     */
    public NewsItem(String mId, String mTitle, String mShortDescription, String mDescription, String mCategory, String mURIphoto) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mShortDescription = mShortDescription;
        this.mDescription = mDescription;
        this.mCategory = mCategory;
        this.mURIphoto = mURIphoto;
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

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
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
}
