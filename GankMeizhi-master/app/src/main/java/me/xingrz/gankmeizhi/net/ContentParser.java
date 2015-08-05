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

import android.support.annotation.Nullable;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ContentParser {

    private static final String TAG = "ArticleParser";

    @Nullable
    public static Content parse(String html) {
        Content article = new Content();

        Document document = Jsoup.parse(html);

        Element content = document.select(".container.content").first();
        if (content == null) {
            Log.e(TAG, "no content");
            return null;
        }

        Element meta = content.select("> .ds-thread").first();
        if (meta == null) {
            Log.e(TAG, "meta not found");
            return null;
        }

        article.key = meta.attr("data-thread-key");
        if (article.key.isEmpty()) {
            Log.e(TAG, "meta no thread key");
            return null;
        }

        article.title = meta.attr("data-title");
        if (article.title.isEmpty()) {
            Log.e(TAG, "meta no title");
            return null;
        }

        Log.d(TAG, "title: " + article.title);

        article.url = meta.attr("data-url");
        if (article.url.isEmpty()) {
            Log.e(TAG, "meta no url");
            return null;
        } else if (!article.url.startsWith(ArticleRequestFactory.URL)) {
            Log.e(TAG, "url wrong prefix " + article.url);
            return null;
        }

        article.url = article.url.substring(ArticleRequestFactory.URL.length());

        if (!"/".equals(article.url) && !article.url.matches("^/\\d{4}/\\d{2}/\\d{2}$")) {
            Log.e(TAG, "invalid url " + article.url);
            return null;
        }

        Log.d(TAG, "url: " + article.url);

        Elements sibling = content.select("> .row .six.columns p");
        if (sibling.size() != 2) {
            Log.e(TAG, "sibling count not 2");
            return null;
        }

        Element later = sibling.get(0).select("a").first();
        if (later != null) {
            article.later = later.attr("href");
            Log.d(TAG, "later: " + article.later);
        }

        Element earlier = sibling.get(1).select("a").first();
        if (earlier != null) {
            article.earlier = earlier.attr("href");
            Log.d(TAG, "earlier: " + article.earlier);
        }

        for (Element image : content.select("> .outlink > * > img")) {
            String src = image.attr("src");
            article.images.add(src);
            Log.d(TAG, "image: " + src);
        }

        return article;
    }

}
