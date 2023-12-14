package com.navin.filimo

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.core.os.BuildCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.navin.filimo.adapter.CategoryAdapter
import com.navin.filimo.adapter.VideoAdapter
import com.navin.filimo.adapter.VideoCoverAdapter
import com.navin.filimo.api.Api
import com.navin.filimo.api.IService
import com.navin.filimo.databinding.ActivityMainBinding
import com.navin.filimo.models.HomeData
import com.navin.filimo.models.Video
import com.navin.filimo.ui.login.LoginActivity
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var  mediaPlayer : MediaPlayer

    lateinit var  service : IService

    var backPressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // setContentView(R.layout.activity_main)



        val typeFace = Typeface.createFromAsset(assets,"fonts/BHoma.ttf")
        //binding.txtFeatured.setTypeface(typeFace)

        val typeFaceVazir = Typeface.createFromAsset(assets,"fonts/BVazir.ttf")
        binding.txtLatest.setTypeface(typeFaceVazir)

        val typeFaceIRANSans = Typeface.createFromAsset(assets,"fonts/IRANSans.ttf")
        binding.txtRandom.setTypeface(typeFaceIRANSans)


          service  = Api.retrofit.create(IService::class.java)



        service.getHome().enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {


                var jsonObject = JSONObject(response.body()?.string())

                var video : String= jsonObject.getString("ALL_IN_ONE_VIDEO")

                var allVideoObject = JSONObject(video)

                var featuredVideo : String = allVideoObject.getString("featured_video")

                var featuredArray = JSONArray(featuredVideo)

                var featuredList : MutableList<Video> = arrayListOf()

                for(i in 0..featuredArray.length()-1){

                    var myObject = featuredArray.getJSONObject(i)

                    var id =  myObject.getString("id")
                    var catId =  myObject.getString("cat_id")
                    var videoType =  myObject.getString("video_type")
                    var videoTitle =  myObject.getString("video_title")
                    var videoUrl =  myObject.getString("video_url")
                    var videoId =  myObject.getString("video_id")
                    var videoThumbnailB =  myObject.getString("video_thumbnail_b")
                    var videoThumbnailS =  myObject.getString("video_thumbnail_s")
                    var videoDuration =  myObject.getString("video_duration")
                    var videoDescription =  myObject.getString("video_description")
                    var rateAvg =  myObject.getString("rate_avg")
                    var totelViewer =  myObject.getString("totel_viewer")
                    var cid =  myObject.getString("cid")
                    var categoryName =  myObject.getString("category_name")
                    var categoryImage =  myObject.getString("category_image")
                    var categoryImageThumb =  myObject.getString("category_image_thumb")


                    var video = Video(id,catId,videoType,videoTitle,videoUrl,videoId,videoThumbnailB,videoThumbnailS,
                        videoDuration,videoDescription,rateAvg,totelViewer,cid,categoryName,categoryImage,categoryImageThumb)
                     featuredList.add(video)
                    Log.e("","")
                }


                binding.recyclerFeatured.adapter = VideoCoverAdapter(this@MainActivity,featuredList)
                binding.recyclerFeatured.layoutManager = LinearLayoutManager(
                    applicationContext, LinearLayoutManager.HORIZONTAL, false
                )

                Log.e("","")



            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("","")
            }

        })

        loadData()

        binding.btnNetwork.setOnClickListener {
            loadData()
        }


        mediaPlayer = MediaPlayer.create(this@MainActivity , R.raw.digital_watch_alarm_long)

      //  mediaPlayer.prepare()
       // mediaPlayer.start()


        onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Back is pressed... Finishing the activity
               // finish()

                if (backPressedTime + 3000 > System.currentTimeMillis()) {
                    //super.onBackPressed()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
                }
                backPressedTime = System.currentTimeMillis()

            }
        })

    }


    override fun onStop() {
        super.onStop()

        if(mediaPlayer.isPlaying){
            mediaPlayer.stop()
        }

    }

    fun loadData() {

        binding.progress.visibility = View.VISIBLE
        binding.txtRandom.visibility = View.GONE
       // binding.txtFeatured.visibility = View.GONE
        binding.txtLatest.visibility = View.GONE
        binding.constraintNetworkError.visibility = View.GONE

        service.getHomeData().enqueue(object : Callback<HomeData>{
            override fun onResponse(call: Call<HomeData>, response: Response<HomeData>) {
                Log.e("","")

                binding.progress.visibility = View.GONE

                binding.txtRandom.visibility = View.VISIBLE
              //  binding.txtFeatured.visibility = View.VISIBLE
                binding.txtLatest.visibility = View.VISIBLE

                binding.recyclerLatestVideo.adapter = response.body()?.baseModel?.let {
                    VideoAdapter(this@MainActivity ,
                        it.latestVideo)
                }
                binding.recyclerLatestVideo.layoutManager = LinearLayoutManager(
                    applicationContext , LinearLayoutManager.HORIZONTAL,false
                )

                binding.recyclerRandom.adapter = response.body()?.baseModel?.let {
                    VideoAdapter(this@MainActivity,
                        it.allVideo)
                }
                binding.recyclerRandom.layoutManager = LinearLayoutManager(
                    applicationContext , LinearLayoutManager.HORIZONTAL,false
                )


                binding.recyclerCategory.adapter = response.body()?.baseModel?.let {
                    CategoryAdapter(applicationContext,
                        it.category)
                }
                binding.recyclerCategory.layoutManager = LinearLayoutManager(
                    applicationContext , LinearLayoutManager.HORIZONTAL,false
                )


            }

            override fun onFailure(call: Call<HomeData>, t: Throwable) {
                Log.e("","")
                binding.progress.visibility = View.GONE
                binding.constraintNetworkError.visibility = View.VISIBLE
            }

        })


    }






}