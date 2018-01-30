package com.ficheralezzi.fantasygo.Utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by gaetano on 30/01/18.
 */

public interface VolleyResponseListener {
    void requestStarted();
    void requestCompleted(Messaggio messaggioResponse);
    void requestEndedWithError(VolleyError error);
}
