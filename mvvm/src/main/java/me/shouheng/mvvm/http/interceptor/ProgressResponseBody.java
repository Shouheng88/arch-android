package me.shouheng.mvvm.http.interceptor;

import android.support.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.*;

import java.io.IOException;

/**
 * The decorator implementation for ResponseBody.
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2019/7/18 22:45
 */
public class ProgressResponseBody extends ResponseBody {

    private ResponseBody responseBody;

    private BufferedSource bufferedSource;

    private ProgressResponseCallback callback;

    ProgressResponseBody(ResponseBody responseBody, ProgressResponseCallback callback) {
        this.responseBody = responseBody;
        this.callback = callback;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {

            long readLength = 0;

            @Override
            public long read(@NonNull Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                readLength += bytesRead == -1 ? 0 : bytesRead;
                if (callback != null) {
                    callback.onProgressChanged(contentLength(), readLength );
                }
                return bytesRead;
            }
        };
    }
}
