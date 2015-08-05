/*
 * Copyright 2015 XiNGRZ <chenxingyu92@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.xingrz.gankmeizhi.net;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.xingrz.gankmeizhi.db.Article;
import me.xingrz.gankmeizhi.db.Image;

public class Content {

    private static final String TAG = "Content";

    public String key;

    public String url;
    public String title;

    public List<String> images = new ArrayList<>();

    @Nullable
    public String later;

    @Nullable
    public String earlier;

    public Article persist(ImageFetcher imageFetcher) throws IOException {
        Article article = new Article();
        article.setKey(key);
        article.setTitle(title);
        article.setUrl(url);
        article.setLater(later == null ? "" : later);
        article.setEarlier(earlier == null ? "" : earlier);
        article.setOrder(order());

        for (String url : images) {
            Point size = new Point();

            // TODO: 这样首次抓取的时候要多抓取一次用于测量尺寸，会耗费多一次。以后再优化
            imageFetcher.prefetchImage(url, size);

            Image image = new Image();
            image.setUrl(url);
            image.setWidth(size.x);
            image.setHeight(size.y);
            image.setArticle(article);
            image.setOrder(order());

            article.getImages().add(image);
        }

        return article;
    }

    /**
     * 更新已有数据库记录
     * <p/>
     * 因为最新一篇文章的地址是 "/"，缺少日期信息，且没有「上一篇」链接。因此当抓到比它新的文章之后，应当把它的这些
     * 信息补充完整。
     *
     * @param article 数据库记录
     */
    public void update(Article article) {
        Log.d(TAG, String.format("updating latest record: %s->%s %s->%s %d->%d",
                article.getUrl(), url,
                article.getLater(), later,
                article.getOrder(), order()));

        article.setUrl(url);
        article.setLater(later == null ? "" : later);
        article.setOrder(order());

        for (Image image : article.getImages()) {
            image.setOrder(order());
        }
    }

    private int order() {
        if ("/".equals(url)) {
            return Article.DATE_LATEST;
        } else {
            String[] seg = url.split("/");
            return Integer.parseInt(seg[1]) * 10000
                    + Integer.parseInt(seg[2]) * 100
                    + Integer.parseInt(seg[3]);
        }
    }

}
