package lucky.kotlinbeginner.tic_tac_toe_kotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import lucky.kotlinbeginner.tic_tac_toe_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn {
        NOUGHT,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var crossesScore = 0
    private var noughtScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intiBoard()

    }

    private fun intiBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.a4)
        boardList.add(binding.a5)
        boardList.add(binding.a6)
        boardList.add(binding.a7)
        boardList.add(binding.a8)
        boardList.add(binding.a9)
    }

    fun boardTapped(view : View) {

        if (view !is Button)
            return
        addToBoard(view)

        if (checkForVictory(NOUGHT)) {
            noughtScore++
            for (button in boardList) {
                button.isEnabled = false
            }
            result("Nought Wins!")

        } else if (checkForVictory(CROSS)) {
            crossesScore++
            for (button in boardList) {
                button.isEnabled = false
            }
            result("Crosses Wins!")

        }

        if (fullBoard()){
            result("Draw")
        }
    }

    private fun checkForVictory(s: String) :  Boolean{

        //Horizontal Victory
        if (match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s))
            return true

        if (match(binding.a4, s) && match(binding.a5, s) && match(binding.a6, s))
            return true

        if (match(binding.a7, s) && match(binding.a8, s) && match(binding.a9, s))
            return true

        //Vertical Victory
        if (match(binding.a1, s) && match(binding.a4, s) && match(binding.a7, s))
            return true

        if (match(binding.a2, s) && match(binding.a5, s) && match(binding.a8, s))
            return true

        if (match(binding.a3, s) && match(binding.a6, s) && match(binding.a9, s))
            return true

        //Diagonal Victory
        if (match(binding.a1, s) && match(binding.a5, s) && match(binding.a9, s))
            return true

        if (match(binding.a3, s) && match(binding.a5, s) && match(binding.a7, s))
            return true

        return false

    }

    private fun match(button: Button, symbol: String) : Boolean = button.text == symbol

    private fun result(title: String) {
        val message = "\n Noughts $noughtScore\n\n Crosses $crossesScore"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            {
                _,_ ->
                for (button in boardList) {
                    button.isEnabled = true
                }
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun addToBoard(button: Button) {
        if (button.text != "")
            return

        if (currentTurn == Turn.NOUGHT){
            button.text = NOUGHT
            //button.setBackgroundColor(resources.getColor(R.color.nought))
            button.setBackgroundColor(0xFFFF9800.toInt())
            //button.setBackgroundResource(R.color.nought)
            currentTurn = Turn.CROSS
        } else if (currentTurn == Turn.CROSS){
            button.text = CROSS
            button.setBackgroundColor(resources.getColor(R.color.cross))
            currentTurn = Turn.NOUGHT
        }

        setTurnTable()
    }

    private fun resetBoard(){
        for (button in boardList){
            button.text = ""
            button.setBackgroundColor(Color.TRANSPARENT)
        }

        if (firstTurn == Turn.NOUGHT)
            firstTurn == Turn.CROSS
        else if (firstTurn == Turn.CROSS)
            firstTurn = Turn.NOUGHT

        currentTurn = firstTurn
        setTurnTable()

    }

    private fun fullBoard(): Boolean{
        for (button in boardList){

            if (button.text == "")
                return  false

        }

        return true
    }

    private fun setTurnTable() {
        var turnText = ""
        if (currentTurn == Turn.CROSS)
            turnText = "Turn $CROSS"

        else if (currentTurn == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"

        binding.turnTV.text = turnText
    }

    companion object
    {
        const val NOUGHT = "0"
        const val CROSS = "X"
    }


}