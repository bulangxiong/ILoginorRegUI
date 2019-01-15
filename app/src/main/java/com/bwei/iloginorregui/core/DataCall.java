package com.bwei.iloginorregui.core;

import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.core.exception.ApiException;

public interface DataCall<T> {
    void success(T result);
    void fail(ApiException e);

}
