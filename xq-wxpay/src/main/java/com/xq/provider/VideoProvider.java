package com.xq.provider;

import com.xq.domain.Video;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author xq
 */
public class VideoProvider {
    public String updateProvider(final Video video){
        return new SQL(){
            {
                UPDATE("video");
                if (video.getTitle() != null){
                    SET("title=#{title}");
                }
                if (video.getSummary()!= null){
                    SET("summary=#{summary}");
                }
            }
        }.toString();
    }
}
