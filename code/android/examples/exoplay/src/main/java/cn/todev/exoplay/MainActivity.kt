package cn.todev.exoplay

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.LogUtils
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.crypto.AesCipherDataSource
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {


    val originStream: ByteArray by lazy {
        assets.open("ge.mp3").readBytes()
    }
    val key = "1234567890123456".toByteArray()
    val encryptPath: String by lazy {
        externalCacheDir?.absolutePath + File.separator + "ge_aes_ctr_no_padding.aes"
    }
    val decryptPath: String by lazy {
        externalCacheDir?.absolutePath + File.separator + "ge_aes_ctr_no_padding.mp3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player

        // This is the MediaSource representing the media to be played.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "cn.todev"))

        val baseDataSource = dataSourceFactory.createDataSource()
        val decryptDataSource = AesCipherDataSource("1234567890123456".toByteArray(), baseDataSource)

        val mp3Source = ProgressiveMediaSource.Factory { decryptDataSource }
                .createMediaSource(Uri.parse(encryptPath))

        // Prepare the player with the source.
        player.prepare(mp3Source)


        btnEncrypt.setOnClickListener { encrypt() }

        btnDecrypt.setOnClickListener { decrypt() }
    }

    private fun encrypt() {
        val encryptStream = AesUtils().encrypt(originStream)
        FileIOUtils.writeFileFromBytesByStream(encryptPath, encryptStream)

        LogUtils.i("encrypt ${encryptStream.size}  $encryptPath")
    }

    private fun decrypt() {
        val encryptStream = FileIOUtils.readFile2BytesByStream(encryptPath) ?: return
        val decryptStream = AesUtils().decrypt(encryptStream)
        FileIOUtils.writeFileFromBytesByStream(decryptPath, decryptStream)

        LogUtils.i("decrypt ${encryptStream.size} ${decryptStream.size}  $decryptPath")
    }

}

