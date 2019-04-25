package edu.us.ischool.tanl2.lifecounter

import android.content.pm.ActivityInfo
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Window
import android.view.WindowManager
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val DEFAULT_HEALTH:Int=20
    lateinit var healths:HashMap<Int,Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        if(!::healths.isInitialized){
            try {
                healths = savedInstanceState?.getSerializable("healths") as HashMap<Int, Int>
            }catch (ex:Exception){
                healths= hashMapOf(R.id.player1 to 20,R.id.player2 to 20,R.id.player3 to 20,R.id.player4 to 20)
            }
            setContentView(R.layout.activity_main)
            setPlayerName(R.id.player1,"1")
            setPlayerName(R.id.player2,"2")
            setPlayerName(R.id.player3,"3")
            setPlayerName(R.id.player4,"4")
        }

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        healths=savedInstanceState?.getSerializable("healths") as HashMap<Int, Int>
    }
    override fun onSaveInstanceState(savedInstanceState: Bundle?){
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putSerializable("healths",healths)
    }
    private fun setPlayerName(Id:Int, name:String){
        val parentView=findViewById<LinearLayout>(Id)
        parentView.findViewWithTag<TextView>("playerName").text="Player $name"
        var health=parentView.findViewWithTag<TextView>("playerHealth")
        val hVal=healths.get(Id)
        health.text="$hVal"
        parentView.findViewWithTag<Button>("add1").setOnClickListener({arithmeticButtonClick(Id,name,1)})
        parentView.findViewWithTag<Button>("minus1").setOnClickListener({arithmeticButtonClick(Id,name,-1)})
        parentView.findViewWithTag<Button>("minus5").setOnClickListener({arithmeticButtonClick(Id,name,-5)})
        parentView.findViewWithTag<Button>("add5").setOnClickListener({arithmeticButtonClick(Id,name,5)})
    }
    fun arithmeticButtonClick(Id:Int, name:String,amount:Int){
       var pHealth= healths.get(Id)!!+amount
        if(pHealth<=0){
            pHealth=0
            var losingLabel=findViewById<TextView>(R.id.losingLabel)
            losingLabel.visibility=View.VISIBLE
            losingLabel.text="Player $name LOSES!"
            findViewById<LinearLayout>(Id).findViewWithTag<TextView>("playerHealth").text="$pHealth"
            healths.put(Id,0)
        }else{
            findViewById<LinearLayout>(Id).findViewWithTag<TextView>("playerHealth").text="$pHealth"
            healths.put(Id,pHealth)
        }
    }
}
