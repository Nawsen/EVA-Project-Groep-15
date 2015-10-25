package hogent.group15.domain;

import java.io.IOException;

/**
 * Created by Brent on 10/25/2015.
 */
public interface OnNetworkResponseListener<R, E> {
    void onResponse(R data);
    void onError(E ex);
}
