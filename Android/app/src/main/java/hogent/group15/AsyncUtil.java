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

    public static AsyncTask<BitmapParameter, Void, Bitmap> getBitmapAsync(BitmapParameter parameter, final Consumer<Bitmap> onPost) {
        return new AsyncTask<BitmapParameter, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(BitmapParameter... params) {
                try {
                    return BitmapFactory.decodeStream(params[0].getUri().toURL().openStream());
                } catch (IOException e) {
                    Log.e(Logging.ERROR.getTag(), "Couldn't read image from: " + params[0].getUri());
                    return BitmapFactory.decodeResource(params[0].getResources(), R.drawable.question_mark);
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                onPost.consume(bitmap);
            }
        }.execute(parameter);
    }
}
