package hogent.group15;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URI;

import hogent.group15.ui.R;

/**
 * Created by Frederik on 10/11/2015.
 */
public class AsyncUtil {

    public static class BitmapParameter {

        private final URI uri;
        private final Resources resources;

        public BitmapParameter(URI uri, Resources resources) {
            this.uri = uri;
            this.resources = resources;
        }

        public URI getUri() {
            return uri;
        }

        public Resources getResources() {
            return resources;
        }
    }


}
