package cn.todev.examples.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.todev.examples.R;

public class AsyncTaskActivity extends AppCompatActivity {

    @BindView(R.id.progress_task)
    ProgressBar progressTask;
    @BindView(R.id.et_down_file)
    EditText etDownFile;

    private DownloadAsyncTask mDownAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDownAsyncTask.cancel(true);
        mDownAsyncTask = null;
    }

    @OnClick({R.id.btn_execute, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_execute:
                execute();
                break;
            case R.id.btn_cancel:
                cancel();
                break;
        }
    }

    private void execute() {
        String url = etDownFile.getText().toString();
        if (TextUtils.isEmpty(url)) return;

        String u1 = "https://oss.bstcine.com/kj/2017/09/13/100416832SFxuHbZ.mp3";
        String u2 = "https://oss.bstcine.com/kj/2017/09/13/100432757SRVstNd.mp3";
        String u3 = "https://oss.bstcine.com/kj/2017/09/13/095911723SSEWH3J.mp3";


        mDownAsyncTask = new DownloadAsyncTask(progressTask);
        mDownAsyncTask.execute(url);
    }

    private void cancel() {
        mDownAsyncTask.cancel(true);
    }

    static class DownloadAsyncTask extends AsyncTask<String, Integer, Long> {

        private WeakReference<ProgressBar> mProgress;

        DownloadAsyncTask(ProgressBar progressBar) {
            mProgress = new WeakReference<>(progressBar);
        }

        protected Long doInBackground(String... urls) {
            int count = urls.length;
            long totalSize = 0;

            Integer[] progress = new Integer[count];

            for (int i = 0; i < count; i++) {
                try {
                    URL url = new URL(urls[i]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    long contentLength = conn.getContentLength();
                    Logger.i("contentLength:" + contentLength);

                    InputStream inputStream = conn.getInputStream();

                    byte[] bytes = new byte[1024];
                    long fileSize = 0;
                    int temp_Len;
                    while ((temp_Len = inputStream.read(bytes)) != -1) {
                        fileSize += temp_Len;
                        progress[i] = (int) (fileSize * 100 / contentLength);

                        Logger.i("progress-" + i + ":" + progress[i]);

                        publishProgress(progress);
                    }

                    totalSize += contentLength;

                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
            if (mProgress == null) return;

            int allProgress = 0;

            for (Integer p : progress)
                allProgress += p == null ? 0 : p;

            mProgress.get().setProgress(allProgress / progress.length);
        }

        protected void onPostExecute(Long result) {
            ToastUtils.showShort("Downloaded " + result + " bytes");
        }

    }

}
