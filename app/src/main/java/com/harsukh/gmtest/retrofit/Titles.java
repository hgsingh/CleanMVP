package com.harsukh.gmtest.retrofit;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by harsukh on 3/22/17.
 */

public class Titles {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        private List<ChildrenBean> children;

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            private DataBean data;

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean implements Parcelable {
                private String url;
                private String title;
                private final static Pattern imgurPattern = Pattern.compile(".*(jpeg|jpg|png)");
                private final static Pattern gifPattern = Pattern.compile(".*(gifv).*");

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public boolean isImgur() {
                    String url = getUrl();
                    return !TextUtils.isEmpty(url) && url.matches(imgurPattern.pattern())
                            && !url.matches(gifPattern.pattern());
                }

                protected DataBean(Parcel in) {
                    url = in.readString();
                    title = in.readString();
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(url);
                    dest.writeString(title);
                }

                @SuppressWarnings("unused")
                public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
                    @Override
                    public DataBean createFromParcel(Parcel in) {
                        return new DataBean(in);
                    }

                    @Override
                    public DataBean[] newArray(int size) {
                        return new DataBean[size];
                    }
                };
            }
        }

    }
}
