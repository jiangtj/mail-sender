package com.jiangtj.mailsender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jiang (jiang.taojie@foxmail.com)
 * 2019/9/12 21:05 End.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class KeyValue<T,R> {

    private T key;
    private R value;

}
