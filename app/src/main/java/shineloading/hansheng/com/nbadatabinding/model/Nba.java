package shineloading.hansheng.com.nbadatabinding.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import shineloading.hansheng.com.nbadatabinding.BR;

/**
 * Created by hansheng on 2016/5/22.
 */
public class Nba extends BaseObservable {

    private String contentType;
    private String description;
    private String title;
    private int articleId;
    private String contentSourceName;
    private String articleUrl;
    private String type;
    private String sourceType;
    private List<String> imgUrlList;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }
    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getContentSourceName() {
        return contentSourceName;
    }

    public void setContentSourceName(String contentSourceName) {
        this.contentSourceName = contentSourceName;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    @Bindable
    public List<String> getImgUrlList() {
        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
        notifyPropertyChanged(BR.imgUrlList);
    }

    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    private static final String BASE_URL = "http://nbaplus.sinaapp.com/api/v1.0/news/";

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    public interface INbaResponse<T> {
        void onData(T data);
    }
    public static void searchMovies(final INbaResponse<List<Nba>> response) {
        RequestParams params = new RequestParams();
        client.get(getAbsoluteUrl("update"), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Gson gson = new Gson();
                    JSONObject json = new JSONObject(new String(responseBody));
                    JSONArray jaNba = json.optJSONArray("newslist");
                    List<Nba> movies = gson.fromJson(jaNba.toString(), new TypeToken<List<Nba>>() {
                    }.getType());
                    response.onData(movies);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

}




