package com.harsukh.gmtest.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

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
