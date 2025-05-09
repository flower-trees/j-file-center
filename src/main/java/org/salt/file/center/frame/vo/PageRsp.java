package org.salt.file.center.frame.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageRsp<T> {

    protected List<T> data;
    protected long total;

    protected long pageSize;
    protected long pageNum;
}
