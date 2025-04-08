/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.salt.file.center.frame.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.salt.file.center.frame.vo.PageRsp;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {

    public static <D, M> D convert(M m, Class<D> dClass) {
        if (m == null) {
            return null;
        }
        try {
            D d = dClass.newInstance();
            BeanUtils.copyProperties(m, d);
            return d;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <D, M> PageRsp<D> convertPage(Page<M> page, Class<D> dClass) {
        PageRsp<D> pageReq = new PageRsp<>();
        pageReq.setPageSize(page.getSize());
        pageReq.setTotal(page.getTotal());
        pageReq.setPageNum(page.getCurrent());
        List<D> recordsD = new ArrayList<>();
        pageReq.setData(recordsD);
        if (!CollectionUtils.isEmpty(page.getRecords())) {
            page.getRecords().forEach(m -> {
                D d = null;
                try {
                    d = dClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                BeanUtils.copyProperties(m, d);
                recordsD.add(d);
            });
        }
        return pageReq;
    }

    public static <D, M> List<D> convertList(List<M> mList, Class<D> dClass) {
        List<D> dtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(mList)) {
            mList.forEach(m -> {
                D d = null;
                try {
                    d = dClass.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                BeanUtils.copyProperties(m, d);
                dtoList.add(d);
            });
        }
        return dtoList;
    }

    public static <D, M> D convert(M from, D to) {
        BeanUtils.copyProperties(from, to);
        return to;
    }

    public static <D, M> D convert(M from, D to, String[] ignoreArray) {
        BeanUtils.copyProperties(from, to, ignoreArray);
        return to;
    }
}
