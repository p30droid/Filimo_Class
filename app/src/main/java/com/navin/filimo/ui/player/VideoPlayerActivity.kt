package com.navin.filimo.ui.player

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import com.navin.filimo.R
import com.navin.filimo.adapter.CommentsAdapter
import com.navin.filimo.api.Api
import com.navin.filimo.api.IService
import com.navin.filimo.config.AppConfig
import com.navin.filimo.databinding.ActivityVideoPlayerBinding
import com.navin.filimo.models.CommentDataModel
import com.navin.filimo.models.Video
import com.navin.filimo.models.VideoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoPlayerActivity : AppCompatActivity() {

    lateinit var binding : ActivityVideoPlayerBinding

    lateinit var bundle : Bundle
    lateinit var  video : Video
    lateinit var player : ExoPlayer

    var isFullScreen = false

    lateinit var appConfig: AppConfig

    lateinit var iService: IService

    lateinit var videoModel: VideoModel

    var videoId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iService = Api.retrofit.create(IService::class.java)

        appConfig = AppConfig(this@VideoPlayerActivity)

        bundle = intent.extras!!

        if(bundle!=null){

           val userData =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("videoObj", Video::class.java)
            } else {
                intent.getParcelableExtra<Video>("videoObj")
            }

            var uri: Uri = Uri.parse(userData?.video_url)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.txtDescription.setText(
                    Html.fromHtml(
                        userData?.video_description,
                        Html.FROM_HTML_MODE_LEGACY
                    )
                );
            } else {
                binding.txtDescription.setText(Html.fromHtml(userData?.video_description));
            }
            player = ExoPlayer.Builder(applicationContext).build()

            binding.playerView.player = player

            val item = MediaItem.fromUri(uri)
            player.setMediaItem(item)
            player.prepare()
            player.play()


            videoId =  userData!!.id

            loadData(videoId)


        }


        binding.imgSend.setOnClickListener {
            if(appConfig.islogin()){

                val comment = binding.edtComment.text.toString()

                if(comment.isNotEmpty()) {

                    iService.videoComment(comment,appConfig.getProfile().email,videoModel.ALL_IN_ONE_VIDEO.get(0).id)
                        .enqueue(object : Callback<CommentDataModel>{
                            override fun onResponse(
                                call: Call<CommentDataModel>,
                                response: Response<CommentDataModel>
                            ) {

                                Toast.makeText(applicationContext,
                                    getString(R.string.success_sending_comment), Toast.LENGTH_LONG).show()

                                binding.edtComment.setText("")

                                loadData(videoId)

                            }

                            override fun onFailure(call: Call<CommentDataModel>, t: Throwable) {
                                Toast.makeText(applicationContext,
                                    getString(R.string.error_in_sending_comment), Toast.LENGTH_LONG).show()

                            }

                        })

                }
                else {
                    Toast.makeText(applicationContext, getString(R.string.login_comment_required), Toast.LENGTH_LONG).show()

                }





            }else {
                Toast.makeText(applicationContext, getString(R.string.login_required), Toast.LENGTH_LONG).show()
            }
        }



    }

    private fun loadData(videoId : String) {
        binding.progress.visibility = View.VISIBLE

        iService.video(videoId).enqueue(object : Callback<VideoModel> {
            override fun onResponse(call: Call<VideoModel>, response: Response<VideoModel>) {
                binding.progress.visibility = View.GONE

                videoModel = response.body()!!

                val video : Video = videoModel.ALL_IN_ONE_VIDEO.get(0)


                binding.recyclerComments.adapter =
                    response.body()!!.ALL_IN_ONE_VIDEO.get(0).userComments?.let {
                        CommentsAdapter(
                            this@VideoPlayerActivity,
                            it
                        )
                    }

                binding.recyclerComments.layoutManager = LinearLayoutManager(
                    applicationContext,
                    LinearLayoutManager.VERTICAL, false
                )


            }

            override fun onFailure(call: Call<VideoModel>, t: Throwable) {
                binding.progress.visibility = View.GONE
            }

        })
    }


    override fun onStop() {
        super.onStop()
        if(player!=null){
            player.stop()
        }

    }
}