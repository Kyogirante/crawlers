# crawlers
web crawlers for meizi

```java
public class DGAGuideInfoStore {

    private static DGAGuideInfoStore mInstance;

    private String mGuideImageUrl = "";
    private boolean mIsCanShowGuide = false;

    private DGAGuideInfoStore() {

    }

    public static DGAGuideInfoStore getInstance() {
        return SingletonHolder.getInstance(DGAGuideInfoStore.class);
    }

    public void setShowGuide(boolean isShow) {
        mIsCanShowGuide = isShow;
    }

    public boolean isShowGuide() {
        return mIsCanShowGuide;
    }

    public void setImageUrl(String url) {
        mGuideImageUrl = url;
    }

    public String getImageUrl() {
        return mGuideImageUrl;
    }
}
```
